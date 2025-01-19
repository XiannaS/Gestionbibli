package controllers;

import model.Role;
import model.User;
import model.UserDAO;
import vue.UserView;

import javax.swing.*;
import exception.UserException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Le contrôleur des utilisateurs. Cette classe gère les interactions entre la vue
 * (UserView) et la base de données (UserDAO) concernant les utilisateurs.
 * Elle permet d'ajouter, de modifier, de supprimer et de rechercher des utilisateurs.
 * Elle met également à jour l'affichage de la liste des utilisateurs.
 */

public class UserController {
    private UserView userView;
    private UserDAO userDAO;
    
    /**
     * Constructeur du contrôleur des utilisateurs.
     * @param userView La vue associée aux utilisateurs.
     * @param userDAO  Le DAO permettant d'accéder aux données des utilisateurs.
     */
    
    public UserController(UserView userView, UserDAO userDAO) {
        this.userView = userView;
        this.userDAO = userDAO;

        userView.getAjouterButton().addActionListener(e -> ajouterUtilisateur());
        userView.getModifierButton().addActionListener(e -> modifierUtilisateur());
        userView.getSupprimerButton().addActionListener(e -> supprimerUtilisateur());
        userView.getSearchButton().addActionListener(e -> rechercherUtilisateurs());
        userView.getUserTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = userView.getUserTable().getSelectedRow();
                if (selectedRow != -1) {
                    afficherDetailsUtilisateur();
                }
            }
        });

        displayUsers();
        
    }
    /**
     * Recherche les utilisateurs en fonction du texte saisi dans le champ de recherche.
     */
	private void rechercherUtilisateurs() {
	    // Récupérer le texte saisi dans le champ de recherche
	    String query = userView.getSearchField().getText().trim();
	
	    // Filtrer les utilisateurs en fonction du texte de recherche
	    List<User> filteredUsers = userDAO.rechercherParCritere(query);
	
	    // Mettre à jour la table avec les utilisateurs filtrés
	    userView.updateUserTable(filteredUsers);
	}
	 /**
     * Ajoute un nouvel utilisateur à la base de données.
     * Les données sont récupérées depuis les champs de saisie de la vue.
     */
    public void ajouterUtilisateur() {
        String id = String.valueOf(System.currentTimeMillis());
        // Utilisation de getText() pour récupérer le contenu des champs
        String nom = userView.getNomField().getText();
        String prenom = userView.getPrenomField().getText();
        String email = userView.getEmailField().getText();
        String numeroTel = userView.getNumeroTelField().getText();
        Role role = userView.getSelectedRole();
        boolean statut = userView.isStatutChecked();

        User user = new User(id, nom, prenom, email, numeroTel, "", role, statut);

        try {
            userDAO.addUser(user);
            displayUsers();
            JOptionPane.showMessageDialog(userView, "Utilisateur ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            userView.clearFields();
        
        } catch (UserException e) {
            JOptionPane.showMessageDialog(userView, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Modifie un utilisateur existant.
     * L'utilisateur sélectionné dans la vue est mis à jour avec les nouvelles informations saisies.
     */
    private void modifierUtilisateur() {
        int selectedRow = userView.getUserTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(userView, "Veuillez sélectionner un utilisateur à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Récupérer l'ID de l'utilisateur sélectionné
        String id = (String) userView.getUserTable().getValueAt(selectedRow, 0);

        // Récupérer les valeurs des champs de saisie
        String nom = userView.getNomField().getText();
        String prenom = userView.getPrenomField().getText();
        String email = userView.getEmailField().getText();
        String numeroTel = userView.getNumeroTelField().getText();
        Role role = userView.getSelectedRole();
        boolean statut = userView.isStatutChecked();

        // Créer un objet User avec les nouvelles informations
        User user = new User(id, nom, prenom, email, numeroTel, "", role, statut);

        try {
            userDAO.updateUser(user);
            displayUsers();  // Actualiser l'affichage
            JOptionPane.showMessageDialog(userView, "Utilisateur mis à jour avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            userView.clearFields();
           
        } catch (UserException e) {
            JOptionPane.showMessageDialog(userView, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Supprime un utilisateur de la base de données.
     * L'utilisateur sélectionné dans la vue est supprimé.
     */
    
    private void supprimerUtilisateur() {
        int selectedRow = userView.getUserTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(userView, "Veuillez sélectionner un utilisateur à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Récupérer l'ID de l'utilisateur sélectionné
        String id = (String) userView.getUserTable().getValueAt(selectedRow, 0);
        
        try {
            userDAO.deleteUser (id); // Suppression de l'utilisateur
            userView.clearFields();
            JOptionPane.showMessageDialog(userView, "Utilisateur supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            userView.clearFields();
            displayUsers(); // Actualiser l'affichage
        } catch (IOException e) {
            JOptionPane.showMessageDialog(userView, "Erreur lors de la suppression de l'utilisateur : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Affiche les détails d'un utilisateur sélectionné.
     * Les champs de saisie de la vue sont pré-remplis avec les données de l'utilisateur.
     */
    private void afficherDetailsUtilisateur() {
        // Récupérer la ligne sélectionnée
        int selectedRow = userView.getUserTable().getSelectedRow();
        if (selectedRow != -1) {
            // Récupérer l'ID de l'utilisateur à partir de la table (supposons que l'ID soit dans la première colonne)
            String userId = (String) userView.getUserTable().getValueAt(selectedRow, 0);
            
            // Rechercher l'utilisateur par ID dans le DAO
            User selectedUser = userDAO.rechercherParID(userId);

            // Si l'utilisateur est trouvé, pré-remplir les champs du formulaire
            if (selectedUser != null) {
                userView.getNomField().setText(selectedUser.getNom());
                userView.getPrenomField().setText(selectedUser.getPrenom());
                userView.getEmailField().setText(selectedUser.getEmail());
                userView.getNumeroTelField().setText(selectedUser.getNumeroTel());
                userView.getRoleComboBox().setSelectedItem(selectedUser.getRole());
                userView.getStatutCheckBox().setSelected(selectedUser.isStatut());
            }
        }
    }



    /**
     * Met à jour l'affichage des utilisateurs dans la vue.
     */
    /**
     * Met à jour l'affichage des utilisateurs dans la vue.
     * L'administrateur est exclu de la liste.
     */
    public void displayUsers() {
        List<User> users = userDAO.getAllUsers()
            .stream()
            .filter(user -> !user.getRole().equals(Role.ADMINISTRATEUR)) // Exclure l'administrateur
            .collect(Collectors.toList());
        
        userView.displayUsers(users);
    }
    
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public int getNombreUtilisateursActifs() {
        return (int) userDAO.getAllUsers().stream()
                .filter(User::isStatut) // Filtrer les utilisateurs actifs
                .count();
    }

    public int getTotalUsers() {
        return userDAO.getAllUsers().size();
    }
}