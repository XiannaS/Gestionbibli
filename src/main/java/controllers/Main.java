package controllers;

import javax.swing.*;

import vue.LivreView;



public class Main {
    public static void main(String[] args) {
        // S'assurer que l'application utilise le Look and Feel de la plateforme
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();  // En cas d'erreur, utiliser l'apparence par défaut
        }

        // Créer et afficher l'interface utilisateur
        SwingUtilities.invokeLater(() -> {
            LivreView livreView = new LivreView(); // Création de l'instance de la vue
            livreView.setVisible(true); // Affichage de la fenêtre

        });
    }
}
