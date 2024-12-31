package vue;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DashboardView extends JPanel {
    private static final long serialVersionUID = 1L;

    // Panneau pour les statistiques
    private JPanel statsPanel;

    // Panneau pour les graphiques
    private JPanel chartPanel;

    // Bouton pour rafraîchir les données
    private JButton refreshButton;

    // Menu déroulant pour les filtres
    private JComboBox<String> filterComboBox;
    private JComboBox<String> genreComboBox; // Déclaration de genreComboBox
    private JComboBox<String> periodComboBox; // Déclaration de periodComboBox
    private JComboBox<String> userComboBox; // Déclaration de userComboBox

    public DashboardView() {
        setLayout(new BorderLayout(15, 15)); // Espacement entre les composants
        setBackground(new Color(0xEAEAEA)); // Couleur de fond du Dashboard

        // Initialisation du panneau des statistiques
        statsPanel = new JPanel(new GridLayout(1, 3, 10, 0));  // GridLayout avec un espacement
        statsPanel.setBackground(new Color(0xEAEAEA)); // Fond clair pour plus de contraste
        add(createCard(statsPanel, "Statistiques"), BorderLayout.NORTH);

        // Initialisation du panneau des graphiques
        chartPanel = new JPanel(new GridLayout(1, 2, 10, 10));  // Deux graphiques côte à côte
        chartPanel.setBackground(new Color(0xEAEAEA)); // Fond clair pour plus de contraste
        add(createCard(chartPanel, "Graphiques"), BorderLayout.CENTER);

        // Panneau inférieur pour les contrôles
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(new Color(0xEAEAEA)); // Fond clair

        // Menu déroulant pour filtrer les statistiques
        filterComboBox = new JComboBox<>(new String[]{"Tous", "Janvier", "Février", "Mars", "Avril"});
        filterComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        filterComboBox.setPreferredSize(new Dimension(150, 30));
        filterComboBox.setBackground(Color.WHITE);
        controlPanel.add(filterComboBox);

        // Initialisation des JComboBox
        genreComboBox = new JComboBox<>(new String[]{"Tous les genres", "Fiction", "Non-Fiction", "Science Fiction"});
        periodComboBox = new JComboBox<>(new String[]{"Tout le temps", "Cette année", "Ce mois"});
        userComboBox = new JComboBox<>(new String[]{"Tous les utilisateurs", "Utilisateur 1", "Utilisateur 2"});

        // Ajoutez les JComboBox au panneau de contrôle
        controlPanel.add(genreComboBox);
        controlPanel.add(periodComboBox);
        controlPanel.add(userComboBox);

        // Bouton pour rafraîchir les graphiques
        refreshButton = new JButton("Rafraîchir");
        refreshButton.setBackground(new Color(0x4CAF50));  // Vert moderne
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        controlPanel.add(refreshButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    // Méthode pour créer une carte avec un panneau intérieur et un titre
    private JPanel createCard(JPanel innerPanel, String title) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0xCCCCCC), 1, true), title));
        card.add(innerPanel, BorderLayout.CENTER);
        return card;
    }

    // Mise à jour des statistiques affichées
    public void updateStats(String totalBooks, String activeUsers, String totalLoans) {
        statsPanel.removeAll();
        statsPanel.add(createStatPanel("Total de Livres", totalBooks));
        statsPanel.add(createStatPanel("Utilisateurs Actifs", activeUsers));
        statsPanel.add(createStatPanel("Emprunts Actifs", totalLoans));
        statsPanel.revalidate();
        statsPanel.repaint();
    }

    // Méthode pour générer un panneau de statistiques (détail d'un chiffre)
    private JPanel createStatPanel(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xFFFFFF));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(valueLabel, BorderLayout.CENTER);
        return panel;
    }

    // Définir le graphique à afficher
    public void setChart(JComponent chart) {
        chartPanel.removeAll();
        chartPanel.add(chart);
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    // Méthode pour créer un graphique à barres
    public JComponent createBarChart(String title, String categoryAxisLabel, String valueAxisLabel, List<String> categories, List<Double> values) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < categories.size(); i++) {
            dataset.addValue(values.get(i), "Valeurs", categories.get(i));
        }
        JFreeChart barChart = ChartFactory.createBarChart(title, categoryAxisLabel, valueAxisLabel, dataset);
        return new ChartPanel(barChart);
    }

    // Méthode pour créer un graphique linéaire
    public JComponent createLineChart(String title, String xAxisLabel, String yAxisLabel, List<Double> xValues, List<Double> yValues) {
        XYSeries series = new XYSeries("Données");
        for (int i = 0; i < xValues.size(); i++) {
            series.add(xValues.get(i), yValues.get(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart lineChart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, dataset);
        return new ChartPanel(lineChart);
    }

    // Méthode pour créer un graphique circulaire
   
public JComponent createPieChart(String title, List<String> categories, List<Double> values) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (int i = 0; i < categories.size(); i++) {
            dataset.setValue(categories.get(i), values.get(i));
        }
        JFreeChart pieChart = ChartFactory.createPieChart(title, dataset, true, true, false);
        return new ChartPanel(pieChart);
    }

    // Getters pour les composants
    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JComboBox<String> getFilterComboBox() {
        return filterComboBox;
    }

    public JComboBox<String> getGenreComboBox() {
        return genreComboBox; // Assurez-vous que genreComboBox est bien initialisé
    }

    public JComboBox<String> getPeriodComboBox() {
        return periodComboBox; // Assurez-vous que periodComboBox est bien initialisé
    }

    public JComboBox<String> getUserComboBox() {
        return userComboBox; // Assurez-vous que userComboBox est bien initialisé
    }
}