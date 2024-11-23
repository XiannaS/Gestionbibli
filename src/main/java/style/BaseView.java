package style;


import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;

public class BaseView extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseView() {
        // Configuration de base de la fenêtre
        setTitle("Application de Gestion de Bibliothèque");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null); // Centrer la fenêtre
        setLayout(new BorderLayout());

        // Création de l'en-tête
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 245)); // Couleur grise claire

        // Conteneur de la barre de recherche
        JPanel searchContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchContainer.setBackground(new Color(245, 245, 245));

        // Ajout de la search bar avec bordures arrondies
        JTextField searchBar = new JTextField();
        searchBar.setFont(new Font("SansSerif", Font.PLAIN, 16));
        searchBar.setPreferredSize(new Dimension(300, 35));
        searchBar.setMargin(new Insets(5, 10, 5, 10)); // Marge interne
        searchBar.setToolTipText("Rechercher...");
        searchContainer.add(searchBar);

        // Ajout d'un bouton "Rechercher" avec une icône
        ImageIcon searchIcon = new ImageIcon(new ImageIcon(getClass().getResource("/ressources/search-icon.png"))
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)); // Redimensionner l'icône
        JButton searchButton = new JButton(searchIcon);
        searchButton.setBackground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createEmptyBorder());
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.addActionListener(e -> {
            String query = searchBar.getText();
            if (!query.isEmpty()) {
                JOptionPane.showMessageDialog(BaseView.this, "Recherche pour : " + query);
                // Implémentez ici la logique de recherche
            } else {
                JOptionPane.showMessageDialog(BaseView.this, "Veuillez entrer un mot-clé pour rechercher !");
            }
        });
        searchContainer.add(searchButton);

        headerPanel.add(searchContainer, BorderLayout.WEST);

        // Profil utilisateur dans l'en-tête
        JPanel profileContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        profileContainer.setBackground(new Color(245, 245, 245));

        // Ajout de l'icône de profil
        ImageIcon profileIcon = new ImageIcon(new ImageIcon(getClass().getResource("/ressources/profile.png"))
                .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)); // Redimensionner l'icône
        JLabel profilePic = new JLabel(profileIcon);
        profilePic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profilePic.setToolTipText("Cliquez pour plus d'options");

        // Ajout de la cloche de notification
        ImageIcon bellIcon = new ImageIcon(new ImageIcon(getClass().getResource("/ressources/notification.png"))
                .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)); // Redimensionner l'icône
        JLabel bellLabel = new JLabel(bellIcon);
        bellLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bellLabel.setToolTipText("Notifications");

        // Ajouter le nom d'utilisateur à côté de l'icône
        JLabel userName = new JLabel("Bienvenue, Utilisateur");
        userName.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userName.setForeground(new Color(50, 50, 50)); // Couleur noire

        // Menu contextuel pour le profil utilisateur
        JPopupMenu profileMenu = new JPopupMenu();
        JMenuItem logoutItem = new JMenuItem("Se déconnecter");
        logoutItem.setFont(new Font("SansSerif", Font.PLAIN, 14));
        logoutItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(BaseView.this, "Voulez-vous vraiment vous déconnecter ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(BaseView.this, "Déconnexion réussie !");
                System.exit(0);
            }
        });
        profileMenu.add(logoutItem);

        // Afficher le menu lors du clic sur l'icône de profil
        profilePic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileMenu.show(profilePic, evt.getX(), evt.getY());
            }
        });

        // Ajouter les composants du profil
        profileContainer.add(bellLabel);
        profileContainer.add(userName);
        profileContainer.add(profilePic);

        // Ajouter le conteneur de profil à l'en-tête
        headerPanel.add(profileContainer, BorderLayout.EAST);

        // Ajouter l'en-tête à la fenêtre
        add(headerPanel, BorderLayout.NORTH);

        // Zone de contenu principale
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());
        JLabel mainLabel = new JLabel("Bienvenue dans votre tableau de bord", SwingConstants.CENTER);
        mainLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        mainPanel.add(mainLabel, BorderLayout.CENTER);

        // Ajouter le contenu principal à la fenêtre
        add(mainPanel, BorderLayout.CENTER);

        // Pied de page
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(245, 245, 245)); // Couleur grise claire
        JLabel footerLabel = new JLabel("© 2024 Mon Application. Tous droits réservés.", SwingConstants.CENTER);
        footerLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footerPanel.add(footerLabel);

        // Ajouter le pied de page
        add(footerPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        // Lancer l'application
        SwingUtilities.invokeLater(() -> {
            // Appliquer FlatLaf pour un look moderne
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            BaseView baseView = new BaseView();
            baseView.setVisible(true);
        });
    }
}