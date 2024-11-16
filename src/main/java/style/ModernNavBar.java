package style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ModernNavBar extends JPanel {

    private static final long serialVersionUID = 1L;

    public ModernNavBar(ActionListener menuListener) {
        // Configuration de la barre de navigation
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(255, 255, 255, 180)); // Effet légèrement transparent
        setPreferredSize(new Dimension(250, getHeight())); // Ajuster la taille de la barre de navigation
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Ajoutez ici le code pour le contenu de la barre de navigation comme avant
        add(createNavBar(menuListener));
    }

    private JPanel createNavBar(ActionListener menuListener) {
        JPanel navBar = new JPanel();
        navBar.setLayout(new BoxLayout(navBar, BoxLayout.Y_AXIS));
        navBar.setBackground(new Color(255, 255, 255, 180)); // Effet légèrement transparent
        navBar.setPreferredSize(new Dimension(250, getHeight()));
        navBar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Logo
        JLabel logo = new JLabel(new ImageIcon(new ImageIcon("C:/Eclipse/gestionbibli/src/main/resources/ressources/logo.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        navBar.add(Box.createRigidArea(new Dimension(0, 20)));
        navBar.add(logo);
        navBar.add(Box.createRigidArea(new Dimension(0, 20)));

        // Photo de profil et nom de l'utilisateur
        JLabel profilePic = new JLabel(new ImageIcon(new ImageIcon("C:/Eclipse/gestionbibli/src/main/resources/ressources/profile.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        profilePic.setPreferredSize(new Dimension(80, 80));
        profilePic.setAlignmentX(Component.CENTER_ALIGNMENT);
        navBar.add(profilePic);
        navBar.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel userName = new JLabel("User    Name", JLabel.CENTER);
        userName.setFont(new Font("SansSerif", Font.BOLD, 18));
        userName.setForeground(new Color(50, 50, 50));
        navBar.add(userName);
        navBar.add(Box.createRigidArea(new Dimension(0, 30)));

        // Options de menu
        navBar.add(createMenuItem("Home", "default-icon", menuListener));
        navBar.add(createMenuItem("Users", "add-icon", menuListener));
        navBar.add(createMenuItem("Livres", "biblio", menuListener));
        navBar.add(createMenuItem("Emprunt", "edit-icon", menuListener));
        navBar.add(createMenuItem("Rapports", "search-icon", menuListener));
        navBar.add(createMenuItem("Paramètres", "settings", menuListener));

        // Espacement bas
        navBar.add(Box.createVerticalGlue());

        return navBar;
    }

    private JPanel createMenuItem(String text, String iconName, ActionListener menuListener) {
        JPanel menuItem = new JPanel();
        menuItem.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        menuItem.setBackground(new Color(255, 255, 255));
        menuItem.setPreferredSize(new Dimension(200, 40));
        menuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuItem.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Redimensionner les icônes
        ImageIcon icon = new ImageIcon("C:/Eclipse/gestionbibli/src/main/resources/ressources/" + iconName + ".png");
        Image img = icon.get Image().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(img));

        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        textLabel.setForeground(new Color(80, 80, 80));

        menuItem.add(iconLabel);
        menuItem.add(textLabel);

        // Ajout de l'écouteur d'événements
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menuListener.actionPerformed(new ActionEvent(menuItem, ActionEvent.ACTION_PERFORMED, text));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setBackground(new Color(255, 182, 193));
                textLabel.setForeground(new Color(0, 120, 215));
                menuItem.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setBackground(new Color(255, 255, 255));
                textLabel.setForeground(new Color(80, 80, 80));
                menuItem.repaint();
            }
        });

        return menuItem;
    }
}