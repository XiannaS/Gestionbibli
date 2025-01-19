package controllers;

import model.UserDAO;
import vue.BibliothequeApp;
import vue.DashboardView;
import vue.EmpruntView;
import vue.LivreView;
import vue.UserView;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BibliothequeAppController {
    private BibliothequeApp bibliothequeApp;
    private LivreView livreView;
    private EmpruntView empruntView;
    private UserView userView;
    private DashboardView dashboardView;
    private UserDAO userDAO;

    public BibliothequeAppController(BibliothequeApp bibliothequeApp) {
        this.bibliothequeApp = bibliothequeApp;

        // Initialisation des vues
        livreView = new LivreView();
        empruntView = new EmpruntView();
        userView = new UserView();
        dashboardView = new DashboardView();

        // Chargement des fichiers CSV en tant que ressources
        String usersFilePath = loadResourcePath("data/users.csv");
        String empruntFilePath = loadResourcePath("data/emprunt.csv");
        String booksFilePath = loadResourcePath("data/books.csv");

        userDAO = new UserDAO(usersFilePath);

        // Initialisation des contrôleurs
        EmpruntController empruntController = new EmpruntController(empruntView, empruntFilePath, booksFilePath, usersFilePath);
        LivreController livreController = new LivreController(livreView, booksFilePath, empruntController);
        UserController userController = new UserController(userView, userDAO);
        DashboardController dashboardController = new DashboardController(dashboardView, empruntController, livreController, userController);

        // Ajout des vues au panneau d'onglets
        addTabs();

        // Gestion des événements
        bibliothequeApp.getToggleThemeButton().addActionListener(this::toggleTheme);
        bibliothequeApp.getProfileButton().addActionListener(this::showProfileDialog);
        bibliothequeApp.getNotificationButton().addActionListener(this::showNotificationDialog);
    }

    private void addTabs() {
        // Check if tabs already exist before adding them
        if (bibliothequeApp.getTabbedPane().getTabCount() == 0) { 
        	bibliothequeApp.getTabbedPane().addTab("Dashboard", dashboardView);
            bibliothequeApp.getTabbedPane().addTab("Livres", livreView);
            bibliothequeApp.getTabbedPane().addTab("Utilisateurs", userView);
            bibliothequeApp.getTabbedPane().addTab("Emprunts", empruntView);
           
        }
    }

    private void showProfileDialog(ActionEvent event) {
        // Display a dialog with user profile information
        String profileInfo = "Nom: John Doe\nEmail: john.doe@example.com\nTéléphone: 123-456-7890";
        JOptionPane.showMessageDialog(bibliothequeApp, profileInfo, "Profil Utilisateur", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showNotificationDialog(ActionEvent event) {
        // Display a dialog indicating there are no new notifications
        JOptionPane.showMessageDialog(bibliothequeApp, "Vous n'avez pas de nouvelles notifications.", "Notifications", JOptionPane.INFORMATION_MESSAGE);
    }

    private void toggleTheme(ActionEvent event) {
        try {
            if (bibliothequeApp.isDarkMode()) {
                UIManager.setLookAndFeel(new FlatLightLaf());
                bibliothequeApp.setDarkMode(false);
            } else {
                UIManager.setLookAndFeel(new FlatDraculaIJTheme());
                bibliothequeApp.setDarkMode(true);
            }
            SwingUtilities.updateComponentTreeUI(bibliothequeApp);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private String loadResourcePath(String resourceName)
    {
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
            // Ne pas afficher de message d'erreur, juste retourner null
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
            JOptionPane.showMessageDialog(null, "Erreur lors de la création du fichier : " + file.getName(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}