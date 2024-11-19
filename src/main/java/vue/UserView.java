package vue;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class UserView extends JFrame {
    private JTextField nomField, prenomField, emailField;
    private JPasswordField motDePasseField;
    private JComboBox<String> roleComboBox;
    private JButton ajouterButton, modifierButton, supprimerButton;
    private JTable userTable;
    private DefaultTableModel tableModel;

    public UserView() {
        setTitle("Gestion des Utilisateurs");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panneau supérieur pour les champs d'entrée
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Nom:"));
        nomField = new JTextField();
        inputPanel.add(nomField);

        inputPanel.add(new JLabel("Prénom:"));
        prenomField = new JTextField();
        inputPanel.add(prenomField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Mot de passe:"));
        motDePasseField = new JPasswordField();
        inputPanel.add(motDePasseField);

        inputPanel.add(new JLabel("Rôle:"));
        roleComboBox = new JComboBox<>(new String[]{"Membre", "Bibliothécaire", "Administrateur"});
        inputPanel.add(roleComboBox);

        add(inputPanel, BorderLayout.NORTH);

        // Panneau central pour la table
        tableModel = new DefaultTableModel(new String[]{"Nom", "Prénom", "Email", "Rôle"}, 0);
        userTable = new JTable(tableModel);
        add(new JScrollPane(userTable), BorderLayout.CENTER);

        // Panneau inférieur pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");
        buttonPanel.add(ajouterButton);
        buttonPanel.add(modifierButton);
        buttonPanel.add(supprimerButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Getters pour les champs et les boutons
    public JTextField getNomField() { return nomField; }
    public JTextField getPrenomField() { return prenomField; }
    public JTextField getEmailField() { return emailField; }
    public JPasswordField getMotDePasseField() { return motDePasseField; }
    public JComboBox<String> getRoleComboBox() { return roleComboBox; }
    public JButton getAjouterButton() { return ajouterButton; }
    public JButton getModifierButton() { return modifierButton; }
    public JButton getSupprimerButton() { return supprimerButton; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getUserTable() { return userTable; }
}

