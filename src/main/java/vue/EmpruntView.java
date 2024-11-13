package vue;

import controllers.EmpruntController;
import model.Emprunt;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EmpruntView extends JFrame {

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
        setLocationRelativeTo(null);
        setLayout(null); // Utiliser null layout pour positionner manuellement les composants

        // Charger l'image de fond
        ImageIcon backgroundImage = new ImageIcon("/Users/mac/Desktop/your_image.jpg"); 
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout()); // Permet l'ajout de panels par-dessus l'image

        // Ajouter le JLabel avec l'image de fond à la fenêtre
        getContentPane().add(backgroundLabel);

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
				// TODO Auto-generated method stub
				
			}       
        });
        panelAjout.add(btnAjouter);
        
        // Panel pour la modification de la date de retour
        JPanel panelModification = new JPanel();
        panelModification.setLayout(new GridLayout(3, 2, 10, 10));
        panelModification.setBorder(BorderFactory.createTitledBorder("Modifier la date de retour"));
        
        JTextField tfIdEmpruntModif = new JTextField();
        JTextField tfNouvelleDateRetour = new JTextField();

        panelModification.add(new JLabel("ID Emprunt à modifier:"));
        panelModification.add(tfIdEmpruntModif);
        panelModification.add(new JLabel("Nouvelle Date Retour (yyyy-MM-dd):"));
        panelModification.add(tfNouvelleDateRetour);

        JButton btnModifier = new JButton("Modifier");
        btnModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierDateRetour(tfIdEmpruntModif.getText(), tfNouvelleDateRetour.getText());
            }

			private void modifierDateRetour(String text, String text2) {
				// TODO Auto-generated method stub
				
			}
        });
        panelModification.add(btnModifier);

        // Panel pour marquer un emprunt comme retourné et le supprimer
        JPanel panelRetour = new JPanel();
        panelRetour.setLayout(new GridLayout(2, 2, 10, 10));
        panelRetour.setBorder(BorderFactory.createTitledBorder("Retourner et supprimer un emprunt"));
        
        JTextField tfIdEmpruntRetour = new JTextField();
        JTextField tfIdEmpruntSuppression = new JTextField();

        panelRetour.add(new JLabel("ID Emprunt à retourner:"));
        panelRetour.add(tfIdEmpruntRetour);
        panelRetour.add(new JLabel("ID Emprunt à supprimer:"));
        panelRetour.add(tfIdEmpruntSuppression);

        JButton btnRetourner = new JButton("Retourner le livre");
        btnRetourner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marquerCommeRetourne(tfIdEmpruntRetour.getText());
            }

			private void marquerCommeRetourne(String text) {
				// TODO Auto-generated method stub
				
			}
        });

        JButton btnSupprimer = new JButton("Supprimer Emprunt");
        btnSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerEmprunt(tfIdEmpruntSuppression.getText());
            }

			private void supprimerEmprunt(String text) {
				// TODO Auto-generated method stub
				
			}
        });

        panelRetour.add(btnRetourner);
        panelRetour.add(btnSupprimer);

        // Panel pour afficher l'historique des emprunts
        JPanel panelHistorique = new JPanel();
        panelHistorique.setLayout(new BorderLayout());
        panelHistorique.setBorder(BorderFactory.createTitledBorder("Historique des Emprunts"));

        taHistorique = new JTextArea();
        taHistorique.setEditable(false);
        JScrollPane scrollHistorique = new JScrollPane(taHistorique);
        panelHistorique.add(scrollHistorique, BorderLayout.CENTER);

        JButton btnAfficherHistorique = new JButton("Afficher Historique");
        btnAfficherHistorique.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherHistorique();
            }

			private void afficherHistorique() {
				// TODO Auto-generated method stub
				
			}
        });
        panelHistorique.add(btnAfficherHistorique, BorderLayout.SOUTH);

        // Table pour afficher les emprunts
        String[] columnNames = {"ID Emprunt", "ID Utilisateur", "ID Livre", "Date Emprunt", "Date Retour", "Est Rendu"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableEmprunts = new JTable(tableModel);
        JScrollPane scrollTable = new JScrollPane(tableEmprunts);

        // Ajouter tous les panels à la fenêtre principale
        panelPrincipal.add(panelAjout);
        panelPrincipal.add(panelModification);
        panelPrincipal.add(panelRetour);
        panelPrincipal.add(panelHistorique);

        // Ajouter le panel principal avec les autres composants par-dessus l'image
        backgroundLabel.add(panelPrincipal, BorderLayout.NORTH);
        backgroundLabel.add(scrollTable, BorderLayout.CENTER); 

       /* // Gérer le redimensionnement de l'image de fond
        addComponentListener(new ComponentAdapter() {
            @Override
            public*/
        }}

