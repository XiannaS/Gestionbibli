package vue;

import controllers.EmpruntController;
import controllers.LivreController;
import controllers.UserController;
import controllers.DashboardController;
import model.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;

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

        // Initialisation des vues
        LivreView livreView = new LivreView();
        EmpruntView empruntView = new EmpruntView();
        UserView userView = new UserView();
        DashboardView dashboardView = new DashboardView();

        // Chargement des fichiers CSV en tant que ressources
        String empruntFilePath = loadResourcePath("data/emprunt.csv");
        String booksFilePath = loadResourcePath("data/books.csv");
        String usersFilePath = loadResourcePath("data/users.csv");

        // Initialisation des contrôleurs
     // Initialisation des contrôleurs
        UserDAO userDAO = new UserDAO(usersFilePath);
        EmpruntController empruntController = new EmpruntController(empruntView, empruntFilePath, booksFilePath, usersFilePath);
        LivreController livreController = new LivreController(livreView, booksFilePath, empruntController);
        UserController userController = new UserController(userView, userDAO);
        DashboardController dashboardController = new DashboardController(dashboardView, empruntController, livreController, userController, userDAO); // Ajout de userDAO ici
        // Création du panneau d'onglets
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Livres", livreView);
        tabbedPane.addTab("Utilisateurs", userView);
        tabbedPane.addTab("Emprunts", empruntView);
        tabbedPane.addTab("Dashboard", dashboardView);

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
        welcomeLabel = new JLabel("Bienvenue, Nom Utilisateur !");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.BLACK); // Couleur de texte par défaut // Ajuster le texte sous l'avatar en réduisant l'espace
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

        // Ajouter les boutons de notification, profil et toggle theme
        notificationButton = new JButton(loadIcon("notification.png"));
        profileButton = new JButton(loadIcon("profile.png"));
        toggleThemeButton = new JButton(loadIcon("mode.png"));

        toggleThemeButton.addActionListener(this::toggleTheme);

        // Panneau pour les boutons à droite
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(notificationButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(toggleThemeButton);

        // Ajouter le panneau de boutons à droite dans le headerPanel
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        // Ajouter le headerPanel et le tabbedPane à la fenêtre
        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    // Méthode pour charger les ressources
    private String loadResourcePath(String resourceName) {
        try {
            // Utilisation de getResource pour obtenir le chemin de la ressource
            String path = getClass().getClassLoader().getResource(resourceName).toURI().getPath();
            
            // Vérifiez si le fichier existe
            File file = new File(path);
            if (!file.exists()) {
                // Si le fichier n'existe pas, créez-le avec les en-têtes appropriés
                createCsvFile(file);
            }
            
            return path;
        } catch (Exception e) {
            // Afficher un message d'erreur si la ressource n'est pas trouvée
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement de la ressource : " + resourceName, "Erreur", JOptionPane.ERROR_MESSAGE);
            return null; // Retourne null si la ressource n'est pas trouvée
        }
    }

    private void createCsvFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Écrire les en-têtes dans le fichier CSV
            if (file.getName().equals("emprunt.csv")) {
                writer.write("id;livreId;userId;dateEmprunt;dateRetourPrevue;dateRetourEffective;rendu;penalite;nombreRenouvellements");
            } else if (file.getName().equals("books.csv")) {
                writer.write("id;titre;auteur;genre;anneePublication;imageUrl;isbn;description;editeur;totalExemplaires");
            } else if (file.getName().equals("users.csv")) {
                writer.write("id;nom;prenom;email;numeroTel;motDePasse;role;statut");
            }
            writer.newLine(); // Ajouter une nouvelle ligne après les en-têtes
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la création du fichier : " + file.getName(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
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

    // Méthode pour charger les fichiers CSV en tant que ressources
    private InputStream loadResourceStream(String resourceName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName);
        if (inputStream == null) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement de la ressource : " + resourceName, "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return inputStream;
    }

    // Bascule entre le mode sombre et le mode clair
    private void toggleTheme(ActionEvent event) {
        try {
            // Basculer entre les thèmes clair et sombre
            if (isDarkMode) {
                UIManager.setLookAndFeel(new FlatLightLaf());
                isDarkMode = false;
            } else {
                UIManager.setLookAndFeel(new FlatDraculaIJTheme());
                isDarkMode = true;
            }
            // Mettre à jour l'interface graphique immédiatement après le changement de thème
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Assurez-vous de lancer l'UI sur le thread de l'Event Dispatching (EDT)
        SwingUtilities.invokeLater(() -> {
            new BibliothequeApp().setVisible(true);
        });
    }
}