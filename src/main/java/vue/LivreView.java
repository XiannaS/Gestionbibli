package vue;

import controllers.LivreController;
import model.Livre;
import style.ModernNavBar;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.io.File;
import java.net.URL;

public class LivreView extends JFrame {
    private static final long serialVersionUID = 1L;
    private LivreController livreController;
    private JButton addButton;
    private JTextField searchField;
    private JCheckBox availableCheckBox;
    private JCheckBox borrowedCheckBox;
    private ModernNavBar navBar; // Ajout de la barre de navigation

    public LivreView() {
        livreController = new LivreController();
        setTitle("Gestion de Bibliothèque");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

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

        // Panel pour afficher les livres
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new GridLayout(0, 3, 10, 10));
        booksPanel.setBackground(new Color(245, 245, 245));
        
        JScrollPane scrollPane = new JScrollPane(booksPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Panel en bas pour le bouton
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(245, 245, 245));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(addButton);
        bottomPanel.add(buttonPanel, BorderLayout.WEST);

        // Panel pour la recherche et le filtrage
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(245, 245, 245));

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Rechercher");
        searchButton.setIcon(resizeIcon(loadIcon("/ressources/search-icon.png"), 20, 20)); // Charger et redimensionner l'icône
        searchButton.addActionListener(e -> filterBooks());
        availableCheckBox = new JCheckBox("Disponibles");
        borrowedCheckBox = new JCheckBox("Empruntés");

        searchPanel.add(new JLabel("Recherche:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(availableCheckBox);
        searchPanel.add(borrowedCheckBox);

        add(searchPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        chargerLivres(livreController.lireLivres(), booksPanel);
    }

    private void openAddBookDialog() {
        // Code pour ouvrir le dialogue d 'ajout de livre
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
                imagePath[0] = selectedFile.getPath(); // Stockage du chemin de l'image
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
                // Créer un objet Livre
                String titre = titreField.getText();
                String auteur = auteurField.getText();
                String genre = (String) genreComboBox.getSelectedItem();
                String annee = anneeField.getText();
                boolean disponible = disponibleCheckBox.isSelected();
                String imageUrl = imagePath[0]; // Utilisation du chemin de l'image

                // Appeler la méthode ajouterLivre avec tous les paramètres requis
                ajouterLivre(titre, auteur, genre, annee, disponible, imageUrl);
                chargerLivres(livreController.lireLivres(), (JPanel) ((JScrollPane) getContentPane().getComponent(0)).getViewport().getView());
                addDialog.dispose();
            }
        });

        // Disposition des composants
        gbc.gridx = 0;
        gbc.gridy = 0;
        addDialog.add(titleLabel, gbc);
        gbc.gridx = 1;
        addDialog.add(titreField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        addDialog.add(auteurLabel, gbc);
        gbc.gridx = 1;
        addDialog.add(auteurField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        addDialog.add(genreLabel, gbc);
        gbc.gridx = 1;
        addDialog.add(genreComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        addDialog.add(anneeLabel, gbc);
        gbc.gridx = 1;
        addDialog.add(anneeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        addDialog.add(disponibleLabel, gbc);
        gbc.gridx = 1;
        addDialog.add(disponibleCheckBox, gbc);

        // Disposition des composants pour la couverture
        gbc.gridx = 0;
        gbc.gridy++;
        addDialog.add(couvertureLabel, gbc);
        gbc.gridx = 1;
        addDialog.add(couvertureButton, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        addDialog.add(couverturePreview, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        addDialog.add(saveButton, gbc);

        addDialog.setVisible(true);
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
    }

    private void chargerLivres(List<Livre> livres, JPanel booksPanel) {
        booksPanel.removeAll();

        livres.stream().forEach(livre -> {
            JPanel livrePanel = new JPanel();
            livrePanel.setLayout(new BorderLayout());
            livrePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            livrePanel.setBackground(new Color(255, 255, 255));
            livrePanel.setPreferredSize(new Dimension(200, 300));

            String imagePath = livre.getImageUrl() != null && !livre.getImageUrl().isEmpty()
                ? livre.getImageUrl()
                : "ressources/default-book.jpeg"; // Chemin par défaut

            // Utilisez loadCoverImage pour charger l'image de couverture
            JLabel imageLabel = new JLabel(resizeCoverImage(imagePath, 120, 180));
            livrePanel.add(imageLabel, BorderLayout.CENTER);

            JPanel textPanel = new JPanel(new GridLayout(2, 1));
            textPanel.setBackground(new Color(255, 255, 255));
            JLabel titleLabel = new JLabel(livre.getTitre(), JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
            JLabel authorLabel = new JLabel(livre.getAuteur(), JLabel.CENTER);
            textPanel.add(titleLabel);
            textPanel.add(authorLabel);

            livrePanel.add(textPanel, BorderLayout.SOUTH);

            // Ajout des boutons Modifier et Supprimer
            JPanel buttonPanel = new JPanel();

            JButton editButton = new JButton("Modifier");
            editButton.setIcon(resizeIcon(loadIcon("/ressources/edit-icon.png"), 20, 20)); // Icône pour modifier
            editButton.addActionListener(e -> openEditBookDialog(livre));

            JButton deleteButton = new JButton("Supprimer");
            deleteButton.setIcon(resizeIcon(loadIcon("/ressources/delete-icon.png"), 20, 20)); // Icône pour supprimer
            deleteButton.addActionListener(e -> deleteBook(livre));

            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);
            livrePanel.add(buttonPanel, BorderLayout.NORTH);

            booksPanel.add(livrePanel);
        });

        booksPanel.revalidate();
        booksPanel.repaint();
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
                    int anneePublication = Integer.parseInt(anneeField.getText());
                    int currentYear = LocalDate.now().getYear();
                    if (anneePublication < 1600 || anneePublication > currentYear) {
                        JOptionPane.showMessageDialog(this, "L'année doit être entre 1600 et " + currentYear, "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Mettre à jour l'objet Livre
                    livre.setTitre(titreField.getText());
                    livre.setAuteur(auteurField.getText());
                    livre.setGenre((String) genreComboBox.getSelectedItem());
                    livre.setAnneePublication(Integer.parseInt(anneeField.getText()));
                    livre.setDisponible(disponibleCheckBox.isSelected());
                    livre.setImageUrl(imagePath[0]); // Utilisation du chemin de l'image mis à jour

                    livreController.modifierLivre(livre);
                    editDialog.dispose();
     chargerLivres(livreController.lireLivres(), (JPanel) ((JScrollPane) getContentPane().getComponent(0)).getViewport().getView());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Veuillez entrer une année valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Disposition des composants
        gbc.gridx = 0;
        gbc.gridy = 0;
        editDialog.add(titleLabel, gbc);
        gbc.gridx = 1;
        editDialog.add(titreField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        editDialog.add(auteurLabel, gbc);
        gbc.gridx = 1;
        editDialog.add(auteurField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        editDialog.add(genreLabel, gbc);
        gbc.gridx = 1;
        editDialog.add(genreComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        editDialog.add(anneeLabel, gbc);
        gbc.gridx = 1;
        editDialog.add(anneeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        editDialog.add(disponibleLabel, gbc);
        gbc.gridx = 1;
        editDialog.add(disponibleCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        editDialog.add(couvertureLabel, gbc);
        gbc.gridx = 1;
        editDialog.add(couvertureButton, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        editDialog.add(couverturePreview, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        editDialog.add(saveButton, gbc);

        editDialog.setVisible(true);
    }
    private void deleteBook(Livre livre) {
        int confirmation = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer ce livre ?", "Confirmation de Suppression", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            livreController.supprimerLivre(livre.getId());
            chargerLivres(livreController.lireLivres(), (JPanel) ((JScrollPane) getContentPane().getComponent(0)).getViewport().getView());
        }
    }

    private void filterBooks() {
        String searchQuery = searchField.getText().trim().toLowerCase();
        boolean onlyAvailable = availableCheckBox.isSelected();
        boolean onlyBorrowed = borrowedCheckBox.isSelected();

        // Filtrer les livres en fonction des critères
        List<Livre> filteredBooks = livreController.lireLivres().stream()
            .filter(livre -> {
                // Vérifier si le livre correspond à la recherche par titre ou auteur
                boolean matchesSearch = livre.getTitre().toLowerCase().contains(searchQuery) || livre.getAuteur().toLowerCase().contains(searchQuery);
                
                // Vérifier la disponibilité ou l'emprunt
                boolean matchesAvailability = false;
                if (onlyAvailable && !onlyBorrowed) {
                    matchesAvailability = livre.isDisponible(); // Seulement les livres disponibles
                } else if (!onlyAvailable && onlyBorrowed) {
                    matchesAvailability = !livre.isDisponible(); // Seulement les livres empruntés
                } else {
                    // Si ni "disponible" ni "emprunté" n'est sélectionné, on ne filtre pas la disponibilité
                    matchesAvailability = true;
                }

                return matchesSearch && matchesAvailability; // Retourner true si les deux conditions sont remplies
            })
            .collect(Collectors.toList());

        // Recharger l'affichage avec les livres filtrés
        chargerLivres(filteredBooks, (JPanel) ((JScrollPane) getContentPane().getComponent(0)).getViewport().getView());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LivreView app = new LivreView();
            app.setVisible(true);
        });
    }

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

    private ImageIcon resizeCoverImage(String path, int width, int height) {
        ImageIcon coverImage = loadCoverImage(path);
        if (coverImage != null) {
            Image img = coverImage.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } else {
            return new ImageIcon(); // Retourne une icône vide si l'image n'a pas pu être chargée
        }
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