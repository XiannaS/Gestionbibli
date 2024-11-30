package controllers;

import vue.ConnexionView;
import vue.InscriptionView;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Appliquer le Look and Feel au démarrage
        try {
            // Choisissez ici le thème souhaité : FlatDraculaIJTheme ou FlatLightLaf
            UIManager.setLookAndFeel(new FlatDraculaIJTheme());
            // Vous pouvez aussi essayer `FlatLightLaf` pour un thème clair
            // UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Erreur lors de l'application du thème : " + e.getMessage());
            e.printStackTrace();
        }

        // Initialisation des vues et du contrôleur
        ConnexionView connexionView = new ConnexionView();
        
        InscriptionView inscriptionView = new InscriptionView();
        AuthController authController = new AuthController(connexionView, inscriptionView);

        // Afficher la vue de connexion en premier
        SwingUtilities.invokeLater(() -> connexionView.setVisible(true));
    }
}
