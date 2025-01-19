package vue;

import javax.swing.*;
import java.awt.*;

public class ConnexionView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public ConnexionView() {
        setTitle("Connexion - Gestion de Bibliothèque");
        setSize(500, 350); // Taille initiale
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran

        // Charger le logo depuis les ressources
        ImageIcon logoIcon = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));
        // Redimensionner le logo pour qu'il soit plus petit
        Image scaledLogo = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledLogo);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        // Créer un panneau principal avec un BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Marge autour du panneau
        mainPanel.setBackground(new Color(245, 245, 245)); // Fond clair

        // Créer un panneau pour le logo et le texte de bienvenue
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(new Color(245, 245, 245)); // Fond clair

        // Ajouter le logo
        headerPanel.add(logoLabel, BorderLayout.NORTH);

        // Ajouter un texte de bienvenue
        JLabel welcomeLabel = new JLabel("Bienvenue dans l'application de gestion de bibliothèque");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(new Color(0, 123, 255)); // Couleur bleue
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        // Ajouter le panneau d'en-tête au panneau principal
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Créer un panneau pour le formulaire de connexion
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245)); // Fond clair

        // Configuration des contraintes pour le GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement entre les composants
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal

        // Étiquette et champ pour l'email
        JLabel usernameLabel = new JLabel("Email:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(200, 30)); // Taille fixe pour le champ
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Permet au champ de s'étirer horizontalement
        formPanel.add(usernameField, gbc);

        // Étiquette et champ pour le mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0; // Réinitialiser le poids pour l'étiquette
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30)); // Taille fixe pour le champ
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Permet au champ de s'étirer horizontalement
        formPanel.add(passwordField, gbc);

        // Bouton de connexion
        loginButton = new JButton("Se connecter");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 123, 255)); // Couleur bleue
        loginButton.setForeground(Color.RED);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding du bouton
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Le bouton occupe deux colonnes
        gbc.anchor = GridBagConstraints.CENTER; // Centrer le bouton
        formPanel.add(loginButton, gbc);

        // Ajouter le panneau du formulaire au centre du panneau principal
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Ajouter le panneau principal à la fenêtre
        add(mainPanel);

        // Rendre la fenêtre visible
        setVisible(true);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public JButton getLoginButton() {
        return loginButton;
    }

  
}