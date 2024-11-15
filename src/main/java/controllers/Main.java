package controllers;

import javax.swing.SwingUtilities;

import vue.EmpruntView;
import vue.LivreView;

public class Main {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater garantit que l'interface graphique est créée sur le thread Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {
            // Créer une instance de LivreView et la rendre visible
            LivreView livreView = new LivreView();
            livreView.setVisible(true);
            EmpruntView empruntView = new EmpruntView();
            empruntView.setVisible(true);
        });
    }
}