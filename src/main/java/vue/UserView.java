package vue;

import controllers.UserController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserView extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField motDePasseField;
    private JComboBox<String> roleComboBox;
    private JTable usersTable;
    private DefaultTableModel tableModel;

    private JButton ajouterButton;
    private JButton modifierButton;
    private JButton supprimerButton;

    public UserView() {
        // Initialisation des composants
        initComponents();
        // Ajouter les composants à la fenêtre
        setLayout(new BorderLayout());
        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private void initComponents() {
        // Champs de texte pour le nom, prénom, email, mot de passe
        nomField = new JTextField(15);
        prenomField = new JTextField(15);
        emailField = new JTextField(15);
        motDePasseField = new JPasswordField(15);

        // ComboBox pour les rôles
        roleComboBox = new JComboBox<>(new String[]{"Membre", "Bibliothécaire", "Administrateur"});

        // Tableau des utilisateurs
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nom");
        tableModel.addColumn("Prénom");
        tableModel.addColumn("Email");
        tableModel.addColumn("Rôle");
        usersTable = new JTable(tableModel);
        usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Boutons
        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");
    }

    // Panel pour le formulaire
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom:"));
        panel.add(prenomField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de Passe:"));
        panel.add(motDePasseField);
        panel.add(new JLabel("Rôle:"));
        panel.add(roleComboBox);

        return panel;
    }

    // Panel pour la table des utilisateurs
    private JPanel createTablePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(usersTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Panel pour les boutons
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        panel.add(ajouterButton);
        panel.add(modifierButton);
        panel.add(supprimerButton);

        return panel;
    }

    // Méthodes pour récupérer les champs de texte, les boutons, etc.
    public JTextField getNomField() {
        return nomField;
    }

    public JTextField getPrenomField() {
        return prenomField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getMotDePasseField() {
        return motDePasseField;
    }

    public JComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    public JTable getUsersTable() {
        return usersTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getAjouterButton() {
        return ajouterButton;
    }

    public JButton getModifierButton() {
        return modifierButton;
    }

    public JButton getSupprimerButton() {
        return supprimerButton;
    }

    // Méthode pour ajouter un ActionListener aux boutons
    public void addAjouterButtonListener(ActionListener listener) {
        ajouterButton.addActionListener(listener);
    }

    public void addModifierButtonListener(ActionListener listener) {
        modifierButton.addActionListener(listener);
    }

    public void addSupprimerButtonListener(ActionListener listener) {
        supprimerButton.addActionListener(listener);
    }

    // Méthode pour afficher un message d'erreur
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    // Classe Main intégrée à UserView
    public static class Main {
        public static void main(String[] args) {
            // Crée la vue
            UserView userView = new UserView();

            // Crée le contrôleur avec la vue
            @SuppressWarnings("unused")
			UserController userController = new UserController();

            // Affiche la vue
            userView.setVisible(true);
        }
    }
}
