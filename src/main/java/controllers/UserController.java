package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import model.Role;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserController {
    private final String fichierCSV = "src/main/resources/ressources/users.csv";  // Nom du fichier CSV contenant les utilisateurs

    // Méthode pour ajouter un utilisateur
    public void ajouterUser (User user) {
        if (user != null) {
            if (user.getRole() == null) {
                JOptionPane.showMessageDialog(null, "Rôle invalide.");
                return;
            }
            List<User> users = lireTousLesUsers(); // Récupère tous les utilisateurs
            user.setMotDePasse(hashPassword(user.getMotDePasse())); // Hachez le mot de passe avant d'ajouter
            users.add(user);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierCSV))) {
                for (User  u : users) {
                    writer.write(u.getNom() + "," + u.getPrenom() + "," + u.getEmail() + "," +
                                 u.getMotDePasse() + "," + u.getRole());
                    writer.newLine();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Méthode pour modifier un utilisateur existant
    public boolean modifierUser (String email, User updatedUser ) {
        List<String> lignes = new ArrayList<>();
        boolean userModifie = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fichierCSV))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] details = ligne.split(",");
                if (details.length == 5 && details[2].equals(email)) { // Utilisateur trouvé
                    userModifie = true;
                    // Hachez le mot de passe mis à jour
                    String hashedPassword = hashPassword(updatedUser .getMotDePasse());
                    // Remplacez l'utilisateur par ses nouvelles informations
                    String nouvelleLigne = updatedUser .getNom() + "," +
                                           updatedUser .getPrenom() + "," +
                                           updatedUser .getEmail() + "," +
                                           hashedPassword + "," +
                                           updatedUser .getRole();
                    lignes.add(nouvelleLigne);
                } else {
                    lignes.add(ligne); // Conservez les autres utilisateurs
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la lecture du fichier : " + e.getMessage());
            return false;
        }

        if (userModifie) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierCSV))) {
                for (String l : lignes) {
                    writer.write(l);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erreur lors de l'écriture dans le fichier : " + e.getMessage());
                return false;
            }
            JOptionPane.showMessageDialog(null, "Utilisateur modifié avec succès.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Utilisateur non trouvé.");
            return false;
        }
    }

    // Méthode pour supprimer un utilisateur
    public void supprimerUser (String email, User currentUser ) {
        if (currentUser .getRole() != Role.ADMINISTRATEUR) {
            JOptionPane.showMessageDialog(null, "Seul un administrateur peut supprimer un utilisateur.");
            return;
        }

        // Récupérer l'utilisateur à partir de l'email pour l'archiver
        User userToDelete = getUserByEmail(email);
        if (userToDelete == null) {
            JOptionPane.showMessageDialog(null, "Utilisateur non trouvé.");
            return;
        }

        // Archive l'utilisateur
        archiveUser (userToDelete);

        List<String> lignes = new ArrayList<>();
        boolean userSupprime = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fichierCSV))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] details = ligne.split(",");
                if (details.length == 5 && details[2].equals(email)) {
                    userSupprime = true; // Utilisateur trouvé pour suppression
                } else {
                    lignes.add(ligne); // Conserver les autres utilisateurs
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userSupprime) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierCSV))) {
                for (String l : lignes) {
                    writer.write(l);
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(null, "Utilisateur supprimé avec succès.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Utilisateur non trouvé.");
        }
    }

    // Méthode pour archiver l'utilisateur
    private void archiveUser (User user) {
        // Implémentez votre logique d'archivage ici
        // Par exemple, vous pouvez écrire l'utilisateur dans un fichier d'archive
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/ressources/archived_users.csv", true))) {
            writer.write(user.getNom() + "," + user.getPrenom() + "," + user.getEmail() + "," + user.getNumeroTel() + "," + user.getRole());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer un utilisateur par email
    public User getUserByEmail(String email) {
        List<User> users = lireTousLesUsers();
        for (User  user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null; // Utilisateur non trouvé
    }
 
 
    
    
    
    // Méthode pour lire tous les utilisateurs depuis le fichier CSV
    public List<User> lireTousLesUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fichierCSV))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] details = ligne.split(",");
                if (details.length == 5) {
                    Role role = Role.valueOf(details[4]); // Convertir la chaîne en énumération Role
                    User user = new User(details[0], details[1], details[2], details[3], role);
                    users.add(user);
                } else {
                    System.out.println("Ligne ignorée : Mauvais format - " + ligne);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Méthode pour hacher un mot de passe
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean submitRoleChangeRequest(User currentUser, User userToChange, Role newRole, String justification) {
        if (currentUser.getRole() != Role.ADMINISTRATEUR) {
            JOptionPane.showMessageDialog(null, "Seul un administrateur peut modifier le rôle d'un utilisateur.");
            return false;
        }
        if (userToChange == null || newRole == null) {
            JOptionPane.showMessageDialog(null, "Utilisateur ou rôle invalide.");
            return false;
        }

        // Changez le rôle de l'utilisateur
        userToChange.setRole(newRole);

        // Mettez à jour le fichier CSV
        List<User> users = lireTousLesUsers(); // Récupérez tous les utilisateurs
        boolean userFound = false;

        for (User  user : users) {
            if (user.getEmail().equals(userToChange.getEmail())) {
                userFound = true;
                break;
            }
        }

        if (!userFound) {
            JOptionPane.showMessageDialog(null, "Utilisateur non trouvé dans le fichier.");
            return false;
        }

        // Écrire les utilisateurs mis à jour dans le fichier CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierCSV))) {
            for (User user : users) {
                // Ne pas hacher le mot de passe à nouveau
                String passwordToWrite = user.getMotDePasse(); // Utiliser le mot de passe existant
                writer.write(user.getNom() + "," + user.getPrenom() + "," + user.getEmail() + "," +
                             passwordToWrite + "," + user.getRole());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Rôle changé avec succès.");
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'écriture dans le fichier : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
 
}