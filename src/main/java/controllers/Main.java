package controllers;

import javax.swing.*;
import java.awt.*;
import style.ModernNavBar;

public class Main {

    public static void main(String[] args) {
        // Créer la fenêtre principale (JFrame)
        JFrame frame = new JFrame("Gestion Bibliothèque");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800); // Taille de la fenêtre principale
        frame.setLocationRelativeTo(null); // Centrer la fenêtre à l'écran

        // Créer un panneau principal (par exemple, un DashboardView)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JLabel label = new JLabel("Bienvenue dans l'application de gestion de bibliothèque", JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
        mainPanel.add(label, BorderLayout.CENTER);

        // Créer une instance de ModernNavBar et passer l'ActionListener
        ModernNavBar navBar = new ModernNavBar();

        // Ajouter la barre de navigation à la fenêtre à gauche (utilisation de BorderLayout)
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(navBar, BorderLayout.WEST); // Ajouter la barre de navigation à l'ouest
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER); // Ajouter le panneau principal au centre

        // Afficher la fenêtre
        frame.setVisible(true);
    }
}
