package com.gestionbibli.app;

import vue.BibliothequeApp;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        // Assurez-vous que l'interface graphique est créée sur le thread de l'EDT
        SwingUtilities.invokeLater(() -> {
            BibliothequeApp app = new BibliothequeApp(); // Créer une instance de BibliothequeApp
            app.setVisible(true); // Afficher la fenêtre principale
        });
    }
}