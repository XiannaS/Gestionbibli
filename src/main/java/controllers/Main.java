package controllers;

import javax.swing.*;
import vue.EmpruntView;

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
            // Créer l'instance de EmpruntView et l'afficher
            EmpruntView empruntView = new EmpruntView();
            empruntView.setVisible(true); // Afficher la fenêtre des emprunts
        });
    }
}
