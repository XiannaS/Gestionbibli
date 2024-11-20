package vue;

import controllers.EmpruntController;
import controllers.UserController;
import model.Emprunt;
import style.ModernNavBar;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class DashboardView extends JFrame {

    private static final long serialVersionUID = 1L;
    private ModernNavBar navBar;
    private JPanel statsPanel;
    private JPanel graphPanel;
    private JPanel overduePanel;
    private EmpruntController empruntController;
    private UserController userController;
    private UserView userView;

    public DashboardView() {
        setTitle("Gestion de Bibliothèque - Tableau de Bord");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialisation de la barre de navigation
        navBar = new ModernNavBar();
        add(navBar, BorderLayout.WEST); // Ajout de la barre de navigation à gauche

        // Initialisation des contrôleurs
        empruntController = new EmpruntController();
        userView = new UserView();
        userController = new UserController(userView);  

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Titre du tableau de bord
        JLabel dashboardTitle = new JLabel("Tableau de Bord", JLabel.CENTER);
        dashboardTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        dashboardTitle.setForeground(new Color(50, 50, 50));
        mainPanel.add(dashboardTitle, BorderLayout.NORTH);

        // Panel pour les statistiques
        statsPanel = createStatsPanel();
        mainPanel.add(statsPanel, BorderLayout.WEST);

        // Panel pour le graphique
        graphPanel = createGraphPanel();
        mainPanel.add(graphPanel, BorderLayout.CENTER);

        // Panel pour les prêts en retard
        overduePanel = createOverduePanel();
        mainPanel.add(overduePanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER); // Ajout du panneau principal au centre

        setVisible(true);
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4, 10, 10));
        panel.setBackground(new Color(245, 245, 245));

        // Exemple de statistiques
        int totalEmprunts = empruntController.afficherHistoriqueEmprunts().size();
        long totalEnRetard = empruntController.afficherHistoriqueEmprunts().stream().filter(e -> e.getDateRetour().isBefore(LocalDate.now()) && !e.isEstRendu()).count();
        int totalVisiteurs = userController.lireTousLesUsers().size();  // Supposons que chaque utilisateur est un visiteur
        int nouveauxMembres = userController.lireTousLesUsers().size(); // Simplifié pour l'exemple

        panel.add(createStatCard("Empruntés", String.valueOf(totalEmprunts)));
        panel.add(createStatCard("En retard", String.valueOf(totalEnRetard)));
        panel.add(createStatCard("Visiteurs", String.valueOf(totalVisiteurs)));
        panel.add(createStatCard("Nouveaux Membres", String.valueOf(nouveauxMembres)));

        return panel;
    }

    private JPanel createStatCard(String label, String value) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        card.setBackground(Color.WHITE);

        JLabel valueLabel = new JLabel(value, JLabel.CENTER);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        valueLabel.setForeground(new Color(50, 50, 50));

        JLabel labelLabel = new JLabel(label, JLabel.CENTER);
        labelLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        labelLabel.setForeground(new Color(100, 100, 100));

        card.add(valueLabel, BorderLayout.CENTER);
        card.add(labelLabel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createGraphPanel() {
        // Remplacer par la logique de création de graphique
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Visiteurs & Emprunteurs"));

        JLabel graphPlaceholder = new JLabel("Graphique ici", JLabel.CENTER);
        graphPlaceholder.setFont(new Font("SansSerif", Font.ITALIC, 20));
        graphPlaceholder.setForeground(new Color(150, 150, 150));

        panel.add(graphPlaceholder);

        return panel;
    }

    private JPanel createOverduePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Prêts en retard"));

        List<Emprunt> emprunts = empruntController.afficherHistoriqueEmprunts();
        String[] columnNames = {"ID Membre", "Titre", "Auteur", "Jours de retard", "Date de retour"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Emprunt emprunt : emprunts) {
            if (emprunt.getDateRetour().isBefore(LocalDate.now()) && !emprunt.isEstRendu()) {
                long joursDeRetard = ChronoUnit.DAYS.between(emprunt.getDateRetour(), LocalDate.now());
                Object[] row = {emprunt.getUtilisateurId(), "Titre du livre", "Auteur du livre", joursDeRetard, sdf.format(java.sql.Date.valueOf(emprunt.getDateRetour()))};
                model.addRow(row);
            }
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DashboardView();
        });
    }
}
