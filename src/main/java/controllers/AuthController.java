package controllers;

import javax.swing.JOptionPane;
import model.User;
import model.UserDAO;
import vue.BibliothequeApp;
import vue.ConnexionView;

public class AuthController {
    private UserDAO userDAO;
    private ConnexionView connexionView;

    public AuthController(ConnexionView connexionView, UserDAO userDAO) {
        this.connexionView = connexionView;
        this.userDAO = userDAO;

        // Action pour le bouton de connexion
        this.connexionView.getLoginButton().addActionListener(e -> seConnecter());
    }

    private void seConnecter() {
        String email = connexionView.getUsername(); // Utiliser getUsername() pour obtenir l'email
        String motDePasse = connexionView.getPassword();

        // Utiliser UserDAO pour authentifier l'utilisateur
        User user = userDAO.authenticate(email, motDePasse);
        if (user != null) {
            // L'utilisateur a été authentifié avec succès
            BibliothequeApp bibliothequeApp = new BibliothequeApp(); // Créer une instance de BibliothequeApp
            bibliothequeApp.setVisible(true); // Afficher la fenêtre principale
            connexionView.setVisible(false); // Fermer la fenêtre de connexion
        } else {
            JOptionPane.showMessageDialog(null, "Email ou mot de passe incorrect.");
        }
    }
}