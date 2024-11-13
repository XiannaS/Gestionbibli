package vue;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JPanel {
    private static final long serialVersionUID = 1L;
    private CardLayout cardLayout;
    private JPanel mainPanel;  // Pour contenir les différentes vues (LivreView, EmpruntsView, etc.)
    private MainFrame mainFrame; 
    
    public DashboardView(MainFrame mainFrame) {
        this.mainFrame = mainFrame;  // Enregistrer la référence à MainFrame

        // Configuration de la vue...
        setLayout(new BorderLayout());}
    
        // Constructeur sans argument pour le test dans le main
        public DashboardView() {
            this(null);  // Appel du constructeur principal avec 'null' pour MainFrame
            initDashboard();
        }
        // Méthode pour initialiser l'interface de la vue (factoring de code)
        private void initDashboard() {
            setLayout(new BorderLayout());
        // En-tête
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#004754"));
        JLabel titleLabel = new JLabel("Tableau de Bord de la Bibliothèque");
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Barre latérale (pour la navigation)
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new GridLayout(0, 1));
        sidebarPanel.setPreferredSize(new Dimension(200, 0));
        sidebarPanel.setBackground(Color.LIGHT_GRAY);

        // Ajout de boutons de navigation
        JButton livresButton = new JButton("Livres");
        livresButton.addActionListener(e -> cardLayout.show(mainPanel, "livres"));
        JButton empruntsButton = new JButton("Emprunts");
        empruntsButton.addActionListener(e -> cardLayout.show(mainPanel, "emprunts"));
        JButton utilisateursButton = new JButton("Utilisateurs");
        utilisateursButton.addActionListener(e -> cardLayout.show(mainPanel, "utilisateurs"));
        JButton statistiquesButton = new JButton("Statistiques");
        statistiquesButton.addActionListener(e -> cardLayout.show(mainPanel, "statistiques"));

        sidebarPanel.add(livresButton);
        sidebarPanel.add(empruntsButton);
        sidebarPanel.add(utilisateursButton);
        sidebarPanel.add(statistiquesButton);
        
        add(sidebarPanel, BorderLayout.WEST);

        // Zone principale (CardLayout pour les vues)
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Ajout des vues (livres, emprunts, etc.) dans mainPanel
        mainPanel.add(createLivresView(), "livres");
        mainPanel.add(createEmpruntsView(), "emprunts");
        mainPanel.add(createUtilisateursView(), "utilisateurs");
        mainPanel.add(createStatistiquesView(), "statistiques");

        add(mainPanel, BorderLayout.CENTER);
    }

    // Vue des livres
    private JPanel createLivresView() {
        JPanel livresPanel = new JPanel(new BorderLayout());
        livresPanel.setBorder(BorderFactory.createTitledBorder("Gestion des Livres"));

        // Exemple de liste de livres (remplacez ceci par la vraie liste)
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Livre 1");
        listModel.addElement("Livre 2");
        listModel.addElement("Livre 3");

        JList<String> booksList = new JList<>(listModel);
        livresPanel.add(new JScrollPane(booksList), BorderLayout.CENTER);

        // Bouton pour ajouter un livre
        JButton addBookButton = new JButton("Ajouter un Livre");
        addBookButton.addActionListener(e -> {
            // Action pour ajouter un livre
        });
        livresPanel.add(addBookButton, BorderLayout.SOUTH);

        return livresPanel;
    }


    // Vue des emprunts (placeholder)
    private JPanel createEmpruntsView() {
        JPanel empruntsPanel = new JPanel();
        empruntsPanel.setBorder(BorderFactory.createTitledBorder("Gestion des Emprunts"));
        empruntsPanel.add(new JLabel("Gestion des emprunts ici"));  // Remplace par des composants réels
        return empruntsPanel;
    }

    // Vue des utilisateurs (placeholder)
    private JPanel createUtilisateursView() {
        JPanel utilisateursPanel = new JPanel();
        utilisateursPanel.setBorder(BorderFactory.createTitledBorder("Gestion des Utilisateurs"));
        utilisateursPanel.add(new JLabel("Liste des utilisateurs ici"));  // Remplace par des composants réels
        return utilisateursPanel;
    }

    // Vue des statistiques (placeholder)
    private JPanel createStatistiquesView() {
        JPanel statistiquesPanel = new JPanel();
        statistiquesPanel.setBorder(BorderFactory.createTitledBorder("Statistiques"));
        statistiquesPanel.add(new JLabel("Statistiques ici"));  // Remplace par des graphiques réels
        return statistiquesPanel;
    }

    // Méthode main pour tester
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tableau de Bord de la Bibliothèque");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            frame.add(new DashboardView());
            frame.setVisible(true);
        });
    }
}
