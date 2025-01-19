 package controllers;

import vue.DashboardView;
import model.Emprunt;
import model.User;
import model.Livre;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardController {
    private DashboardView dashboardView;
    private EmpruntController empruntController;
    private LivreController livreController;
    private UserController userController;

    public DashboardController(DashboardView dashboardView, EmpruntController empruntController, LivreController livreController, UserController userController) {
        this.dashboardView = dashboardView;
        this.empruntController = empruntController;
        this.livreController = livreController;
        this.userController = userController;

        // Ajouter des écouteurs pour les boutons
        dashboardView.getGenerateReportButton().addActionListener(e -> generateReport());

        // Initialiser les graphiques
        updateCharts();
    }

    private void updateCharts() {
        updateBarChart();
        updatePieChart();
        updateTotalStats();
    }

    private void updateBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Emprunt> emprunts = empruntController.getEmprunts();

        // Créer un mappage entre chaque livre et le nombre d'emprunts
        Map<Livre, Integer> empruntCounts = new HashMap<>();
        
        for (Emprunt emprunt : emprunts) {
            Livre livre = livreController.getLivreById(emprunt.getLivreId());
            if (livre != null) {
                empruntCounts.put(livre, empruntCounts.getOrDefault(livre, 0) + 1);
            }
        }

        // Trier les livres par le nombre d'emprunts décroissant
        List<Map.Entry<Livre, Integer>> sortedEntries = new ArrayList<>(empruntCounts.entrySet());
        sortedEntries.sort((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()));

        // Ajouter les 5 livres les plus empruntés au dataset
        int maxBooksToShow = 5;
        for (int i = 0; i < Math.min(maxBooksToShow, sortedEntries.size()); i++) {
            Livre livre = sortedEntries.get(i).getKey();
            int count = sortedEntries.get(i).getValue();
            dataset.addValue(count, "Emprunts", livre.getTitre());
        }

        // Créer le graphique à barres
        JFreeChart barChart = ChartFactory.createBarChart(
                "Livres les Plus Empruntés",    // Titre du graphique
                "Livres",                      // Axe X
                "Nombre d'Emprunts",           // Axe Y
                dataset                        // Données
        );
        
        // Supprimer le fond blanc
        barChart.setBackgroundPaint(new Color(0, 0, 0, 0)); // Fond transparent
        barChart.getPlot().setBackgroundPaint(new Color(0, 0, 0, 0)); // Fond transparent du graphique

        // Créer le chart panel et réduire sa taille
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(200, 150)); // Définir une taille fixe pour les graphiques
        dashboardView.getBarChartPanel().removeAll();
        dashboardView.getBarChartPanel().add(chartPanel, BorderLayout.CENTER);
        dashboardView.getBarChartPanel().revalidate();
        dashboardView.getBarChartPanel().repaint();
    }


    private void updatePieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<User> users = userController.getAllUsers();

        // Compter les utilisateurs actifs
        int activeUsers = (int) users.stream().filter(User::isStatut).count();
        int inactiveUsers = users.size() - activeUsers;

        dataset.setValue("Utilisateurs Actifs", activeUsers);
        dataset.setValue("Utilisateurs Inactifs", inactiveUsers);

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Statut des Utilisateurs",
                dataset,
                true, // Légende
                true, // Tooltips
                false // URLs
        );
        
        // Supprimer le fond blanc
        pieChart.setBackgroundPaint(new Color(0, 0, 0, 0)); // Fond transparent
        pieChart.getPlot().setBackgroundPaint(new Color(0, 0, 0, 0)); // Fond transparent du graphique

        // Créer le chart panel et réduire sa taille
        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(200, 150)); // Définir une taille fixe pour les graphiques
        dashboardView.getPieChartPanel().removeAll();
        dashboardView.getPieChartPanel().add(chartPanel, BorderLayout.CENTER);
        dashboardView.getPieChartPanel().revalidate();
        dashboardView.getPieChartPanel().repaint();
    }

    private void updateTotalStats() {
        // Créer des cartes colorées pour les statistiques
        int totalLivres = livreController.getAllLivres().size();
        int totalUsers = userController.getTotalUsers();
        int totalEmprunts = empruntController.getTotalEmprunts();

        JPanel totalLivresPanel = createStatCard("Total Livres", totalLivres, new Color(0xFF7043));
        JPanel totalUsersPanel = createStatCard("Total Utilisateurs", totalUsers, new Color(0x4CAF50));
        JPanel totalEmpruntsPanel = createStatCard("Total Emprunts", totalEmprunts, new Color(0x2196F3));

        // Ajouter les cartes à la vue
        dashboardView.getLineChartPanel().removeAll();
        dashboardView.getLineChartPanel().setLayout(new GridLayout(1, 3, 10, 10)); // Disposition en ligne
        dashboardView.getLineChartPanel().add(totalLivresPanel);
        dashboardView.getLineChartPanel().add(totalUsersPanel);
        dashboardView.getLineChartPanel().add(totalEmpruntsPanel);
        dashboardView.getLineChartPanel().revalidate();
        dashboardView.getLineChartPanel().repaint();
    }

    private JPanel createStatCard(String title, int value, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(color);
        panel.setPreferredSize(new Dimension(200, 150)); // Taille de la carte
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel valueLabel = new JLabel(String.valueOf(value), JLabel.CENTER);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 18));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    private void generateReport() {
        // Logique pour générer un rapport
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("rapport.txt"))) {
            writer.write("Rapport des Emprunts, Livres et Utilisateurs\n");
            writer.write("Total des Livres: " + livreController.getAllLivres().size() + "\n");
            writer.write("Total des Utilisateurs: " + userController.getTotalUsers() + "\n");
            writer.write("Total des Emprunts: " + empruntController.getTotalEmprunts() + "\n");
            JOptionPane.showMessageDialog(dashboardView, "Rapport généré avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(dashboardView, "Erreur lors de la génération du rapport: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
