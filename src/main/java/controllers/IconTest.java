package controllers;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class IconTest {

    public static void main(String[] args) {
        // Créer une fenêtre pour afficher les icônes
        JFrame frame = new JFrame("Test des icônes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        // Tester le chargement d'icônes
        try {
            // Exemple d'icône valide (chemin relatif dans resources)
            ImageIcon validIcon = loadIcon("/ressources/add-icon.png");
            JLabel validIconLabel = new JLabel("Icône valide");
            validIconLabel.setIcon(resizeIcon(validIcon, 50, 50));

            // Exemple d'icône invalide (pour tester le fallback)
            ImageIcon invalidIcon = loadIcon("/ressources/invalid-icon.png");
            JLabel invalidIconLabel = new JLabel("Icône invalide");
            invalidIconLabel.setIcon(resizeIcon(invalidIcon, 50, 50));

            // Ajouter les icônes à la fenêtre
            frame.add(validIconLabel);
            frame.add(invalidIconLabel);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des icônes: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        frame.setVisible(true);
    }

    // Méthode pour charger une icône (avec vérification de l'existence)
    private static ImageIcon loadIcon(String path) {
        // Utilisation du class loader pour accéder à la ressource
        try {
            URL iconUrl = IconTest.class.getResource(path);
            if (iconUrl != null) {
                return new ImageIcon(iconUrl);
            } else {
                System.err.println("Icône non trouvée à: " + path);
                return new ImageIcon(IconTest.class.getResource("/ressources/default-icon.png")); // Icône par défaut
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'icône: " + e.getMessage());
            return new ImageIcon(); // Retourne une icône vide en cas d'erreur
        }
    }

    // Méthode pour redimensionner une icône
    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        if (icon == null || icon.getImage() == null) {
            System.err.println("Icône non valide, utilisation de l'icône par défaut.");
            icon = new ImageIcon(IconTest.class.getResource("/ressources/default-icon.png"));
        }
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
