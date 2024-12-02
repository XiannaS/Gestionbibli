package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

import controllers.EmpruntController;
import controllers.UserController;
import model.Emprunt;
import model.Livre;
import model.User;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class EmpruntView extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable tableEmprunts;
    private DefaultTableModel tableModel;
    private EmpruntController empruntController;
    private UserController userController; 
    private JComboBox<User> cbUser;
    private JComboBox<Livre> cbLivre;
    private JDateChooser dateEmpruntChooser;
    private JDateChooser dateRetourChooser;

    public EmpruntView() {
        this.empruntController = new EmpruntController();
        this.userController = new UserController();
        setLayout(new BorderLayout());
        initializeComponents();
        loadUsers();
        loadLivres();
        loadEmprunts();
    }

    private void initializeComponents() {
        // Panel for form inputs
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(5, 2, 10, 10));

        // User dropdown
        panelForm.add(new JLabel("Utilisateur :"));
        cbUser = new JComboBox<>();
        panelForm.add(cbUser);

        // Livre dropdown
        panelForm.add(new JLabel("Livre :"));
        cbLivre = new JComboBox<>();
        panelForm.add(cbLivre);

        // Date Emprunt
        panelForm.add(new JLabel("Date d'emprunt :"));
        dateEmpruntChooser = new JDateChooser();
        panelForm.add(dateEmpruntChooser);

        // Date Retour
        panelForm.add(new JLabel("Date de retour :"));
        dateRetourChooser = new JDateChooser();
        panelForm.add(dateRetourChooser);

        // Button for adding emprunt
        JButton btnAjouter = new JButton("Ajouter Emprunt");
        btnAjouter.addActionListener(e -> ajouterEmprunt());
        panelForm.add(btnAjouter);

        add(panelForm, BorderLayout.NORTH);

        // Table for emprunts
        tableModel = new DefaultTableModel(new String[]{"ID", "Utilisateur", "Livre", "Date Emprunt", "Date Retour", "Rendu"}, 0);
        tableEmprunts = new JTable(tableModel);
        add(new JScrollPane(tableEmprunts), BorderLayout.CENTER);
    }

    private void ajouterEmprunt() {
        User selectedUser  = (User ) cbUser .getSelectedItem();
        Livre selectedLivre = (Livre) cbLivre.getSelectedItem();
        LocalDate dateEmprunt = dateEmpruntChooser.getDate() != null
                ? dateEmpruntChooser.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                : null;
        LocalDate dateRetour = dateRetourChooser.getDate() != null
                ? dateRetourChooser.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                : null;

        // Validate inputs
        if (selectedUser  == null || selectedLivre == null || dateEmprunt == null || dateRetour == null) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        // Validate the return date
        if (dateRetour.isAfter(dateEmprunt.plusDays(14))) {
            JOptionPane.showMessageDialog(this, "La date de retour ne peut pas dépasser 14 jours.");
            return;
        }

        // Check if the book is available
        if (!selectedLivre.isDisponible()) {
            JOptionPane.showMessageDialog(this, "Ce livre est déjà emprunté par un autre utilisateur.");
            return;
        }

        // Call the controller to add the emprunt
        empruntController.ajouterEmprunt(Integer.parseInt(selectedUser .getId()), selectedLivre.getId(), dateEmprunt, dateRetour);
        JOptionPane.showMessageDialog(this, "Emprunt ajouté avec succès.");
        loadEmprunts(); // Refresh the table to show the new emprunt
    }

    private void loadUsers() {
        List<User> users = userController.lireTousLesUsers();
        cbUser.removeAllItems(); // Clear existing items
        for (User user : users) {
            cbUser.addItem(user);
        }
    }

    private void loadLivres() {
        List<Livre> livres = empruntController.getLivres();
        cbLivre.removeAllItems(); // Clear existing items
        for (Livre livre : livres) {
            cbLivre.addItem(livre);
        }
    }

    private void loadEmprunts() {
        List<Emprunt> emprunts = empruntController.afficherHistoriqueEmprunts();
        tableModel.setRowCount(0); // Clear existing rows
        for (Emprunt emprunt : emprunts) {
            tableModel.addRow(new Object[]{
                    emprunt.getId(),
                    emprunt.getUtilisateurId(),
                    emprunt.getLivreId(),
                    emprunt.getDateEmprunt(),
                    emprunt.getDateRetour(),
                    emprunt.isEstRendu() ? "Oui" : "Non"
            });
        }
    }
}
