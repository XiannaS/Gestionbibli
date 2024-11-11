package vue;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {
    private static final long serialVersionUID = 1L;

    public DashboardView() {
        setTitle("Tableau de Bord de la Bibliothèque");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // En-tête
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#004754"));
        JLabel titleLabel = new JLabel("Tableau de Bord de la Bibliothèque");
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Barre latérale
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new GridLayout(0, 1));
        sidebarPanel.setPreferredSize(new Dimension(200, 0));
        sidebarPanel.setBackground(Color.LIGHT_GRAY);

        // Ajout de boutons de navigation
        sidebarPanel.add(new JButton("Livres"));
        sidebarPanel.add(new JButton("Emprunts"));
        sidebarPanel.add(new JButton("Utilisateurs"));
        sidebarPanel.add(new JButton("Statistiques"));
        add(sidebarPanel, BorderLayout.WEST);

        // Zone principale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2)); // 3 lignes, 2 colonnes

        // Statistiques
        JPanel statsPanel = new JPanel(new GridLayout(4, 1));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistiques"));
        statsPanel.add(new JLabel("Total des Livres: 100"));
        statsPanel.add(new JLabel("Livres Disponibles: 75"));
        statsPanel.add(new JLabel("Livres Empruntés: 25"));
        statsPanel.add(new JLabel("Utilisateurs: 50"));
        mainPanel.add(statsPanel);

        // Graphiques (placeholder)
        JPanel graphPanel = new JPanel();
        graphPanel.setBorder(BorderFactory.createTitledBorder("Graphiques"));
        graphPanel.add(new JLabel("Graphiques ici")); // Remplacez ceci par un graphique réel
        mainPanel.add(graphPanel);

        // Liste des livres récemment ajoutés
        JPanel recentBooksPanel = new JPanel(new BorderLayout());
        recentBooksPanel.setBorder(BorderFactory.createTitledBorder("Livres Récemment Ajoutés"));
        recentBooksPanel.add(new JLabel("Liste des livres ici"), BorderLayout.CENTER); // Remplacez ceci par une liste réelle
        mainPanel.add(recentBooksPanel);

        // Notifications
        JPanel notificationsPanel = new JPanel();
        notificationsPanel.setBorder(BorderFactory.createTitledBorder("Notifications"));
        notificationsPanel.add(new JLabel("Aucune notification pour le moment.")); // Remplacez ceci par des notifications réelles
        mainPanel.add(notificationsPanel);

        // Recherche rapide
        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createTitledBorder("Recherche Rapide"));
        searchPanel.add(new JTextField(15));
        searchPanel.add(new JButton("Rechercher"));
        mainPanel.add(searchPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashboardView dashboard = new DashboardView();
            dashboard.setVisible(true);
        });
    }
}