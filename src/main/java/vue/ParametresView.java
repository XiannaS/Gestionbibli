package vue;

import javax.swing.*;

import controllers.UserController;
import model.Role;
import model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParametresView extends JPanel {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField currentPasswordField; // Champ pour le mot de passe actuel
    private JPasswordField newPasswordField; // Champ pour le nouveau mot de passe
    private JPasswordField confirmPasswordField; // Champ pour confirmer le nouveau mot de passe
    private JCheckBox notificationCheckBox;
    private JCheckBox notificationDueCheckBox;
    private JTextField currentRoleField;
    private JComboBox<Role> requestedRoleComboBox; // Utilisation de JComboBox
    private JTextArea justificationArea;
    private UserController userController;
    private User currentUser ;
    
    public ParametresView(User user, UserController userController) {
        this.currentUser  = user;
        this.userController = new UserController();
// Ajoutez UserView comme paramètre
        Role role = currentUser .getRole(); // Obtenir le rôle de l'utilisateur connecté
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
   
        
     
        // Titre
        JLabel titleLabel = new JLabel("Paramètres de Compte");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        gbc.gridwidth = 1; // Reset gridwidth

        // Panneau d'informations utilisateur
        addUserInfoPanel(gbc, currentUser ); // Passer l'utilisateur pour remplir les informations

        // Panneau de paramètres de notification
        addNotificationSettingsPanel(gbc);

        // Options spécifiques selon le rôle
        if (role == Role.MEMBRE) {
            addRoleChangeRequestPanel(gbc, role);
        }

        if (role == Role.BIBLIOTHECAIRE) {
            addLibrarianOptions(gbc);
        } else if (role == Role.ADMINISTRATEUR) {
            addAdminOptions(gbc);
        }
    }
    

    private void addUserInfoPanel(GridBagConstraints gbc, User currentUser ) {
        JPanel userInfoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints userGbc = new GridBagConstraints();
        userGbc.insets = new Insets(5, 5, 5, 5);
        userGbc.fill = GridBagConstraints.HORIZONTAL;

        userGbc.gridx = 0;
        userGbc.gridy = 0;
        userInfoPanel.add(new JLabel("Nom :"), userGbc);
        nameField = new JTextField(20);
        nameField.setText(currentUser .getNom()); // Remplir avec le nom de l'utilisateur connecté
        userGbc.gridx = 1;
        userInfoPanel.add(nameField, userGbc);

        userGbc.gridx = 0;
        userGbc.gridy = 1;
        userInfoPanel.add(new JLabel("Email :"), userGbc);
        emailField = new JTextField(20);
        emailField.setText(currentUser .getEmail()); // Remplir avec l'email de l'utilisateur connecté
        userGbc.gridx = 1;
        userInfoPanel.add(emailField, userGbc);

        // Ajout des champs pour le changement de mot de passe
        userGbc.gridx = 0;
        userGbc.gridy = 2;
        userInfoPanel.add(new JLabel("Mot de passe actuel :"), userGbc);
        currentPasswordField = new JPasswordField(20);
        userGbc.gridx = 1;
        userInfoPanel.add(currentPasswordField, userGbc);

        userGbc.gridx = 0;
        userGbc.gridy = 3;
        userInfoPanel.add(new JLabel("Nouveau mot de passe :"), userGbc);
        newPasswordField = new JPasswordField(20);
        userGbc.gridx = 1;
        userInfoPanel.add(newPasswordField, userGbc);

        userGbc.gridx = 0;
        userGbc.gridy = 4;
        userInfoPanel.add(new JLabel("Confirmer le mot de passe :"), userGbc);
        confirmPasswordField = new JPasswordField(20);
        userGbc.gridx = 1;
        userInfoPanel.add(confirmPasswordField, userGbc);

        JButton saveButton = new JButton("Sauvegarder");
        saveButton.addActionListener(new SaveButtonListener(currentUser  ));
        userGbc.gridx = 0;
        userGbc.gridy = 5;
        userGbc.gridwidth = 2;
        userInfoPanel.add(saveButton, userGbc);

        gbc.gridy++;
        add(userInfoPanel, gbc);
    }

    private class SaveButtonListener implements ActionListener {
        private User currentUser ;

        public SaveButtonListener(User currentUser ) {
            this.currentUser  = currentUser ;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String enteredCurrentPassword = new String(currentPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Vérification du mot de passe actuel
            if (!enteredCurrentPassword.equals(currentUser .getMotDePasse())) {
                JOptionPane.showMessageDialog(ParametresView.this, "Le mot de passe actuel est incorrect !");
                return;
            }

            // Vérification des mots de passe
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(ParametresView.this, "Les mots de passe ne correspondent pas !");
                return;
            }

            // Validation de l'email
            String newEmail = emailField.getText();
            if (newEmail.isEmpty() || !newEmail.contains("@")) {
                JOptionPane.showMessageDialog(ParametresView.this, "Veuillez entrer un email valide !");
                return;
            }

            // Mettre à jour l'utilisateur avec les nouvelles informations
            currentUser .setMotDePasse(newPassword); // Mettez à jour le mot de passe dans l'objet User
            currentUser .setEmail(newEmail); // Mettez à jour l'email dans l'objet User

            // Appel de la méthode pour modifier l'utilisateur
            userController.modifierUser (currentUser , enteredCurrentPassword); // Passez currentUser  à la méthode

            JOptionPane.showMessageDialog(ParametresView.this, "Mot de passe et email changés avec succès !");
        }
    }
    
    private void addNotificationSettingsPanel(GridBagConstraints gbc) {
        JPanel notificationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints notifGbc = new GridBagConstraints();
        notifGbc.insets = new Insets(5, 5, 5, 5);
        notifGbc.fill = GridBagConstraints.HORIZONTAL;

        notificationCheckBox = new JCheckBox("Recevoir des notifications par email");
        notifGbc.gridx = 0;
        notifGbc.gridy = 0;
        notifGbc.gridwidth = 2;
        notificationPanel.add(notificationCheckBox, notifGbc);

        notificationDueCheckBox = new JCheckBox("Recevoir des notifications avant la fin des prêts");
        notifGbc.gridy = 1;
        notificationPanel.add(notificationDueCheckBox, notifGbc);

        gbc.gridy++;
        add(notificationPanel, gbc);
    }

    private void addRoleChangeRequestPanel(GridBagConstraints gbc, Role role) {
        JPanel roleChangePanel = new JPanel(new GridBagLayout());
        GridBagConstraints roleGbc = new GridBagConstraints();
        roleGbc.insets = new Insets(5, 5, 5, 5);
        roleGbc.fill = GridBagConstraints.HORIZONTAL;

        roleGbc.gridx = 0;
        roleGbc.gridy = 0;
        roleChangePanel.add(new JLabel("Rôle actuel :"), roleGbc);
        currentRoleField = new JTextField(20);
        currentRoleField.setEditable(false); // Rendre le champ non modifiable
        currentRoleField.setText(role.getLabel()); // Afficher le rôle actuel
        roleGbc.gridx = 1;
        roleChangePanel.add(currentRoleField, roleGbc);

        roleGbc.gridx = 0;
        roleGbc.gridy = 1;
        roleChangePanel.add(new JLabel("Rôle demandé :"), roleGbc);
        requestedRoleComboBox = new JComboBox<>(new Role[]{Role.BIBLIOTHECAIRE, Role.ADMINISTRATEUR}); // Exclure MEMBRE
        roleGbc.gridx = 1;
        roleChangePanel.add(requestedRoleComboBox, roleGbc);

        roleGbc.gridx = 0;
        roleGbc.gridy = 2;
        roleChangePanel.add(new JLabel("Justification :"), roleGbc);
        justificationArea = new JTextArea(5, 20);
        roleGbc.gridx = 1;
        roleGbc.fill = GridBagConstraints.BOTH;
        roleChangePanel.add(new JScrollPane(justificationArea), roleGbc);

        JButton submitRoleChangeButton = new JButton("Soumettre la demande de changement de rôle");
        submitRoleChangeButton.addActionListener(new SubmitRoleChangeButtonListener());
        roleGbc.gridx = 0;
        roleGbc.gridy = 3;
        roleGbc.gridwidth = 2;
        roleChangePanel.add(submitRoleChangeButton, roleGbc);

        gbc.gridy++;
        add(roleChangePanel, gbc);
    }

    private void addLibrarianOptions(GridBagConstraints gbc) {
        JLabel librarianLabel = new JLabel("Options Bibliothécaire");
        gbc.gridy++;
        add(librarianLabel, gbc);
    }

    private void addAdminOptions(GridBagConstraints gbc) {
        JLabel adminLabel = new JLabel("Options Administrateur");
        gbc.gridy++;
        add(adminLabel, gbc);
    }

    private class SubmitRoleChangeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentRole = currentRoleField.getText();
            Role requestedRole = (Role) requestedRoleComboBox.getSelectedItem();
            String justification = justificationArea.getText();

            JOptionPane.showMessageDialog(ParametresView.this, 
                "Demande de changement de rôle soumise !\n" +
                "Rôle actuel : " + currentRole + "\n" +
                "Rôle demandé : " + requestedRole.getLabel() + "\n" +
                "Justification : " + justification);
        }
    }
}