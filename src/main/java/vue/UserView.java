package vue;

import controllers.UserController;
import model.Role;
import model.User;
import style.IconCellRenderer;
import style.SearchBar;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.UUID;

public class UserView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField prenomField;
    private JTextField emailField;
    private JTextField numeroTelField;
    private JComboBox<Role> roleComboBox;
    private JCheckBox statutCheckBox;
    private JTable usersTable;
    private DefaultTableModel tableModel;
    private SearchBar searchBar;
    private User currentUser ;
    private UserController userController;
    public UserView(Role currentUserRole, User currentUser ) {
        this.userController = new UserController();
        this.currentUser  = currentUser ;
        initComponents();
        loadUsers();
        searchBar = new SearchBar(currentUserRole);

        setLayout(new BorderLayout());
        add(searchBar, BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createIconPanel(), BorderLayout.SOUTH);
    }

    private void initComponents() {
        new JTextField(15);
        prenomField = new JTextField(15);
        emailField = new JTextField(15);
        numeroTelField = new JTextField(15);
        roleComboBox = new JComboBox<>(Role.values());
        statutCheckBox = new JCheckBox("Actif");

        tableModel = new DefaultTableModel(new Object[]{"Nom", "Prénom", "Email", "Téléphone", "Rôle", "Statut", "Modifier", "Supprimer"}, 0);
        usersTable = new JTable(tableModel);
        usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usersTable.getColumnModel().getColumn(5).setCellRenderer(new StatusRenderer());

        usersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                usersTable.rowAtPoint(e.getPoint());
                int column = usersTable.columnAtPoint(e.getPoint());

                if (column == 6) { // Colonne Modifier
                    modifierUtilisateur();
                } else if (column == 7) { // Colonne Supprimer
                    supprimerUtilisateur();
                }
            }
        });
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(usersTable), BorderLayout.CENTER);
        return tablePanel;
    }

    private JPanel createIconPanel() {
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        iconPanel.setBackground(new Color(0, 0, 0, 0));

        JLabel addIcon = createIconLabel("/ressources/add-icon.png", "Ajouter un utilisateur", e -> ajouterUtilisateur());
        iconPanel.add(addIcon);
        return iconPanel;
    }

    private JLabel createIconLabel(String iconPath, String tooltip, ActionListener action) {
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image scaledImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaledImage));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.setToolTipText(tooltip);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(new ActionEvent(label, ActionEvent.ACTION_PERFORMED, null));
            }
        });
        return label;
    }

    private void ajouterUtilisateur() {
        UserAddDialog addDialog = new UserAddDialog(this);
        addDialog.setVisible(true);
    }

    private void modifierUtilisateur() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un utilisateur à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String email = (String) tableModel.getValueAt(selectedRow, 2);
        User userToEdit = userController.getUserByEmail(email);

        if (userToEdit == null) {
            JOptionPane.showMessageDialog(this, "Utilisateur non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Passer l'instance de UserView
        UserEditDialog editDialog = new UserEditDialog(this, userToEdit);
        editDialog.setVisible(true);
        // Note: loadUsers() est déjà appelé dans le UserEditDialog après l'enregistrement
    }
    
    private void supprimerUtilisateur() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un utilisateur à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String email = (String) tableModel.getValueAt(selectedRow, 2);
        int confirmation = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cet utilisateur ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        String password = JOptionPane.showInputDialog(this, "Veuillez entrer votre mot de passe pour confirmer la suppression :");
        if (!isPasswordValid(currentUser , password)) {
            JOptionPane.showMessageDialog(this, "Mot de passe incorrect. Suppression annulée.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        userController.supprimerUser (email, currentUser );
        tableModel.removeRow(selectedRow);
    }

    private boolean isPasswordValid(User currentUser , String password) {
        return currentUser .getMotDePasse().equals(password);
    }

    private static class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = (String) value;
            cell.setForeground("Actif".equals(status) ? Color.GREEN : Color.RED);
            return cell;
        }
    }
    private void loadUsers() {
        List<User> users = userController.lireTousLesUsers();
        tableModel.setRowCount(0);
        for (User  user : users) {
            JLabel editIcon = createIconLabel("/ressources/edit-icon.png", "Modifier", e -> modifierUtilisateur());
            JLabel deleteIcon = createIconLabel("/ressources/delete-icon.png", "Supprimer", e -> supprimerUtilisateur());

            tableModel.addRow(new Object[]{
                user.getNom(),
                user.getPrenom(),
                user.getEmail(),
                user.getNumeroTel(),
                user.getRole(),
                user.isStatut() ? "Actif" : "Inactif",
                editIcon,  // Colonne pour l'icône de modification
                deleteIcon // Colonne pour l'icône de suppression
            });
        }

        // Appliquer le renderer aux colonnes d'icônes
        usersTable.getColumnModel().getColumn(6).setCellRenderer(new IconCellRenderer()); // Modifier
        usersTable.getColumnModel().getColumn(7).setCellRenderer(new IconCellRenderer()); // Supprimer
    }
    
public class UserEditDialog extends JDialog {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JTextField numeroTelField;
    private JComboBox<Role> roleComboBox;
    private JCheckBox statutCheckBox;
    private User userToEdit;
    public UserEditDialog(UserView userView, User user) {
        super(); // Passer userView ici
        this.userToEdit = user;

        nomField = new JTextField(user.getNom(), 15);
        prenomField = new JTextField(user.getPrenom(), 15);
        emailField = new JTextField(user.getEmail(), 15);
        numeroTelField = new JTextField(user.getNumeroTel(), 15);
        roleComboBox = new JComboBox<>(Role.values());
        roleComboBox.setSelectedItem(user.getRole());
        statutCheckBox = new JCheckBox("Actif", user.isStatut());

        setLayout(new GridLayout(6, 2));
        add(new JLabel("Nom:"));
        add(nomField);
        add(new JLabel("Prénom:"));
        add(prenomField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Téléphone:"));
        add(numeroTelField);
        add(new JLabel("Rôle:"));
        add(roleComboBox);
        add(statutCheckBox);

        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(e -> {
            userToEdit.setNom(nomField.getText());
            userToEdit.setPrenom(prenomField.getText());
            userToEdit.setEmail(emailField.getText());
            userToEdit.setNumeroTel(numeroTelField.getText());
            userToEdit.setRole((Role) roleComboBox.getSelectedItem());
            userToEdit.setStatut(rootPaneCheckingEnabled);
         // Appeler la méthode pour mettre à jour l'utilisateur dans le contrôleur
            userView.getUserController().modifierUser (userToEdit.getEmail(), userToEdit); // Assurez-vous que cette méthode existe

            userView.loadUsers(); // Mettre à jour le tableau après l'enregistrement
            dispose();
        });
        add(saveButton);

        pack();
        setLocationRelativeTo(userView);
    }
}
public UserController getUserController() {
    return userController;
}
 
public class UserAddDialog extends JDialog {
    private JTextField prenomField; // Inversé
    private JTextField nomField; // Inversé
    private JTextField emailField;
    private JTextField numeroTelField;
    private JCheckBox statutCheckBox;
    private UserView userView;

    public UserAddDialog(UserView userView) {
        super();
        this.userView = userView;

        prenomField = new JTextField(15);
        nomField = new JTextField(15);
        emailField = new JTextField(15);
        numeroTelField = new JTextField(15);
        statutCheckBox = new JCheckBox("Actif");

        setLayout(new GridLayout(5, 2)); // Ajusté pour 5 lignes
        add(new JLabel("Prénom:")); // Inversé
        add(prenomField); // Inversé
        add(new JLabel("Nom:")); // Inversé
        add(nomField); // Inversé
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Téléphone:"));
        add(numeroTelField);
        add(statutCheckBox);

     // Dans UserAddDialog
        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(e -> {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String numeroTel = numeroTelField.getText();
            boolean actif = statutCheckBox.isSelected(); // Vérifiez si la case est cochée

            // Vérification des champs
            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || numeroTel.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérification de l'email
            if (userView.getUserController().getUserByEmail(email) != null) {
                JOptionPane.showMessageDialog(this, "L'email existe déjà.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérification du numéro de téléphone (doit être numérique)
            if (!numeroTel.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Le numéro de téléphone doit contenir uniquement des chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Générer un identifiant unique pour l'utilisateur
            String id = UUID.randomUUID().toString(); // Génération d'un UUID

            // Créer un nouvel utilisateur avec le rôle "Membre"
            User newUser  = new User(id, nom, prenom, email, numeroTel, "", Role.MEMBRE, actif); // Assurez-vous que le constructeur de User accepte ces paramètres
            userView.getUserController().ajouterUser (newUser );
            userView.loadUsers(); // Mettre à jour le tableau après l'ajout
            dispose();
        });
        add(saveButton);

        pack();
        setLocationRelativeTo(userView);
    }
}
}