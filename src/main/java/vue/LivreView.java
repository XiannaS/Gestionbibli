package vue;

import controllers.LivreController;
import model.Livre;
import style.ModernNavBar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class LivreView extends JFrame {

    private final LivreController livreController = new LivreController();
    private final JPanel bookPanel = new JPanel(new GridLayout(0, 4, 20, 20)); // Grille pour afficher les livres

    public LivreView() {
        // Configuration de la fenêtre principale
        setTitle("Library Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);

        // Barre de navigation
        ModernNavBar navBar = new ModernNavBar(); // Créer une instance de la barre de navigation
        navBar.setPreferredSize(new Dimension(250, getHeight())); // Ajuster la taille de la barre de navigation

        // Barre supérieure
        JPanel topBar = createTopBar();

        // Contenu principal
        JScrollPane mainContent = createMainContent();

        // Organisation de la disposition
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navBar, mainContent);
        splitPane.setDividerSize(0); // Pas de séparateur visible
        splitPane.setDividerLocation(250);

        add(topBar, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        // Chargement initial des livres
        updateBookCards();
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        JTextField searchField = new JTextField("Search...");
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JButton searchButton = new JButton("🔍");
        searchButton.setPreferredSize(new Dimension(50, 30));

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        JLabel profileLabel = new JLabel("👤 Profile");
        JLabel notificationLabel = new JLabel("🔔");

        JPanel iconsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        iconsPanel.add(notificationLabel);
        iconsPanel.add(profileLabel);

        topBar.add(searchPanel, BorderLayout.WEST);
        topBar.add(iconsPanel, BorderLayout.EAST);

        return topBar;
    }

    private JScrollPane createMainContent() {
        bookPanel.setBackground(new Color(250, 250, 255));
        JScrollPane scrollPane = new JScrollPane(bookPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }

    private void updateBookCards() {
        List<Livre> livres = livreController.lireLivres();
        bookPanel.removeAll();
        for (Livre livre : livres) {
            JPanel card = createBookCard(livre);
            bookPanel.add(card);
        }
        bookPanel.revalidate();
        bookPanel.repaint();
    }

    private JPanel createBookCard(Livre livre) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card.setMaximumSize(new Dimension(200, 300));

        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(loadBookImage(livre.getImageUrl(), 150, 200));

        JLabel titleLabel = new JLabel(livre.getTitre());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel authorLabel = new JLabel("By " + livre.getAuteur());
        authorLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            livreController.supprimerLivre(livre.getId());
            updateBookCards();
        });

        card.add(imageLabel, BorderLayout.CENTER);
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(authorLabel, BorderLayout.SOUTH);
        card.add(deleteButton, BorderLayout.EAST);

        return card;
    }

    private ImageIcon loadBookImage(String path, int width, int height) {
        try {
            Image img = new ImageIcon(path).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showAddBookDialog() {
        JDialog dialog = new JDialog(this, "Add Book", true);
        // Récupérez les détails comme dans le code précédent
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LivreView app = new LivreView();
            app.setVisible(true);
        });
    }
}