package controllers;

import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginController {
    private static final String USER_FILE = "users.csv";
    private Map<String, String> userDatabase = new HashMap<>();

    public LoginController() {
        loadUsers();
    }

    // Charger les utilisateurs depuis le fichier CSV
    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 2) {
                    userDatabase.put(userData[0], userData[1]); // username -> password_hash
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des utilisateurs : " + e.getMessage());
        }
    }

    // Enregistrer les utilisateurs dans le fichier CSV
    private void saveUser(String username, String hashedPassword) {
        try (FileWriter writer = new FileWriter(USER_FILE, true)) {
            writer.write(username + "," + hashedPassword + "\n");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'enregistrement de l'utilisateur : " + e.getMessage());
        }
    }

    // Méthode pour hacher les mots de passe
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Authentifier l'utilisateur
    public boolean authenticate(String username, String plainPassword) {
        String storedPasswordHash = userDatabase.get(username);
        if (storedPasswordHash != null && BCrypt.checkpw(plainPassword, storedPasswordHash)) {
            System.out.println("Connexion réussie !");
            return true;
        } else {
            System.out.println("Nom d'utilisateur ou mot de passe incorrect.");
            return false;
        }
    }

    // Inscription d'un nouvel utilisateur
    public void register(String username, String plainPassword) {
        if (userDatabase.containsKey(username)) {
            System.out.println("Nom d'utilisateur déjà utilisé. Choisissez un autre nom.");
            return;
        }

        String hashedPassword = hashPassword(plainPassword);
        userDatabase.put(username, hashedPassword);
        saveUser(username, hashedPassword);
        System.out.println("Utilisateur inscrit avec succès !");
    }

    // Réinitialisation du mot de passe
    public void resetPassword(String username, String newPassword) {
        if (!userDatabase.containsKey(username)) {
            System.out.println("Nom d'utilisateur introuvable.");
            return;
        }

        String hashedPassword = hashPassword(newPassword);
        userDatabase.put(username, hashedPassword);
        // Réécrire tout le fichier CSV avec le nouveau mot de passe pour l'utilisateur spécifié
        try (FileWriter writer = new FileWriter(USER_FILE, false)) {
            for (Map.Entry<String, String> entry : userDatabase.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
            System.out.println("Mot de passe réinitialisé avec succès !");
        } catch (IOException e) {
            System.err.println("Erreur lors de la réinitialisation du mot de passe : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        LoginController loginController = new LoginController();

        // Exemple d'inscription
        loginController.register("user1", "password123");

        // Exemple d'authentification
        loginController.authenticate("user1", "password123");

        // Exemple de réinitialisation du mot de passe
        loginController.resetPassword("user1", "newpassword123");

        // Vérification de l'authentification avec le nouveau mot de passe
        loginController.authenticate("user1", "newpassword123");
    }
}
