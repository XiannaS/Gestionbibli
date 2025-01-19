package controllers;

import model.UserDAO;
import vue.BibliothequeApp;
import vue.ConnexionView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ConnexionController {
    private ConnexionView connexionView;
    private UserDAO userDAO;

    public ConnexionController(ConnexionView connexionView, UserDAO userDAO) {
        this.connexionView = connexionView;
        this.userDAO = userDAO;

        // Add action listener for the login button
        connexionView.getLoginButton().addActionListener(this::handleLogin);
    }

    private void handleLogin(ActionEvent event) {
        String username = connexionView.getUsername().trim(); // Trim to remove leading/trailing spaces
        String password = connexionView.getPassword().trim(); // Trim to remove leading/trailing spaces

        System.out.println("Attempting login with Username: " + username + " and Password: " + password);

        // Validate user credentials  
        if (userDAO.validateUser (username, password)) {
            System.out.println("Login successful!");
            // If login is successful, switch to BibliothequeApp
            SwingUtilities.invokeLater(() -> {
                BibliothequeApp app = new BibliothequeApp();
                new BibliothequeAppController(app); // Initialize the controller with the app instance
                app.setVisible(true);
                // Close the login window
                
                connexionView.dispose();
            });
        } else {
            System.out.println("Login failed.");
            JOptionPane.showMessageDialog(connexionView, "Nom d'utilisateur ou mot de passe incorrect.", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
        }
    }
    }