package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import model.Role;
import model.User;
import org.mindrot.jbcrypt.BCrypt; // Assurez-vous d'avoir cette bibliothèque pour le hachage

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
            users.add(user);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierCSV))) {
                for (User  u : users) {
                    String hashedPassword = BCrypt.hashpw(u.getMotDePasse(), BCrypt.gensalt());
                    writer.write(u.getNom() + "," + u.getPrenom() + "," + u.getEmail() + "," +
                                 hashedPassword + "," + u.getRole());
                    writer.newLine();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

 // Méthode pour modifier un utilisateur
 // Méthode pour modifier un utilisateur (sans cryptage du mot de passe)
    public void modifierUser(User user, String currentPassword) {
        if (user != null) {
            List<String> lignes = new ArrayList<>();
            boolean userTrouve = false;
            try (BufferedReader reader = new BufferedReader(new FileReader(fichierCSV))) {
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    String[] details = ligne.split(",");
                    if (details.length == 5 && details[2].equals(user.getEmail())) {  // Vérifie que l'utilisateur est trouvé par email
                        // Vérification du mot de passe avant modification
                        if (!details[3].equals(currentPassword)) {  // Comparaison directe avec le mot de passe actuel
                            JOptionPane.showMessageDialog(null, "Mot de passe incorrect.");
                            return;
                        }
                        // Mise à jour de l'utilisateur sans cryptage du mot de passe
                        lignes.add(user.getNom() + "," + user.getPrenom() + "," + user.getEmail() + "," +
                                   user.getMotDePasse() + "," + user.getRole());
                        userTrouve = true;
                    } else {
                        lignes.add(ligne);  // Si l'utilisateur n'est pas celui que l'on cherche, conserver la ligne
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (userTrouve) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierCSV))) {
                    for (String l : lignes) {
                        writer.write(l);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Utilisateur non trouvé.");
            }
        }
    }


    // Méthode pour supprimer un utilisateur
    public void supprimerUser (String email) {
        List<String> lignes = new ArrayList<>();
        boolean userSupprime = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fichierCSV))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] details = ligne.split(",");
                if (details.length == 5 && details[2].equals(email)) {  // Vérifie que l'utilisateur est valide
                    userSupprime = true;
                } else {
                    lignes.add(ligne);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Utilisateur non trouvé.");
        }
    }

    // Méthode pour lire tous les utilisateurs depuis le fichier CSV
    public List<User> lireTousLesUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fichierCSV))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] details = ligne.split(",");
                // Vérifiez que chaque ligne contient exactement 5 éléments avant de créer un utilisateur
                if (details.length == 5) {
                    Role role = Role.valueOf(details[4]); // Convertir la chaîne en énumération Role
                    User user = new User(details[0], details[1], details[2], details[3], role);
                    users.add(user);
                } else {
                    // Afficher un avertissement ou ignorer la ligne si elle est mal formatée
                    System.out.println("Ligne ignorée : Mauvais format - " + ligne);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}