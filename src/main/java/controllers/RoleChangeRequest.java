package controllers;

import model.Role;
import model.User; // Assurez-vous d'importer la classe User
import java.util.List; // Importez les classes nécessaires pour la gestion des utilisateurs

public class RoleChangeRequest {
    private String userId;
    private Role requestedRole;
    private String justification;
    private String userType; // Nouveau champ pour le type d'utilisateur

    public RoleChangeRequest(String userId, Role requestedRole, String justification, String userType) {
        this.userId = userId;
        this.requestedRole = requestedRole;
        this.justification = justification;
        this.setUserType(userType);
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    // Getters et setters pour les autres champs
    public String getUserId() {
        return userId;
    }

    public Role getRequestedRole() {
        return requestedRole;
    }

    public String getJustification() {
        return justification;
    }

    // Méthode pour changer le rôle de l'utilisateur
    public boolean changeUserRole(User user, List<User> userList) {
        // Vérifiez si l'utilisateur existe dans la liste
        for (User  u : userList) {
            if (u.getId() == userId) {
                // Mettre à jour le rôle de l'utilisateur
                u.setRole(requestedRole);
                return true; // Indique que le changement de rôle a réussi
            }
        }
        return false; // Indique que l'utilisateur n'a pas été trouvé
    }
}