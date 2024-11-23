package style;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class StylishWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private boolean isDarkMode = true; // Flag pour le mode actuel
    private int unreadNotifications = 5; // Nombre de notifications non lues
    private JPopupMenu suggestionsPopup; // Menu popup pour les suggestions de recherche

    public StylishWindow() {
        // Configuration de base de la fenêtre
        setTitle("Card Style Tabs");
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
        JLabel welcomeLabel = new JLabel("Bienvenue, Utilisateur !");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.WHITE);

        // Ajuster le texte sous l'avatar en réduisant l'espace
        welcomePanel.add(userAvatarLabel);
        welcomePanel.add(Box.createVerticalStrut(5));  // Espace entre l'avatar et le texte
        welcomePanel.add(welcomeLabel);

        // Créer un panneau pour aligner l'avatar et le texte à gauche avec un peu d'espace
        JPanel avatarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Espacement entre les éléments
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

        // **Barre de recherche et icônes déplacés ici**
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Alignement au centre
        searchPanel.setOpaque(false); 
        
        JTextField searchBar = new JTextField("Search...");
        searchBar.setPreferredSize(new Dimension(200, 30));
        searchBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        // Suggestions automatiques pour la recherche
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { searchSuggestions(searchBar.getText()); }
            public void removeUpdate(DocumentEvent e) { searchSuggestions(searchBar.getText()); }
            public void changedUpdate(DocumentEvent e) { searchSuggestions(searchBar.getText()); }

            private void searchSuggestions(String query) {
                updateSuggestions(query);
            }
        });

        // Icône de la loupe avec animation de survol
        ImageIcon searchIcon = resizeIcon(new ImageIcon(getClass().getResource("/ressources/search.png")), 30, 30); // Taille de la loupe
        JLabel searchIconLabel = new JLabel(searchIcon);
        
        // Ajouter le listener de survol pour l'effet de zoom
        searchIconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Agrandir l'icône au survol
                searchIconLabel.setIcon(resizeIcon(new ImageIcon(getClass().getResource("/ressources/search.png")), 40, 40));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Rétablir la taille normale lorsque la souris sort
                searchIconLabel.setIcon(resizeIcon(new ImageIcon(getClass().getResource("/ressources/search.png")), 30, 30));
            }
        });

        searchPanel.add(searchIconLabel);
        searchPanel.add(searchBar);

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

        // Ajouter la barre de recherche et les icônes au headerPanel
        JPanel headerRightPanel = new JPanel();
        headerRightPanel.setLayout(new BoxLayout(headerRightPanel, BoxLayout.X_AXIS));
        headerRightPanel.setOpaque(false);
        headerRightPanel.add(searchPanel);
        headerRightPanel.add(iconsPanel);

        // Ajouter tout dans le headerPanel
        headerPanel.add(headerRightPanel, BorderLayout.CENTER);

        // Création de l'onglet avec style de carte
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setUI(new CardTabbedPaneUI());

        // Ajout des onglets
        String[] labels = {"Home", "Loans", "Books", "Members", "Settings", "Rapport", "Rappels", "Messages"};
        for (String label : labels) {
            JLabel content = new JLabel("Contenu de l'onglet " + label, SwingConstants.CENTER);
            content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            tabbedPane.addTab(label, content);
        }
        
        // Ajout de la fenêtre
        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        // Appliquer le thème Dracula par défaut
        applyDraculaTheme();

        setVisible(true);
    }

    private void updateSuggestions(String query) {
        // Création du popup de suggestions
        if (suggestionsPopup == null) {
            suggestionsPopup = new JPopupMenu();
        } else {
            suggestionsPopup.removeAll();
        }

        if (query.length() > 0) {
            String[] suggestions = getSearchSuggestions(query);
            for (String suggestion : suggestions) {
                JMenuItem item = new JMenuItem(suggestion);
                item.addActionListener(e -> System.out.println("Suggestion sélectionnée : " + suggestion));
                suggestionsPopup.add(item);
            }
            suggestionsPopup.show(this, 400, 50);  // Position du popup
        }
    }

    private String[] getSearchSuggestions(String query) {
        // Retourne des suggestions basées sur la recherche
        return new String[]{query + " Result 1", query + " Result 2", query + " Result 3"};
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage(); // Obtient l'image
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH); // Redimensionne l'image
        return new ImageIcon(newImg); // Retourne l'image redimensionnée
    }

    private void toggleTheme() {
        try {
            if (isDarkMode) {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } else {
                UIManager.setLookAndFeel(new FlatDraculaIJTheme());
            }
            isDarkMode = !isDarkMode;
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void applyDraculaTheme() {
        try {
            UIManager.setLookAndFeel(new FlatDraculaIJTheme());
            isDarkMode = true;
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StylishWindow view = new StylishWindow();
            view.setVisible(true);
        });
    }
}

class CardTabbedPaneUI extends BasicTabbedPaneUI {
    private static final int ARC = 30; // Coins encore plus arrondis

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Création du dégradé rose à gauche et couleur foncée à droite
        Color color1 = new Color(255, 105, 180); // Rose
        Color color2 = new Color(45, 45, 45); // Gris foncé
        GradientPaint gradient = new GradientPaint(x, y, color1, x + w, y, color2);
        g2.setPaint(gradient);

        // Dessiner le tab avec le dégradé
        Shape shape = createTabShape(x, y, w, h);
        g2.fill(shape);

        // Ombre douce sous les onglets (si non sélectionné)
        if (!isSelected) {
            g2.setColor(new Color(0, 0, 0, 50)); // Ombre subtile
            g2.fill(new RoundRectangle2D.Double(x + 2, y + 2, w, h, ARC, ARC));
        }
    }

    private Shape createTabShape(int x, int y, int w, int h) {
        return new RoundRectangle2D.Double(x, y, w, h, ARC, ARC); // Forme arrondie
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        // Pas de bordure pour un look plus net
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        // Pas d'indicateur de focus pour un look plus épuré
    }
}
