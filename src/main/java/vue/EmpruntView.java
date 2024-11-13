package vue;

import controllers.EmpruntController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmpruntView extends JFrame {

    private static final long serialVersionUID = 1L;
    private EmpruntController empruntController = new EmpruntController();
    private JTextField tfIdEmprunt, tfIdUtilisateur, tfIdLivre, tfDateEmprunt, tfDateRetour;
    private JTable tableEmprunts;
    private DefaultTableModel tableModel;
    private JTextArea taHistorique;
    private JLabel backgroundLabel;

    public EmpruntView() {
        // Configuration de la fenêtre
        setTitle("Gestion des Emprunts");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centrer la fenêtre sur l'écran

        // Utilisation du BorderLayout pour la fenêtre principale
        setLayout(new BorderLayout());

        // Charger l'image de fond
        ImageIcon backgroundImage = new ImageIcon("/Users/mac/Desktop/your_image.jpg");
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout()); // Permet l'ajout de panels par-dessus l'image

        // Ajouter le JLabel avec l'image de fond à la fenêtre
        getContentPane().add(backgroundLabel, BorderLayout.CENTER);

        // Panel principal (superposition des autres composants par-dessus l'image)
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setOpaque(false); // Rendre ce panel transparent pour que l'image de fond soit visible

        // Panel pour l'ajout d'un emprunt
        JPanel panelAjout = new JPanel();
        panelAjout.setLayout(new GridLayout(6, 2, 10, 10));
        panelAjout.setBorder(BorderFactory.createTitledBorder("Ajouter un emprunt"));

        tfIdEmprunt = new JTextField();
        tfIdUtilisateur = new JTextField();
        tfIdLivre = new JTextField();
        tfDateEmprunt = new JTextField();
        tfDateRetour = new JTextField();

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

        JButton btnAjouter = new JButton("Ajouter Emprunt");
        btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterEmprunt();
            }

            private void ajouterEmprunt() {
                // TODO: ajouter la logique pour ajouter un emprunt
            }
        });
        panelAjout.add(btnAjouter);

        // Ajouter le panelAjout au panelPrincipal
        panelPrincipal.add(panelAjout);

        // Panel pour afficher les emprunts dans un tableau
        String[] columnNames = {"ID Emprunt", "ID Utilisateur", "ID Livre", "Date Emprunt", "Date Retour", "Est Rendu"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableEmprunts = new JTable(tableModel);
        JScrollPane scrollTable = new JScrollPane(tableEmprunts);
        panelPrincipal.add(scrollTable);

        // Ajouter le panel principal (contenant tous les sous-panels) au centre de la fenêtre
        backgroundLabel.add(panelPrincipal, BorderLayout.CENTER);

        // Table pour afficher les emprunts
        JScrollPane scrollHistoriquePanel = new JScrollPane(panelPrincipal);
        backgroundLabel.add(scrollHistoriquePanel, BorderLayout.CENTER);

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
            EmpruntView empruntView = new EmpruntView();
            empruntView.setVisible(true); // Afficher la fenêtre des emprunts
        });
    }
}
