package style;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;

import model.User;
import vue.DashboardView;
import vue.EmpruntView;
import vue.LivreView;
import vue.ParamètresView;
import vue.RapportView;
import vue.UserView;
import vue.MessagesView;
import vue.RemindersView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StylishWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private boolean isDarkMode = true; // Flag pour le mode actuel
    private int unreadNotifications = 5; // Nombre de notifications non lues
    private User user; 
    
    public StylishWindow(User user) {
        // Configuration de base de la fenêtre
        setTitle("Card Style Tabs");
        this.user = user;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600); // Taille augmentée
        setLocationRelativeTo(null); // Centrer la fenêtre
        setLayout(new BorderLayout());

        // Panneau pour le logo et la barre de titre
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        // Panneau pour le logo à gauche
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Alignement à gauche sans espace
        logoPanel.setOpaque(false); 
        
        // Ajout du logo au panneau
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/ressources/logo.png"));
        if (logoIcon.getIconWidth() == -1) {
            System.err.println("Logo non trouvé !");
        } else {
            // Redimensionner le logo
            Image logoImage = logoIcon.getImage().getScaledInstance(50, 25, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
            logoPanel.add(logoLabel);
        }

        // Panneau pour afficher l'avatar et le message de bienvenue
        JPanel welcomePanel = new JPanel();
        welcomePanel.setOpaque(false);
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS)); // Utilisation de BoxLayout pour empiler verticalement

        // Icône de l'avatar utilisateur
        ImageIcon userAvatarIcon = new ImageIcon(getClass().getResource("/ressources/profile.png"));
        Image userAvatarImage = userAvatarIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Redimensionner l'avatar
        JLabel userAvatarLabel = new JLabel(new ImageIcon(userAvatarImage));

        // Message de bienvenue sous l'avatar
        JLabel welcomeLabel = new JLabel("Bienvenue, " + user.getNom() + " " + user.getPrenom() + " !");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.WHITE);
        
        // Ajuster le texte sous l'avatar en réduisant l'espace
        welcomePanel.add(userAvatarLabel);
        welcomePanel.add(Box.createVerticalStrut(5));  // Espace entre l'avatar et le texte
        welcomePanel.add(welcomeLabel);

        // Créer un panneau pour aligner l'avatar et le texte à gauche avec un peu d'espace
        JPanel avatarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20)); // Espacement entre les éléments
        avatarPanel.setOpaque(false);
        avatarPanel.add(welcomePanel);

        // Créer un panneau principal pour le logo et le panneau d'avatar
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // Utiliser BoxLayout pour empiler
        topPanel.setOpaque(false);
        topPanel.add(logoPanel);
        topPanel.add(Box.createVerticalStrut(10)); // Ajouter un petit espace entre le logo et l'avatar
        topPanel.add(avatarPanel);

        // Ajouter le panneau principal dans le headerPanel, en le collant à gauche
        headerPanel.add(topPanel, BorderLayout.WEST);

        // Icônes sans fond
        ImageIcon modeIcon = resizeIcon(new ImageIcon(getClass().getResource("/ressources/mode.png")), 24, 24);
        ImageIcon notificationIcon = resizeIcon(new ImageIcon(getClass().getResource("/ressources/notification.png")), 24, 24);

        JLabel modeLabel = new JLabel(modeIcon);
        modeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        modeLabel.setToolTipText("Basculez entre le mode clair et le mode sombre");
        modeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleTheme(); // Basculer entre les modes
            }
        });

        JLabel notificationLabel = new JLabel(notificationIcon);
        notificationLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        notificationLabel.setToolTipText("Notifications");

        // Badge pour notifications non lues
        JLabel notificationBadge = new JLabel(String.valueOf(unreadNotifications));
        notificationBadge.setForeground(Color.RED);
        notificationBadge.setFont(new Font("Arial", Font.BOLD, 12));
        notificationBadge.setBounds(20, 0, 20, 20);
        notificationLabel.add(notificationBadge);

        // Panneau pour les icônes
        JPanel iconsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10)); // Alignement à droite
        iconsPanel.setOpaque(false); 
        iconsPanel.add(modeLabel);
        iconsPanel.add(notificationLabel);

        // Ajouter les icônes au headerPanel
        headerPanel.add(iconsPanel, BorderLayout.EAST);

        // Création du panneau des onglets (à gauche)
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT); // Onglets verticaux à gauche
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14)); 
        
        
        // Ajout des onglets avec des vues spécifiques en fonction du rôle
        tabbedPane.addTab("Home", new DashboardView(user));
        if ("Bibliothécaire".equals(user.getRole())) {
            tabbedPane.addTab("Books", new LivreView(this, user));
            tabbedPane.addTab("Members", new UserView());
            tabbedPane.addTab("Loans", new EmpruntView());
            tabbedPane.addTab("Settings", new ParamètresView());
            tabbedPane.addTab("Rapport", new RapportView());
            tabbedPane.addTab("Rappels", new RemindersView());
            tabbedPane.addTab("Messages", new MessagesView());
        } else if ("Membre".equals(user.getRole())) {
            tabbedPane.addTab("Books", new LivreView(this, user));
            // D'autres onglets spécifiques aux membres peuvent être ajoutés ici
        }

        // Ajout de la fenêtre
        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        // Appliquer le thème Dracula par défaut
        applyDraculaTheme();

        setVisible(true);
    }

    public boolean isDarkMode() {
        return isDarkMode;
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage(); // Obtient l'image
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH); // Redimensionne l'image
        return new ImageIcon(newImg); // Retourne l'image redimensionnée
    }

    public void toggleTheme() {
        try {
            // Si le thème est déjà activé, on ne fait rien
            if (isDarkMode) {
                if (!(UIManager.getLookAndFeel() instanceof FlatLightLaf)) {
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    SwingUtilities.updateComponentTreeUI(this);
                }
            } else {
                if (!(UIManager.getLookAndFeel() instanceof FlatDraculaIJTheme)) {
                    UIManager.setLookAndFeel(new FlatDraculaIJTheme());
                    SwingUtilities.updateComponentTreeUI(this);
                }
            }
            isDarkMode = !isDarkMode;
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void applyDraculaTheme() {
        try {
            // Si le thème actuel n'est pas déjà le Dracula, l'appliquer
            if (!(UIManager.getLookAndFeel() instanceof FlatDraculaIJTheme)) {
                UIManager.setLookAndFeel(new FlatDraculaIJTheme());
                isDarkMode = true;
                SwingUtilities.updateComponentTreeUI(this);
            }
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatDraculaIJTheme()); // Appliquer avant toute création
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
            User currentUser = new User("John", "Doe", "john.doe@example.com", "password123", "Bibliothécaire");
            new StylishWindow(currentUser); // Créer la fenêtre après avoir appliqué le Look and Feel
        });
    }


}