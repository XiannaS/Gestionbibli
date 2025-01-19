package vue;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JPanel {
    private static final long serialVersionUID = 1L;

    private JPanel barChartPanel; // Pour les livres les plus empruntés
    private JPanel pieChartPanel; // Pour les utilisateurs actifs
    private JPanel lineChartPanel; // Pour le total des livres, utilisateurs et emprunts
    private JButton generateReportButton;

    public DashboardView() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(0xEAEAEA));

        // Panneau de contrôle
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(new Color(0xEAEAEA));

        generateReportButton = new JButton("Générer Rapport");
        generateReportButton.setBackground(new Color(0x2196F3));
        generateReportButton.setForeground(Color.WHITE);
        generateReportButton.setFocusPainted(false);
        generateReportButton.setFont(new Font("Arial", Font.BOLD, 14));
        controlPanel.add(generateReportButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Panneaux pour les graphiques
        barChartPanel = createChartPanel("Livres les Plus Empruntés");
        pieChartPanel = createChartPanel("Utilisateurs Actifs");
        lineChartPanel = createChartPanel("Total des Livres, Utilisateurs et Emprunts");

        // Ajouter les panneaux de graphiques au tableau de bord dans un GridLayout
        JPanel chartsPanel = new JPanel();
        chartsPanel.setLayout(new GridLayout(2, 2, 10, 10)); // Disposition 2x2 pour les graphiques
        chartsPanel.add(barChartPanel);
        chartsPanel.add(pieChartPanel);
        chartsPanel.add(new JPanel()); // Espace vide pour remplir la grille
        chartsPanel.add(lineChartPanel);

        // Utiliser un JScrollPane si nécessaire (si le contenu dépasse la taille de l'écran)
        JScrollPane scrollPane = new JScrollPane(chartsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(550, 380)); // Ajuster la taille pour plus de flexibilité

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createChartPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Utilisation de BorderLayout pour positionner les éléments
        panel.setBackground(new Color(0, 0, 0, 0)); // Fond transparent
        panel.setBorder(BorderFactory.createEmptyBorder()); // Enlever la bordure par défaut

        // Titre
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Étiquette plus petite
        panel.add(titleLabel, BorderLayout.NORTH);

        // Graphique
        JPanel chartPanel = new JPanel();
        chartPanel.setBackground(new Color(0, 0, 0, 0)); // Fond transparent pour le graphique
        chartPanel.setPreferredSize(new Dimension(150, 150)); // Ajuster la taille des graphiques
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

    public JButton getGenerateReportButton() {
        return generateReportButton;
    }

    public JPanel getBarChartPanel() {
        return barChartPanel;
    }

    public JPanel getPieChartPanel() {
        return pieChartPanel;
    }

    public JPanel getLineChartPanel() {
        return lineChartPanel;
    }
}
