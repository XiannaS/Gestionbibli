package controllers;

import java.io.*;
import javax.swing.*;
import model.Role;
import model.User;

public class AdminUserManager {

    private final String fichierCSV = "src/main/resources/ressources/users.csv"; // Chemin vers le fichier CSV

    /**
     * Ajoute un utilisateur avec le rôle Administrateur dans le fichier users.csv.
     * @param nom Le nom de l'utilisateur.
     * @param prenom Le prénom de l'utilisateur.
     * @param email L'email de l'utilisateur.
     * @param motDePasse Le mot de passe de l'utilisateur.
     * @return true si l'utilisateur est ajouté avec succès, false sinon.
     */
    public boolean ajouterAdmin(String nom, String prenom, String email, String motDePasse) {
        if (nom == null || prenom == null || email == null || motDePasse == null ||
            nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tous les champs sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Vérifiez si l'utilisateur existe déjà dans le fichier CSV
        if (utilisateurExiste(email)) {
            JOptionPane.showMessageDialog(null, "Un utilisateur avec cet email existe déjà.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Création de l'utilisateur Administrateur
        User admin = new User(nom, prenom, email, hashMotDePasse(motDePasse), Role.ADMINISTRATEUR);

        // Ajout de l'utilisateur dans le fichier CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierCSV, true))) {
            writer.write(admin.getNom() + "," +
                         admin.getPrenom() + "," +
                         admin.getEmail() + "," +
                         admin.getMotDePasse() + "," +
                         admin.getRole());
            writer.newLine();
            JOptionPane.showMessageDialog(null, "Administrateur ajouté avec succès !");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de l'administrateur.", "Erreur", JOptionPane.ERROR_MESSAGE);
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
                if (details.length >= 3 && details[2].equalsIgnoreCase(email)) {
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
        // Utilisez une bibliothèque comme BCrypt pour hacher le mot de passe
        return org.mindrot.jbcrypt.BCrypt.hashpw(motDePasse, org.mindrot.jbcrypt.BCrypt.gensalt());
    }
}
