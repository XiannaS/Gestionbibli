package controllers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.User;
import vue.UserView;

public class UserController {
    private UserView userView;
    private final String fichierCSV = "users.csv";  // Nom du fichier CSV contenant les utilisateurs

    public UserController(UserView userView) {
        this.userView = userView;
        // Liaison des actions de l’interface avec les méthodes de gestion des utilisateurs
        this.userView.getAjouterButton().addActionListener(e -> ajouterUser());
        this.userView.getModifierButton().addActionListener(e -> modifierUser());
        this.userView.getSupprimerButton().addActionListener(e -> supprimerUser());
    }


    // Méthode pour ajouter un utilisateur
    private void ajouterUser() {
        User user = getUserFromView();
        if (user != null) {
            if (!user.getRole().matches("Membre|Bibliothécaire|Administrateur")) {
                JOptionPane.showMessageDialog(null, "Rôle invalide.");
                return;
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierCSV, true))) {
                writer.write(user.getNom() + "," + user.getPrenom() + "," + user.getEmail() + "," +
                             user.getMotDePasse() + "," + user.getRole());
                writer.newLine();
                afficherUsersDansTable();  // Met à jour la table avec le nouvel utilisateur
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Méthode pour modifier un utilisateur
    private void modifierUser() {
        User user = getUserFromView();
        if (user != null) {
            List<String> lignes = new ArrayList<>();
            boolean userTrouve = false;
            try (BufferedReader reader = new BufferedReader(new FileReader(fichierCSV))) {
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    String[] details = ligne.split(",");
                    if (details[2].equals(user.getEmail())) {  // Trouve l'utilisateur par son email
                        lignes.add(user.getNom() + "," + user.getPrenom() + "," + user.getEmail() + "," +
                                   user.getMotDePasse() + "," + user.getRole());
                        userTrouve = true;
                    } else {
                        lignes.add(ligne);
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
    private void supprimerUser() {
        String email = userView.getEmailField().getText();
        List<String> lignes = new ArrayList<>();
        boolean userSupprime = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fichierCSV))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] details = ligne.split(",");
                if (details[2].equals(email)) {  // Trouve l'utilisateur par son email
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

    // Méthode pour récupérer les informations d'un utilisateur depuis la vue
    private User getUserFromView() {
        String nom = userView.getNomField().getText();
        String prenom = userView.getPrenomField().getText();
        String email = userView.getEmailField().getText();
        String motDePasse = new String(userView.getMotDePasseField().getPassword());
        String role = (String) userView.getRoleComboBox().getSelectedItem();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tous les champs doivent être remplis.");
            return null;
        }
        return new User(nom, prenom, email, motDePasse, role);
    }

    // Méthode pour afficher tous les utilisateurs dans la table de la vue
    private void afficherUsersDansTable() {
        List<User> users = lireTousLesUsers();  // Récupère tous les utilisateurs
        DefaultTableModel model = userView.getTableModel();
        model.setRowCount(0);  // Réinitialise la table

        // Ajoute chaque utilisateur dans la table
        for (User user : users) {
            model.addRow(new Object[]{user.getNom(), user.getPrenom(), user.getEmail(), user.getRole()});
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
                    User user = new User(details[0], details[1], details[2], details[3], details[4]);
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
