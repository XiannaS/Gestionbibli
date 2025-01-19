package vue;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class BibliothequeApp extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTabbedPane tabbedPane;
    private JButton toggleThemeButton;
    private JButton profileButton;
    private JButton notificationButton;
    private JLabel welcomeLabel;
    private boolean isDarkMode = false; // Mode clair par défaut

    public BibliothequeApp() {
        // Appliquer le thème clair par défaut
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setTitle("Gestion de Bibliothèque");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création du panneau d'onglets
        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        add(tabbedPane, BorderLayout.CENTER);

        // Création du header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        // Panneau pour le logo à gauche
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Alignement à gauche sans espace
        logoPanel.setOpaque(false);

        // Ajout du logo au panneau
        ImageIcon logoIcon = loadIcon("logo.png");
        if (logoIcon != null) {
            // Redimensionner le logo
            Image logoImage = logoIcon.getImage().getScaledInstance(50, 25, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
            logoPanel.add(logoLabel);
        }

        // Panneau pour afficher l'avatar et le message de bienvenue
        JPanel welcomePanel = new JPanel();
        welcomePanel.setOpaque(false);
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS)); // Utilisation de BoxLayout pour empiler verticalement

        // Icône de l'avatar utilisateur
        ImageIcon userAvatarIcon = loadIcon("profile.png");
        if (userAvatarIcon != null) {
            Image userAvatarImage = userAvatarIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Redimensionner l'avatar
            JLabel userAvatarLabel = new JLabel(new ImageIcon(userAvatarImage));
            welcomePanel.add(userAvatarLabel);
        }

        // Message de bienvenue sous l'avatar
        welcomeLabel = new JLabel("Bienvenue !");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.BLACK); // Couleur de texte par défaut
        welcomePanel.add(Box.createVerticalStrut(5));  // Espace entre l'avatar et le texte
        welcomePanel.add(welcomeLabel);

        // Créer un panneau pour aligner l'avatar et le texte à gauche avec un peu d'espace
        JPanel avatarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20)); // Espacement entre les éléments
        avatarPanel.setOpaque(false);
        avatarPanel.add(welcomePanel);

        // Créer un panneau principal pour le logo et le panneau d'avatar
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // Utiliser BoxLayout pour empiler
        topPanel.setOpaque(false);
        topPanel.add(logoPanel);
        topPanel.add(Box.createVerticalStrut(10)); // Ajouter un petit espace entre le logo et l'avatar
        topPanel.add(avatarPanel);

        // Ajouter le panneau principal dans le headerPanel, en le collant à gauche
        headerPanel.add(topPanel, BorderLayout.WEST);

        // Initialisation des boutons de notification, profil et toggle theme
        notificationButton = new JButton(loadIcon("notification.png"));
        profileButton = new JButton(loadIcon("profile.png"));
        toggleThemeButton = new JButton(loadIcon("mode.png"));

        // Panneau pour les boutons à droite
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(notificationButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(toggleThemeButton);

        // Ajouter le panneau de boutons à droite dans le headerPanel
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        // Ajouter le headerPanel à la fenêtre
        add(headerPanel, BorderLayout.NORTH);
    }

    // Méthode pour charger les icônes en toute sécurité
    private ImageIcon loadIcon(String resourceName) {
        try {
            URL resourceUrl = getClass().getClassLoader().getResource(resourceName);
            if (resourceUrl == null) {
                throw new IOException("Resource not found: " + resourceName);
            }
            return new ImageIcon(
                new ImageIcon(resourceUrl)
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)
            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Icône introuvable : " + resourceName, "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Getters pour les boutons
    public JButton getToggleThemeButton() {
        return toggleThemeButton;
    }

    public JButton getProfileButton() {
        return profileButton;
    }

    public JButton getNotificationButton() {
        return notificationButton;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JLabel getWelcomeLabel() {
        return welcomeLabel;
    }

    public boolean isDarkMode() {
        return isDarkMode;
    }

    public void setDarkMode(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
    }
}