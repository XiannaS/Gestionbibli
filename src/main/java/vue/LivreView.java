package vue;

import controllers.LivreController;
import model.Livre;
import model.User;
import style.SearchBar;
import style.StylishWindow;
import javax.swing.*;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class LivreView extends JPanel {
    private static final long serialVersionUID = 1L;
    private LivreController livreController;
    private JButton addButton;
    private SearchBar searchBar; 
    private JScrollPane scrollPane; 
    private StylishWindow parentWindow;
    private User user; 

    public LivreView(StylishWindow parentWindow, User user) {
        this.parentWindow = parentWindow;
        livreController = new LivreController();
        this.user = user; 
        setLayout(new BorderLayout());

        // Déterminer le rôle de l'utilisateur
        String userRole = user.getRole();
		// Initialiser SearchBar
        searchBar = new SearchBar(userRole); 
        searchBar.addSearchListener((searchText, genre, year, isAvailable, isUnavailable) -> {
            List<Livre> filteredBooks = livreController.filtrerLivres(searchText.toLowerCase(), isAvailable, isUnavailable, genre);
            chargerLivres(filteredBooks, (JPanel) scrollPane.getViewport().getView());
        });

        // Panel d'en-tête
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(50, 50, 50)); // Fond sombre
        headerPanel.add(searchBar, BorderLayout.CENTER);

        // Vérifier le rôle de l'utilisateur
        if (user != null && "Bibliothécaire".equals(user.getRole())) {
            // Bouton d'ajout de livre
            addButton = new JButton("Ajouter Livre");
            addButton.setIcon(resizeIcon(loadIcon("/ressources/add-icon.png"), 20, 20));
            addButton.setFocusPainted(false);
            addButton.setForeground(Color.WHITE); // Texte en blanc
            addButton.addActionListener(e -> openAddBookDialog());

            // Panneau pour les icônes
            JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            iconPanel.add(addButton);
            headerPanel.add(iconPanel, BorderLayout.EAST);
        }

        // Panel pour afficher les livres
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new GridLayout(0, 3, 10, 10));

        scrollPane = new JScrollPane(booksPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ajouter le panneau d'en-tête et le panneau des livres
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        chargerLivres(livreController.lireLivres(), booksPanel);
    }

   
 void openAddBookDialog() {
    JDialog addDialog = new JDialog(parentWindow, "Ajouter un Livre", true);
    addDialog.setSize(400, 600);
    addDialog.setLocationRelativeTo(parentWindow); // Utilisez parentWindow pour centrer
    addDialog.setLayout(new GridBagLayout());
    addDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    addDialog.setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Champs du formulaire
        JLabel titleLabel = new JLabel("Titre:");
        JTextField titreField = new JTextField();

        JLabel auteurLabel = new JLabel("Auteur:");
        JTextField auteurField = new JTextField();

        JLabel genreLabel = new JLabel("Genre:");
        JComboBox<String> genreComboBox = new JComboBox<>(new String[]{"Fiction", "Non-Fiction", "Science Fiction", "Fantasy", "Biographie", "Histoire"});

        JLabel anneeLabel = new JLabel("Année:");
        JTextField anneeField = new JTextField();

        JLabel disponibleLabel = new JLabel("Disponible:");
        JCheckBox disponibleCheckBox = new JCheckBox();

        // Champ de sélection et aperçu de la couverture
        JLabel couvertureLabel = new JLabel("Couverture:");
        JButton couvertureButton = new JButton("Choisir une image");
        JLabel couverturePreview = new JLabel();
        couverturePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        couverturePreview.setPreferredSize(new Dimension(100, 150)); // Taille de prévisualisation de la couverture

        // Variable pour stocker le chemin de l'image
        String[] imagePath = new String[1]; // Utilisation d'un tableau pour que la variable soit modifiable dans le listener
        couvertureButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePath[0] = selectedFile.getAbsolutePath(); // Stockage du chemin de l'image
                try {
                    ImageIcon couvertureIcon = new ImageIcon(imagePath[0]);
                    if (couvertureIcon.getIconWidth() == -1) {
                        throw new Exception("Erreur lors du chargement de l'image : " + imagePath[0]);
                    }
                    couverturePreview.setIcon(resizeIcon(couvertureIcon, 100, 150)); // Redimensionner l'image pour l'aperçu
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors du chargement de l'image : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ajout des composants au dialogue
        gbc.gridx = 0; gbc.gridy = 0; addDialog.add(titleLabel, gbc);
        gbc.gridx = 1; addDialog.add(titreField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; addDialog.add(auteurLabel, gbc);
        gbc.gridx = 1; addDialog.add(auteurField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; addDialog.add(genreLabel, gbc);
        gbc.gridx = 1; addDialog.add(genreComboBox, gbc);
        gbc.gridx = 0; gbc.gridy = 3; addDialog.add(anneeLabel, gbc);
        gbc.gridx = 1; addDialog.add(anneeField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; addDialog.add(disponibleLabel, gbc);
        gbc.gridx = 1; addDialog.add(disponibleCheckBox, gbc);
        gbc.gridx = 0; gbc.gridy = 5; addDialog.add(couvertureLabel, gbc);
        gbc.gridx = 1; addDialog.add(couvertureButton, gbc);
        gbc.gridx = 1; gbc.gridy = 6; addDialog.add(couverturePreview, gbc);

        // Bouton pour ajouter le livre
        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(e -> {
            ajouterLivre(titreField.getText(), auteurField.getText(), (String) genreComboBox.getSelectedItem(), anneeField.getText(), disponibleCheckBox.isSelected(), imagePath[0]);
            addDialog.dispose();
        });
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; addDialog.add(addButton, gbc);

        addDialog.setVisible(true);
    }

    private void openEditBookDialog(Livre livre) {
    	JDialog editDialog = new JDialog(parentWindow, "Modifier un Livre", true); // Changer le titre
        editDialog.setSize(400, 600);
        editDialog.setLocationRelativeTo(parentWindow); // Centrer par rapport à parentWindow
        editDialog.setLayout(new GridBagLayout());
        editDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        editDialog.setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Champs du formulaire
        JLabel titleLabel = new JLabel("Titre:");
        JTextField titreField = new JTextField(livre.getTitre());

        JLabel auteurLabel = new JLabel("Auteur:");
        JTextField auteurField = new JTextField(livre.getAuteur());

        JLabel genreLabel = new JLabel("Genre:");
        JComboBox<String> genreComboBox = new JComboBox<>(new String[]{"Fiction", "Non-Fiction", "Science Fiction", "Fantasy", "Biographie", "Histoire"});
        genreComboBox.setSelectedItem(livre.getGenre());

        JLabel anneeLabel = new JLabel("Année:");
        JTextField anneeField = new JTextField(String.valueOf(livre.getAnneePublication()));

        JLabel disponibleLabel = new JLabel("Disponible:");
        JCheckBox disponibleCheckBox = new JCheckBox();
        disponibleCheckBox.setSelected(livre.isDisponible());

        // Champ de sélection et aperçu de la couverture
        JLabel couvertureLabel = new JLabel("Couverture:");
        JButton couvertureButton = new JButton("Choisir une image");
        JLabel couverturePreview = new JLabel();
        couverturePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        couverturePreview.setPreferredSize(new Dimension(100, 150));

        // Utilisation d'une variable locale pour stocker le chemin de l'image
        String[] imagePath = new String[1]; // Utilisation d'un tableau pour que la variable soit modifiable dans le listener
        imagePath[0] = livre.getImageUrl(); // Utiliser l'URL de l'image existante
        couverturePreview.setIcon(resizeIcon(loadCoverImage(imagePath[0]), 100, 150)); // Afficher l'image existante

        couvertureButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePath[0] = selectedFile.getPath(); // Mettre à jour le chemin de l'image
                ImageIcon couvertureIcon = new ImageIcon(imagePath[0]);
                couverturePreview.setIcon(resizeIcon(couvertureIcon, 100, 150)); // Redimensionner l'image pour l'aperçu
            }
        });

        // Bouton Enregistrer
        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(e -> {
            if (titreField.getText().trim().isEmpty() || auteurField.getText().trim().isEmpty() || anneeField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    int anneePublication = Integer .parseInt(anneeField.getText());
                    int currentYear = LocalDate.now().getYear();
                    if (anneePublication < 1600 || anneePublication > currentYear) {
                        JOptionPane.showMessageDialog(this, "L'année doit être entre 1600 et " + currentYear, "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Mettre à jour l'objet Livre
                    livre.setTitre(titreField.getText());
                    livre.setAuteur(auteurField.getText());
                    livre.setGenre((String) genreComboBox.getSelectedItem());
                    livre.setAnneePublication(anneePublication);
                    livre.setDisponible(disponibleCheckBox.isSelected());
                    livre.setImageUrl(imagePath[0]); // Utilisation du chemin de l'image mis à jour

                    livreController.modifierLivre(livre);
                    editDialog.dispose();
                    chargerLivres(livreController.lireLivres(), (JPanel) scrollPane.getViewport().getView()); // Utilisation de scrollPane ici
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Veuillez entrer une année valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Disposition des composants
        gbc.gridx = 0; gbc.gridy = 0; editDialog.add(titleLabel, gbc);
        gbc.gridx = 1; editDialog.add(titreField, gbc);
        gbc.gridx = 0; gbc.gridy++; editDialog.add(auteurLabel, gbc);
        gbc.gridx = 1; editDialog.add(auteurField, gbc);
        gbc.gridx = 0; gbc.gridy++; editDialog.add(genreLabel, gbc);
        gbc.gridx = 1; editDialog.add(genreComboBox, gbc);
        gbc.gridx = 0; gbc.gridy++; editDialog.add(anneeLabel, gbc);
        gbc.gridx = 1; editDialog.add(anneeField, gbc);
        gbc.gridx = 0; gbc.gridy++; editDialog.add(disponibleLabel, gbc);
        gbc.gridx = 1; editDialog.add(disponibleCheckBox, gbc);
        gbc.gridx = 0; gbc.gridy++; editDialog.add(couvertureLabel, gbc);
        gbc.gridx = 1; editDialog.add(couvertureButton, gbc);
        gbc.gridx = 1; gbc.gridy++; editDialog.add(couverturePreview, gbc);
        gbc.gridx = 1; gbc.gridy++; editDialog.add(saveButton, gbc);

        editDialog.setVisible(true);
    } 
    
    private void ajouterLivre(String titre, String auteur, String genre, String annee, boolean disponible, String imageUrl) {
        int anneePublication;
        try {
            anneePublication = Integer.parseInt(annee);
            int currentYear = LocalDate.now().getYear();
            if (anneePublication < 1900 || anneePublication > currentYear) {
                JOptionPane.showMessageDialog(this, "L'année doit être entre 1900 et " + currentYear, "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Année invalide. Veuillez entrer un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Livre livre = new Livre(UUID.randomUUID().toString(), titre, auteur, genre, anneePublication, disponible, imageUrl);
        livreController.ajouterLivre(livre);
        chargerLivres(livreController.lireLivres(), (JPanel) scrollPane.getViewport().getView());
    }


private ImageIcon resizeCoverImage(String path) {
    try {
        ImageIcon icon = new ImageIcon(path);
        // Redimensionner l'image pour la rendre plus grande (par exemple 250x375)
        Image img = icon.getImage().getScaledInstance(250, 375, Image.SCALE_SMOOTH); // Nouvelle taille
        return new ImageIcon(img);
    } catch (Exception e) {
        System.err.println("Error loading cover image from path: " + path);
        return new ImageIcon(); // Retourne une icône vide si l'image n'est pas trouvée
    }
}


private void chargerLivres(List<Livre> livres, JPanel booksPanel) {
    booksPanel.removeAll(); // Supprimez tous les composants existants
    System.out.println("Nombre de livres à charger: " + livres.size());

    for (Livre livre : livres) {
        JPanel livrePanel = new JPanel();
        livrePanel.setLayout(new BorderLayout());
        livrePanel.setOpaque(false); // Rendre le panneau transparent

        // Définir le fond en fonction du mode
        if (parentWindow.isDarkMode()) {
            livrePanel.setBackground(new Color(40, 42, 54, 200)); // Couleur sombre avec transparence
        } else {
            livrePanel.setBackground(new Color(255, 255, 255, 200)); // Fond clair avec transparence
        }

        livrePanel.setPreferredSize(new Dimension(200, 350));

        String imagePath = livre.getImageUrl() != null && !livre.getImageUrl().isEmpty()
            ? livre.getImageUrl()
            : "ressources/default-book.jpeg"; // Chemin par défaut

        System.out.println("Chemin de l'image: " + imagePath); // Débogage

        JLabel imageLabel = new JLabel(resizeCoverImage(imagePath));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        livrePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Vérifiez si l'utilisateur est un bibliothécaire
                if (user != null && "Bibliothécaire".equals(user.getRole())) {
                    if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
                        int response = JOptionPane.showOptionDialog(livrePanel, 
                            "Voulez-vous modifier ou supprimer ce livre ?", 
                            "Choix de l'action", 
                            JOptionPane.YES_NO_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, 
                            null, 
                            new Object[] {"Modifier", "Supprimer"}, 
                            null);
                        
                        if (response == JOptionPane.YES_OPTION) {
                            openEditBookDialog(livre);
                        } else if (response == JOptionPane.NO_OPTION) {
                            deleteBook(livre);
                        }
                    }
                } else {
                    // Si l'utilisateur n'est pas un bibliothécaire, afficher un message ou effectuer une autre action
                    JOptionPane.showMessageDialog(livrePanel, "Vous n'avez pas les droits nécessaires pour modifier ou supprimer ce livre.", "Accès Refusé", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        livrePanel.add(imageLabel, BorderLayout.CENTER);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false); // Rendre le panneau de texte transparent

        JLabel titleLabel = new JLabel(livre.getTitre(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(parentWindow.isDarkMode() ? Color.WHITE : Color.BLACK); // Texte en fonction du mode

        JLabel authorLabel = new JLabel(livre.getAuteur(), JLabel.CENTER);
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        authorLabel.setForeground(parentWindow.isDarkMode() ? Color.LIGHT_GRAY : Color.DARK_GRAY); // Texte en fonction du mode

        textPanel.add(titleLabel);
        textPanel.add(authorLabel);

        livrePanel.add(textPanel, BorderLayout.SOUTH);

        booksPanel.add(livrePanel);
    }

    booksPanel.revalidate(); // Revalider le panneau
    booksPanel.repaint(); // Redessiner le panneau
}


private void deleteBook(Livre livre) {
    int confirmed = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce livre ?", "Confirmation", JOptionPane.YES_NO_OPTION);
    if (confirmed == JOptionPane.YES_OPTION) {
        // Utilisez l'identifiant du livre passé en paramètre
        livreController.supprimerLivre(livre.getId());
        JOptionPane.showMessageDialog(this, "Le livre a été supprimé avec succès.");
        
        // Recharger la liste des livres
        chargerLivres(livreController.lireLivres(), (JPanel) scrollPane.getViewport().getView());
    }
}
    
 
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        try {
            // Appliquer le thème global par défaut
            UIManager.setLookAndFeel(new FlatDraculaIJTheme());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        User currentUser = null;
		StylishWindow view = new StylishWindow(currentUser);
        view.setVisible(true);
    });
}
 
JLabel createImageLabel(String resourcePath, int width, int height) {
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

    // Utilisation dans le constructeur
    JLabel logo = createImageLabel("/ressources/logo.png", 50, 50); // Réduire la taille du logo

    
    private ImageIcon loadIcon(String path) {
        try {
            URL iconUrl = LivreView.class.getResource(path);
            if (iconUrl != null) {
                return new ImageIcon(iconUrl);
            } else {
                System.err.println("Icône non trouvée à: " + path);
                return new ImageIcon(LivreView.class.getResource("/ressources/default-icon.png")); // Icône par défaut
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'icône: " + e.getMessage());
            return new ImageIcon(); // Retourne une icône vide en cas d'erreur
        }
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        if (icon == null || icon.getImage() == null) {
            System.err.println("Icône non valide, utilisation de l'icône par défaut.");
            icon = new ImageIcon(LivreView.class.getResource("/ressources/default-icon.png")); // Icône par défaut
        }
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private ImageIcon loadCoverImage(String path) {
        File coverFile = new File(path);
        if (coverFile.exists()) {
            return new ImageIcon(path);
        } else {
            System.err.println("Image de couverture non trouvée à : " + path);
            return new ImageIcon("ressources/default-book.jpeg"); // Retourne une image par défaut
        }
    }
}