package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmpruntView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField tfIdEmprunt, tfIdUtilisateur, tfIdLivre, tfDateEmprunt, tfDateRetour;
    private JTable tableEmprunts;
    private DefaultTableModel tableModel;
    private JLabel backgroundLabel;

    public EmpruntView() {
        // Configuration du panel
        setLayout(new BorderLayout());

        // Charger l'image de fond en utilisant le ClassLoader et vérifier si elle est chargée
        ImageIcon backgroundImage;
        try {
            backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("ressources/biblio.png"));
            // Vérifiez si l'image est bien chargée
            if (backgroundImage.getIconWidth() == -1) {
                System.out.println("Image non trouvée : vérifiez le chemin d'accès.");
            } else {
                System.out.println("Image chargée avec succès.");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
            e.printStackTrace();
            backgroundImage = null; // En cas d'erreur, définir l'image comme nulle
        }

        // Assurez-vous que l'image a été chargée avant de l'utiliser
        if (backgroundImage != null) {
            backgroundLabel = new JLabel(backgroundImage);
            backgroundLabel.setLayout(new BorderLayout()); // Permet l'ajout de composants par-dessus l'image
            add(backgroundLabel); // Ajouter le JLabel avec l'image de fond au JPanel
        } else {
            System.out.println("Aucune image à afficher en fond.");
        }

        // Panel principal (superposition des autres composants par-dessus l'image)
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setOpaque(false); // Rendre ce panel transparent pour que l'image de fond soit visible

        // Panel pour l'ajout d'un emprunt
        JPanel panelAjout = new JPanel();
        panelAjout.setLayout(new GridLayout(6, 2, 10, 10));
        panelAjout.setBorder(BorderFactory.createTitledBorder("Ajouter un emprunt"));
        panelAjout.setOpaque(false); // Rendre ce panel transparent

        // Création des champs de saisie avec fond transparent léger
        tfIdEmprunt = new JTextField();
        tfIdUtilisateur = new JTextField();
        tfIdLivre = new JTextField();
        tfDateEmprunt = new JTextField();
        tfDateRetour = new JTextField();

        // Définir une couleur marron pour les labels
        Color marron = new Color(139, 69, 19); // Marron foncé

        // Ajouter les champs avec des labels marron
        panelAjout.add(new JLabel("ID Emprunt:"));
        panelAjout.add(tfIdEmprunt);
        panelAjout.add(new JLabel("ID Utilisateur:"));
        panelAjout.add(tfIdUtilisateur);
        panelAjout.add(new JLabel("ID Livre:"));
        panelAjout.add(tfIdLivre);
        panelAjout.add(new JLabel("Date Emprunt (yyyy-MM-dd):"));
        panelAjout.add(tfDateEmprunt);
        panelAjout.add(new JLabel("Date Retour (yyyy-MM-dd):"));
        panelAjout.add(tfDateRetour);

        // Appliquer la couleur marron aux labels
        for (Component c : panelAjout.getComponents()) {
            if (c instanceof JLabel) {
                ((JLabel) c).setForeground(marron);
                ((JLabel) c).setFont(new Font("Segoe Script", Font.PLAIN, 16)); // Police fantaisie pour les labels
            }
        }

        // Définir la transparence légère pour les champs de sais ie
        setTransparentTextField(tfIdEmprunt);
        setTransparentTextField(tfIdUtilisateur);
        setTransparentTextField(tfIdLivre);
        setTransparentTextField(tfDateEmprunt);
        setTransparentTextField(tfDateRetour);

        // Bouton pour ajouter un emprunt
        JButton btnAjouter = new JButton("Ajouter Emprunt");
        btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterEmprunt();
            }

            private void ajouterEmprunt() {
                // Logique pour ajouter un emprunt (ici on affiche juste les valeurs dans la console)
                String idEmprunt = tfIdEmprunt.getText();
                String idUtilisateur = tfIdUtilisateur.getText();
                String idLivre = tfIdLivre.getText();
                String dateEmprunt = tfDateEmprunt.getText();
                String dateRetour = tfDateRetour.getText();

                // Vous pouvez ajouter ici la logique pour ajouter un emprunt dans le tableau ou dans une base de données.
                System.out.println("Ajout d'un emprunt : " + idEmprunt + ", " + idUtilisateur + ", " + idLivre + ", " + dateEmprunt + ", " + dateRetour);
            }
        });
        btnAjouter.setFont(new Font("Segoe Script", Font.PLAIN, 16)); // Police fantaisie pour le bouton
        panelAjout.add(btnAjouter);

        // Ajouter le panelAjout au panelPrincipal
        panelPrincipal.add(panelAjout);

        // Panel pour afficher les emprunts dans un tableau
        String[] columnNames = {"ID Emprunt", "ID Utilisateur", "ID Livre", "Date Emprunt", "Date Retour", "Est Rendu"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableEmprunts = new JTable(tableModel);
        JScrollPane scrollTable = new JScrollPane(tableEmprunts);

        // Rendre le tableau et son fond transparent
        tableEmprunts.setOpaque(false);
        tableEmprunts.setBackground(new Color(0, 0, 0, 0)); // Fond transparent
        JTableHeader header = tableEmprunts.getTableHeader();
        header.setOpaque(false); // Titre de la table transparent
        header.setBackground(new Color(0, 0, 0, 0)); // Titre transparent
        
        // Modifier les couleurs des cellules
        tableEmprunts.setFont(new Font("Segoe Script", Font.PLAIN, 16)); // Police fantaisie
        tableEmprunts.setForeground(Color.BLACK); // Couleur du texte en noir

        // Appliquer une transparence au JScrollPane pour que la zone de la table soit également transparente
        scrollTable.setOpaque(false);
        scrollTable.getViewport().setOpaque(false);

        // Ajouter le tableau au panel
        panelPrincipal.add(scrollTable);

        // Ajouter le panel principal (contenant tous les sous-panels) au JLabel avec l'image en fond
        if (backgroundLabel != null) {
            backgroundLabel.add(panelPrincipal, BorderLayout.CENTER);
        }
    }

    private void setTransparentTextField(JTextField textField) {
        textField.setOpaque(false);
        textField.setForeground(Color.BLACK);  // Pour que le texte soit noir
        textField.setBackground(new Color(255, 255, 255, 150)); // Fond blanc semi-transparent
        textField.setFont(new Font("Segoe Script", Font.PLAIN, 16)); // Police fantaisie
    }

    public static void main(String[] args) {
        // S'assurer que l'application utilise le Look and Feel de la plateforme
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();  // En cas d'erreur, utiliser l'apparence par défaut
        }

        // Créer et afficher l'interface utilisateur
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestion des Emprunts");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            EmpruntView empruntView = new EmpruntView();
            frame.add(empruntView);
            frame.setVisible(true); // Afficher la fenêtre des emprunts
        });
    }
}