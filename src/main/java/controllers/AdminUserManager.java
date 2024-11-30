package controllers;

import java.io.*;
import javax.swing.*;
import model.Role;
import model.User;
import java.util.Random;

public class AdminUserManager {

    private final String fichierCSV = "src/main/resources/ressources/users.csv"; // Chemin vers le fichier CSV

    /**
     * Ajoute un utilisateur avec le rôle Administrateur dans le fichier users.csv.
     * @param nom Le nom de l'utilisateur.
     * @param prenom Le prénom de l'utilisateur.
     * @param email L'email de l'utilisateur.
     * @param numeroTel Le numéro de téléphone de l'utilisateur.
     * @param motDePasse Le mot de passe de l'utilisateur.
     * @return true si l'utilisateur est ajouté avec succès, false sinon.
     */
    public boolean ajouterAdmin(String nom, String prenom, String email, String numeroTel, String motDePasse) {
        if (nom == null || prenom == null || email == null || numeroTel == null || motDePasse == null ||
            nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || numeroTel.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tous les champs sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Vérifiez si l'utilisateur existe déjà dans le fichier CSV
        if (utilisateurExiste(email)) {
            JOptionPane.showMessageDialog(null, "Un utilisateur avec cet email existe déjà.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Générer un nouvel ID unique de 4 caractères
        String newId;
        do {
            newId = generateRandomId(4);
        } while (idExists(newId)); // Vérifiez que l'ID n'existe pas déjà

        // Création de l'utilisateur Administrateur
        User admin = new User(newId, nom, prenom, email, numeroTel, hashMotDePasse(motDePasse), Role.ADMINISTRATEUR, false);

        // Ajout de l'utilisateur dans le fichier CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierCSV, true))) {
            StringBuilder sb = new StringBuilder();
            sb.append(admin.getId()).append(",")
              .append(admin.getNom()).append(",")
              .append(admin.getPrenom()).append(",")
              .append(admin.getEmail()).append(",")
              .append(admin.getNumeroTel()).append(",")
              .append(admin.getMotDePasse()).append(",")
              .append(admin.getRole());
            writer.write(sb.toString());
            writer.newLine();
            JOptionPane.showMessageDialog(null, "Administrateur ajouté avec succès !");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de l'administrateur : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Vérifie si un utilisateur avec l'email donné existe dans le fichier CSV.
     * @param email L'email à vérifier.
     * @return true si l'utilisateur existe, false sinon.
     */
    private boolean utilisateurExiste(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichierCSV))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] details = ligne.split(",");
                if (details.length >= 3 && details[3].equalsIgnoreCase(email)) {
                    return true; // L'utilisateur existe déjà
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Hache un mot de passe en utilisant une bibliothèque de hachage (par exemple BCrypt).
     * @param motDePasse Le mot de passe en clair.
     * @return Le mot de passe haché.
     */
    private String hashMotDePasse(String motDePasse) {
        return org.mindrot.jbcrypt.BCrypt.hashpw(motDePasse, org.mindrot.jbcrypt.BCrypt.gensalt());
    }

    /**
     * Génère un ID aléatoire de 4 caractères.
     * @param length La longueur de l'ID à générer.
     * @return Un ID aléatoire sous forme de chaîne.
     */
    private String generateRandomId(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder id = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            id.append(characters.charAt(random.nextInt(characters.length())));
        }
        return id.toString();
    }

    /**
     * Vérifie si un ID existe déjà dans le fichier CSV.
     * @param id L'ID à vérifier.
     * @return true si l'ID existe, false sinon.
     */
    private boolean idExists(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichierCSV))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] details = ligne.split(",");
                if (details.length >= 1 && details[0].equals(id)) {
                    return true; // L'ID existe déjà
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // L'ID est unique
    }

    public static void main(String[] args) {
        AdminUserManager adminUserManager = new AdminUserManager();

        // Demander les informations de l'administrateur à ajouter
        String nom = JOptionPane.showInputDialog("Entrez le nom de l'administrateur :");
        String prenom = JOptionPane.showInputDialog("Entrez le prénom de l'administrateur :");
        String email = JOptionPane.showInputDialog("Entrez l'email de l'administrateur :");
        String numeroTel = JOptionPane.showInputDialog("Entrez le numéro de téléphone de l'administrateur :");
        String motDePasse = JOptionPane.showInputDialog("Entrez le mot de passe de l'administrateur :");

        // Appeler la méthode pour ajouter l'administrateur
        adminUserManager.ajouterAdmin(nom, prenom, email, numeroTel, motDePasse);
    }
}