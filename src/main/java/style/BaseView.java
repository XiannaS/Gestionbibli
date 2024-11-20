package style;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
import model.User;
import java.awt.*;

public class BaseView extends JFrame {
    private static final long serialVersionUID = 1L;
    private User user; // Définir la variable user

    public BaseView(User user) {
        this.user = user; // Initialiser la variable user
        setTitle("Application de gestion de bibliothèque");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new BorderLayout());

        // Ajout de la barre de navigation
        setNavBar(user);

        // Ajout du header (Search bar + Logout)
        addHeader();

        // Contenu principal (Placeholder)
        addMainContent();

        setVisible(true);
    }

    /**
     * Configure la barre de navigation.
     */
    private void setNavBar(User user) {
        ModernNavBar navBar = new ModernNavBar(user);
        add(navBar, BorderLayout.WEST);
    }

    /**
     * Configure le header avec une search bar et un bouton de déconnexion.
     */
    private void addHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 245)); // Couleur grise claire

        // Search Bar Container
        JPanel searchContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchContainer.setBackground(new Color(245, 245, 245));

        // Ajout de la search bar
        JTextField searchBar = new JTextField();
        searchBar.setFont(new Font("SansSerif", Font.PLAIN, 16));
        searchBar.setPreferredSize(new Dimension(300, 35));
        searchBar.setMargin(new Insets(5, 10, 5, 10)); // Marge interne
        searchBar.setToolTipText("Rechercher...");
        searchContainer.add(searchBar);

        // Ajout d'un bouton "Rechercher"
        JButton searchButton = new JButton("Rechercher");
        searchButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        searchButton.setBackground(new Color(135, 206, 250)); // Bleu clair
        searchButton.setForeground(Color.WHITE);
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

        // Profile & Logout Container
        JPanel profileContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        profileContainer.setBackground(new Color(245, 245, 245));

        // Ajout du profil utilisateur
        JLabel profilePic = new JLabel(new ImageIcon(getClass().getResource("/ressources/profile.png")));
        profileContainer.add(profilePic);

        JLabel userName = new JLabel(user.getNom() + " " + user.getPrenom());
        userName.setFont(new Font("SansSerif", Font.BOLD, 14));
        profileContainer.add(userName);

        // Bouton Logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutButton.setBackground(new Color(255, 105, 97)); // Rouge clair
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(BaseView.this, "Voulez-vous vraiment vous déconnecter ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Implémentez ici la logique de déconnexion
                JOptionPane.showMessageDialog(BaseView.this, "Déconnexion réussie !");
                System.exit(0);
            }
        });
        profileContainer.add(logoutButton);

        headerPanel.add(profileContainer, BorderLayout.EAST);

        // Ajout du header en haut
        add(headerPanel, BorderLayout.NORTH);
    }

    /**
     * Configure le contenu principal de l'application.
     */
    private void addMainContent() {
        JPanel mainContent = new JPanel();
        mainContent.setBackground(new Color(255, 255, 255)); // Blanc
        mainContent.setLayout(new BorderLayout());
        JLabel placeholder = new JLabel("Contenu principal ici", JLabel.CENTER);
        placeholder.setFont(new Font("SansSerif", Font.BOLD, 24));
        placeholder.setForeground(new Color(100, 100, 100)); // Gris foncé
        mainContent.add(placeholder, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Définir le look and feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Créer un utilisateur fictif pour tester l'interface
        User user = new User("Nom", "Prénom", "email@example.com", "motdepasse", "Rôle");

        // Lancer l'application dans le thread de dispatching des événements Swing
        SwingUtilities.invokeLater(() -> {
            BaseView baseView = new BaseView(user);
            baseView.setVisible(true); // Rendre la fenêtre visible
        });
    }
}
