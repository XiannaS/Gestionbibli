package style;

import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchBar extends JPanel {

    private static final long serialVersionUID = 1L;
    private JComboBox<String> categoryComboBox;
    private JPanel filterPanel;
    private JTextField searchField;
    private SearchListener searchListener; // Champ pour l'écouteur

    public SearchBar() {
        // Configuration de la barre de recherche
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(400, 100)); // Ajustez selon vos besoins

        // Barre de recherche
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        add(searchField);
        // ComboBox pour sélectionner la catégorie
        String[] categories = {"Livres", "Utilisateurs", "Emprunts"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFilterPanel();
            }
        });
        add(categoryComboBox);

        // Panneau pour les filtres
        filterPanel = new JPanel();
        updateFilterPanel(); // Initialiser les filtres en fonction de la catégorie sélectionnée
        add(filterPanel);

        // Charger l'icône de recherche
        ImageIcon icon = new ImageIcon(getClass().getResource("/ressources/search.png"));

        // Vérifier si l'icône est bien chargée
        if (icon.getIconWidth() == -1) {
            System.out.println("L'icône n'a pas pu être chargée !");
            // Remplacer l'icône par une icône système pour tester
            Icon defaultIcon = UIManager.getIcon("FileView.fileIcon");
            // Convertir le Icon en ImageIcon
            icon = new ImageIcon(defaultIcon.toString());
        } else {
            // Redimensionner l'icône si nécessaire
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        }

        // Ajouter le bouton de recherche avec l'icône de loupe
        JButton searchButton = new JButton(icon);
        searchButton.setPreferredSize(new Dimension(40, 40)); // Taille du bouton ajustée
        searchButton.setContentAreaFilled(false);
        searchButton.setFocusPainted(false);

     // Action pour le bouton de recherche
        searchButton.addActionListener(e -> {
            if (searchListener != null) {
                // Récupérer les valeurs des filtres
                String genre = ""; // Récupérer le genre choisi
                String year = ""; // Récupérer l'année
                boolean isAvailable = false; // Récupérer l'état de disponibilité
                boolean isUnavailable = false; // Récupérer l'état de non-disponibilité

                // Exemple de récupération de valeurs en fonction de la catégorie
                if ("Livres".equals(categoryComboBox.getSelectedItem())) {
                    genre = ((JComboBox<String>) filterPanel.getComponent(1)).getSelectedItem().toString();
                    year = ((JTextField) filterPanel.getComponent(3)).getText();
                    isAvailable = ((JRadioButton) filterPanel.getComponent(5)).isSelected();
                    isUnavailable = ((JRadioButton) filterPanel.getComponent(6)).isSelected();
                }
                // Appeler l'écouteur de recherche
                searchListener.onSearch(searchField.getText(), genre, year, isAvailable, isUnavailable);
            }
        });

        // Ajouter un MouseListener pour l'effet de zoom
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Agrandir le bouton
                searchButton.setPreferredSize(new Dimension(50, 50));
                searchButton.revalidate(); // Revalider pour appliquer la nouvelle taille
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Rétablir la taille d'origine
                searchButton.setPreferredSize(new Dimension(40, 40));
                searchButton.revalidate(); // Revalider pour appliquer la nouvelle taille
            }
        });

        add(searchButton); // Ajouter le bouton de recherche à la barre de recherche

        // Appliquer le thème Dracula
        applyDraculaTheme();
    }

    // Méthode pour ajouter un écouteur de recherche
    public void addSearchListener(SearchListener listener) {
        this.searchListener = listener;
    }

    private void updateFilterPanel() {
        filterPanel.removeAll(); // Vider le panneau des filtres

        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        if ("Livres".equals(selectedCategory)) {
            // Genre: JComboBox avec plusieurs options
            filterPanel.add(new JLabel("Genre:"));
            JComboBox<String> genreComboBox = new JComboBox<>(new String[]{
                    "Tous", "Fiction", "Non-Fiction", "Science Fiction", "Fantasy", "Biographie", "Histoire"});
            filterPanel.add(genreComboBox);

            // Année : Vérification de l'année
            JTextField yearField = new JTextField(10);
            filterPanel.add(new JLabel(" Année:"));
            filterPanel.add(yearField);

            // Disponibilité: JRadioButton pour disponible / non disponible
            filterPanel.add(new JLabel("Disponibilité:"));
            JRadioButton availableRadioButton = new JRadioButton("Disponible");
            JRadioButton unavailableRadioButton = new JRadioButton("Non disponible");
            ButtonGroup availabilityGroup = new ButtonGroup();
            availabilityGroup.add(availableRadioButton);
            availabilityGroup.add(unavailableRadioButton);
            filterPanel.add(availableRadioButton);
            filterPanel.add(unavailableRadioButton);

            // Auteur: JTextField
            filterPanel.add(new JLabel("Auteur:"));
            filterPanel.add(new JTextField(10));

        } else if ("Utilisateurs".equals(selectedCategory)) {
            // Nom: JTextField
            filterPanel.add(new JLabel("Nom:"));
            filterPanel.add(new JTextField(10));

            // Statut: JComboBox pour sélectionner le statut
            filterPanel.add(new JLabel("Statut:"));
            JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Tous", "Actif", "Inactif"});
            filterPanel.add(statusComboBox);

        } else if ("Emprunts".equals(selectedCategory)) {
            // Nom de l'utilisateur: JTextField
            filterPanel.add(new JLabel("Nom de l'utilisateur:"));
            filterPanel.add(new JTextField(10));

            // Titre du livre: JTextField
            filterPanel.add(new JLabel("Titre du livre:"));
            filterPanel.add(new JTextField(10));

            // Date d'emprunt: JDateChooser (sélecteur de date)
            filterPanel.add(new JLabel("Date d'emprunt:"));
            JDateChooser borrowDateChooser = new JDateChooser();
            borrowDateChooser.setDateFormatString("dd/MM/yyyy"); // Format de date
            filterPanel.add(borrowDateChooser);

            // Date de retour: JDateChooser (sélecteur de date)
            filterPanel.add(new JLabel("Date de retour:"));
            JDateChooser returnDateChooser = new JDateChooser();
            returnDateChooser.setDateFormatString("dd/MM/yyyy"); // Format de date
            filterPanel.add(returnDateChooser);
        }

        filterPanel.revalidate(); // Revalider le panneau pour afficher les nouveaux composants
        filterPanel.repaint(); // Repeindre le panneau
    }
 
    public String getSearchText() {
        return searchField.getText();
    }

    public String getSelectedGenre() {
        // Supposons que la JComboBox pour les genres est le deuxième composant du filterPanel
        return ((JComboBox<String>) filterPanel.getComponent(1)).getSelectedItem().toString();
    }

    public boolean isAvailableSelected() {
        // Supposons que le bouton radio "Disponible" est le cinquième composant du filterPanel
        return ((JRadioButton) filterPanel.getComponent(5)).isSelected();
    }

    public boolean isBorrowedSelected() {
        // Supposons que le bouton radio "Non disponible" est le sixième composant du filterPanel
        return ((JRadioButton) filterPanel.getComponent(6)).isSelected();
    }
    private void applyDraculaTheme() {
        try {
            UIManager.setLookAndFeel(new FlatDraculaIJTheme());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);
    }
}