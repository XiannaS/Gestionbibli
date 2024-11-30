package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import model.Role;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Random;

public class UserController {
    private final String fichierCSV = "src/main/resources/ressources/users.csv";  // Nom du fichier CSV contenant les utilisateurs

    
 // Ajoutez cette méthode à votre classe UserController
 private String generateRandomId(int length) {
     String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
     StringBuilder id = new StringBuilder();
     Random random = new Random();
     for (int i = 0; i < length; i++) {
         id.append(characters.charAt(random.nextInt(characters.length())));
     }
     return id.toString();
 }

 // Ajoutez cette méthode pour vérifier si l'ID existe déjà
 private boolean idExists(String id) {
     List<User> users = lireTousLesUsers();
     for (User  user : users) {
         if (user.getId().equals(id)) {
             return true; // L'ID existe déjà
         }
     }
     return false; // L'ID est unique
 }

 // Modifiez la méthode ajouterUser 
 public void ajouterUser (User user) {
     if (user != null) {
         if (user.getRole() == null) {
             JOptionPane.showMessageDialog(null, "Rôle invalide.");
             return;
         }
         
         // Générer un nouvel ID unique de 4 caractères
         String newId;
         do {
             newId = generateRandomId(4);
         } while (idExists(newId)); // Vérifiez que l'ID n'existe pas déjà
         user.setId(newId); // Assignez l'ID généré à l'utilisateur

         List<User> users = lireTousLesUsers(); // Récupère tous les utilisateurs

         // Hachez le mot de passe seulement si l'utilisateur est un administrateur
         if (user.getRole() == Role.ADMINISTRATEUR && user.getMotDePasse() != null && !user.getMotDePasse().isEmpty()) {
             user.setMotDePasse(hashPassword(user.getMotDePasse()));
         } else {
             user.setMotDePasse(""); // Assurez-vous que le mot de passe est vide pour les membres
         }

         users.add(user);
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierCSV))) {
             for (User  u : users) {
                 writer.write(u.getId() + "," + u.getNom() + "," + u.getPrenom() + "," +
                              u.getEmail() + "," + u.getNumeroTel() + "," +
                              u.getMotDePasse() + "," + u.getRole() + "," + u.isStatut());
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
                if (details.length == 8 && details[3].equals(email)) { // Vérifiez que vous attendez 8 colonnes
                    userModifie = true;

                    // Hachez le mot de passe mis à jour seulement si l'utilisateur est un administrateur
                    String hashedPassword = (updatedUser .getRole() == Role.ADMINISTRATEUR  && updatedUser .getMotDePasse() != null && !updatedUser .getMotDePasse().isEmpty())
                            ? hashPassword(updatedUser .getMotDePasse())
                            : ""; // Laissez-le vide pour les membres

     // Remplacez l'utilisateur par ses nouvelles informations
                    String nouvelleLigne = updatedUser  .getId() + "," +
                                           updatedUser  .getNom() + "," +
                                           updatedUser  .getPrenom() + "," +
                                           updatedUser  .getEmail() + "," +
                                           updatedUser  .getNumeroTel() + "," +
                                           hashedPassword + "," +
                                           updatedUser  .getRole() + "," +
                                           updatedUser  .isStatut();
                    lignes.add(nouvelleLigne);
                } else {
                    lignes.add(ligne); // Conservez les autres utilisateurs
                }
            }

            if (userModifie) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierCSV))) {
                    for (String l : lignes) {
                        writer.write(l);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la modification de l'utilisateur : " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la lecture du fichier : " + e.getMessage());
            e.printStackTrace();
        }
        return userModifie;
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
                if (details.length == 8) { // Assurez-vous que vous attendez 8 colonnes
                    try {
                        String id = details[0]; // Lire l'identifiant
                        String nom = details[1];
                        String prenom = details[2];
                        String email = details[3];
                        String numeroTel = details[4]; // Lire le numéro de téléphone
                        String motDePasse = details[5]; // Haché ou vide
                        Role role = Role.valueOf(details[6].toUpperCase()); // Convertir la chaîne en énumération Role
                        boolean statut = Boolean.parseBoolean(details[7]); // Lire le statut

                        // Créez l'utilisateur avec tous les champs, y compris l'identifiant
                        User user = new User(id, nom, prenom, email, numeroTel, motDePasse, role, statut);
                        users.add(user);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Rôle inconnu dans la ligne : " + ligne);
                    }
                } else {
                    System.err.println("Ligne ignorée (format incorrect) : " + ligne);
                }
            }
            System.out.println("Nombre d'utilisateurs lus : " + users.size());
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Le fichier des utilisateurs est introuvable : " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la lecture des utilisateurs : " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }
    
    // Méthode pour hacher un mot de passe
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

 
}