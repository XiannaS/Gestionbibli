package vue;

import controllers.LivreController;
import model.Livre;
import style.ModernNavBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LivreView extends JFrame {
    private static final long serialVersionUID = 1L;
    private LivreController livreController;
    private JButton addButton;
    private JTextField searchField;
    private JCheckBox availableCheckBox;
    private JCheckBox borrowedCheckBox;
    private ModernNavBar navBar; // Ajout de la barre de navigation
    private JScrollPane scrollPane; // Référence au JScrollPane
    private JComboBox<String> genreComboBox;

    public LivreView() {
        livreController = new LivreController();
        setTitle("Gestion de Bibliothèque");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245)); // Fond doux et taupe

        // Initialisation de la barre de navigation
        navBar = new ModernNavBar();
        add(navBar, BorderLayout.WEST); // Ajout de la barre de navigation à gauche

        // Bouton d'ajout de livre
        addButton = new JButton("Ajouter Livre");
        addButton.setIcon(resizeIcon(loadIcon("/ressources/add-icon.png"), 20, 20)); // Charger et redimensionner l'icône
        addButton.setFocusPainted(false);
        addButton.setBackground(Color.decode("#004754"));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> openAddBookDialog());

        // Panneau de recherche
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(245, 245, 245));
        searchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Alignement à droite

        searchField = new JTextField(20);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        searchField.setPreferredSize(new Dimension(250, 30)); // Taille adaptée
        
        // JComboBox pour le genre
        genreComboBox = new JComboBox<>(new String[]{"Tous", "Fiction", "Non-Fiction", "Science Fiction", "Fantasy", "Biographie", "Histoire"});
        genreComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        genreComboBox.setPreferredSize(new Dimension(150, 30));

        availableCheckBox = new JCheckBox("Disponibles");
        availableCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        availableCheckBox.setBackground(new Color(245, 245, 245));

        borrowedCheckBox = new JCheckBox("Empruntés");
        borrowedCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        borrowedCheckBox.setBackground(new Color(245, 245, 245));

        // JComboBox pour le genre
        genreComboBox = new JComboBox<>(new String[]{"Tous", "Fiction", "Non-Fiction", "Science Fiction", "Fantasy", "Biographie", "Histoire"});
        // Icône de recherche
        ImageIcon searchIcon = new ImageIcon(getClass().getResource("/ressources/search-icon.png"));
        Image scaledImage = searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton searchButton = new JButton(new ImageIcon(scaledImage));
        searchButton.setBackground(new Color(255, 182, 193));
        searchButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> filterBooks());

        searchPanel.add(searchField);
        searchPanel.add(genreComboBox);
        searchPanel.add(availableCheckBox);
        searchPanel.add(borrowedCheckBox);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH); // Ajoutez le panneau de recherche en haut

        add(searchPanel, BorderLayout.NORTH); // Ajoutez le panneau de recherche en haut

        // Panel pour afficher les livres
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new GridLayout(0, 3, 10, 10));
        booksPanel.setBackground(new Color(245, 245, 245)); // Fond doux et taupe

        scrollPane = new JScrollPane(booksPanel); // Stocker la référence ici
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Panel en bas pour le bouton
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(245, 245, 245));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(addButton);
        bottomPanel.add(buttonPanel, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH); // Ajoutez le panneau inférieur

        chargerLivres(livreController.lireLivres(), booksPanel);
    }

    private void openAddBookDialog() {
        JDialog addDialog = new JDialog(this, "Ajouter un Livre", true);
        addDialog.setSize(400, 600);
        addDialog.setLocationRelativeTo(this);
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
        JDialog editDialog = new JDialog(this, "Modifier un Livre", true);
        editDialog.setSize(400, 600);
        editDialog.setLocationRelativeTo(this);
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
            Image img = icon.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH); // Taille plus grande
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Error loading cover image from path: " + path);
            return new ImageIcon(); // Retourne une icône vide si l'image n'est pas trouvée
        }
    }

private void chargerLivres(List<Livre> livres, JPanel booksPanel) {
    booksPanel.removeAll();

    for (Livre livre : livres) {
        JPanel livrePanel = new JPanel();
        livrePanel.setLayout(new BorderLayout());
        livrePanel.setBackground(new Color(245, 245, 245)); // Fond doux et taupe
        livrePanel.setPreferredSize(new Dimension(200, 350)); // Ajuster la hauteur

        String imagePath = livre.getImageUrl() != null && !livre.getImageUrl().isEmpty()
            ? livre.getImageUrl()
            : "ressources/default-book.jpeg"; // Chemin par défaut

        JLabel imageLabel = new JLabel(resizeCoverImage(imagePath));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        livrePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Vérifiez si le clic est sur le livrePanel lui-même, pas sur un composant enfant
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
                    // Demander à l'utilisateur s'il veut modifier ou supprimer le livre
                    int response = JOptionPane.showOptionDialog(livrePanel, 
                        "Voulez-vous modifier ou supprimer ce livre ?", 
                        "Choix de l'action", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE, 
                        null, 
                        new Object[] {"Modifier", "Supprimer"}, 
                        null);
                    
                    if (response == JOptionPane.YES_OPTION) {
                        // Ouvrir le dialogue d'édition
                        openEditBookDialog(livre);
                    } else if (response == JOptionPane.NO_OPTION) {
                        // Appeler la méthode deleteBook pour gérer la confirmation
                        deleteBook(livre);
                    }
                }
            }
        });
        livrePanel.add(imageLabel, BorderLayout.CENTER);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(new Color(245, 245, 245));
        JLabel titleLabel = new JLabel(livre.getTitre(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Ajuster la taille de la police
        titleLabel.setForeground(new Color(50, 50, 50)); // Texte sombre pour un contraste doux
        JLabel authorLabel = new JLabel(livre.getAuteur(), JLabel.CENTER);
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Ajuster la taille de la police
        authorLabel.setForeground(new Color(50, 50, 50)); // Texte sombre pour un contraste doux
        textPanel.add(titleLabel);
        textPanel.add(authorLabel);

        livrePanel.add(textPanel, BorderLayout.SOUTH);

        booksPanel.add(livrePanel);
    }

    booksPanel.revalidate();
    booksPanel.repaint();
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
    
    
private void filterBooks() {
    String searchQuery = searchField.getText().trim().toLowerCase();
    boolean onlyAvailable = availableCheckBox.isSelected();
    boolean onlyBorrowed = borrowedCheckBox.isSelected();
    String selectedGenre = (String) genreComboBox.getSelectedItem();

    // Filtrer les livres en fonction des critères
    List<Livre> filteredBooks = livreController.lireLivres().stream()
        .filter(livre -> {
            // Vérifier si le livre correspond à la recherche par titre ou auteur
            boolean matchesSearch = livre.getTitre().toLowerCase().contains(searchQuery) || livre.getAuteur().toLowerCase().contains(searchQuery);

            // Vérifier la disponibilité ou l'emprunt
            boolean matchesAvailability = true; // Par défaut, on considère que ça correspond
            if (onlyAvailable && !onlyBorrowed) {
                matchesAvailability = livre.isDisponible(); // Seulement les livres disponibles
            } else if (!onlyAvailable && onlyBorrowed) {
                matchesAvailability = !livre.isDisponible(); // Seulement les livres empruntés
            }

            // Vérifier le genre
            boolean matchesGenre = selectedGenre.equals("Tous") || livre.getGenre().equals(selectedGenre);

            return matchesSearch && matchesAvailability && matchesGenre; // Retourner true si toutes les conditions sont remplies
        })
        .collect(Collectors.toList());

    // Recharger l'affichage avec les livres filtrés
    chargerLivres(filteredBooks, (JPanel) scrollPane.getViewport().getView());
}
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LivreView app = new LivreView();
            app.setVisible(true);
        });
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