package style;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class ModernNavBar extends JPanel {
    private static final long serialVersionUID = 1L;

    public ModernNavBar() {
        // Configuration de la barre de navigation
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(255, 182, 193)); // Couleur rose pastel clair
        setPreferredSize(new Dimension(250, 800)); // Largeur fixe
        setBorder(new RoundedBorder(new Color(255, 182, 193), 20)); // Bordure arrondie

        // Logo
        JLabel logo = createImageLabel("/ressources/logo.png", 100, 100);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(0, 20)));
        add(logo);

        // Photo de profil et nom utilisateur
        JLabel profilePic = createImageLabel("/ressources/profile.png", 80, 80);
        profilePic.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userName = new JLabel("User   Name", JLabel.CENTER);
        userName.setFont(new Font("SansSerif", Font.BOLD, 16));
        userName.setForeground(new Color(50, 50, 50));

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(profilePic);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(userName);

        add(Box.createRigidArea(new Dimension(0, 30)));

        // Menu Items
        add(createMenuItem("Home", "/ressources/default-icon.png"));
        add(createMenuItem("Users", "/ressources/add-icon.png"));
        add(createMenuItem("Livres", "/ressources/biblio.png"));
        add(createMenuItem("Emprunt", "/ressources/edit-icon.png"));
        add(createMenuItem("Rapports", "/ressources/search-icon.png"));
        add(createMenuItem("Paramètres", "/ressources/settings.png"));

        add(Box.createVerticalGlue());
    }

    private JPanel createMenuItem(String text, String iconPath) {
        // Panel pour chaque option
        JPanel menuItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        menuItem.setBackground(Color.WHITE);
        menuItem.setPreferredSize(new Dimension(200, 40));
        menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menuItem.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Marge interne
        menuItem.setOpaque(false);

        // Ajout des coins arrondis
        menuItem.setBorder(new RoundedBorder(new Color(255, 255, 255), 10));

        // Icône
        JLabel iconLabel = createImageLabel(iconPath, 20, 20);

        // Texte
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Ajout d'événements
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(menuItem, "Clicked: " + text);
                // Implémentez ici le changement de contenu
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setBackground(new Color(255, 200, 200)); // Effet survol
                menuItem.setBorder(new RoundedBorder(new Color(255, 200, 200), 10));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setBackground(Color.WHITE); // Retour à l'état normal
                menuItem.setBorder(new RoundedBorder(Color.WHITE, 10));
            }
        });

        menuItem.add(iconLabel);
        menuItem.add(textLabel);
        return menuItem;
    }

    private JLabel createImageLabel(String resourcePath, int width, int height) {
        // Chargement des images avec ClassLoader
        URL resourceUrl = getClass().getResource(resourcePath);
        if (resourceUrl == null) {
            System.err.println("Image non trouvée à : " + resourcePath);
            return new JLabel(); // Retourne un JLabel vide si l'image n'est pas trouvée
        }
        
        ImageIcon icon = new ImageIcon(resourceUrl);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(scaledImage));
    }

    // Classe interne pour créer des bordures arrondies
    private static class RoundedBorder extends AbstractBorder {
 
        private static final long serialVersionUID = 1L;
        private final Color backgroundColor;
        private final int cornerRadius;

        public RoundedBorder(Color backgroundColor, int cornerRadius) {
            this.backgroundColor = backgroundColor;
            this.cornerRadius = cornerRadius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(backgroundColor);
            g2d.fillRoundRect(x, y, width - 1, height - 1, cornerRadius, cornerRadius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(cornerRadius, cornerRadius, cornerRadius, cornerRadius);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = cornerRadius;
            return insets;
        }
    }
}