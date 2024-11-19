import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import model.User;
import vue.UserView;

public class UserController {
    private UserView userView;
    private final String fichierCSV = "users.csv";

    public UserController(UserView userView) {
        this.userView = userView;

        // Liaison des actions de l’interface avec les méthodes de gestion des utilisateurs
        this.userView.getAjouterButton().addActionListener(e -> ajouterUser());
        this.userView.getModifierButton().addActionListener(e -> modifierUser());
        this.userView.getSupprimerButton().addActionListener(e -> supprimerUser());
    }

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
                afficherUsersDansTable();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void modifierUser() {
        User user = getUserFromView();
        if (user != null) {
            List<String> lignes = new ArrayList<>();
            boolean userTrouve = false;
            try (BufferedReader reader = new BufferedReader(new FileReader(fichierCSV))) {
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    String[] details = ligne.split(",");
                    if (details[2].equals(user.getEmail())) {
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

    private void supprimerUser() {
        String email = userView.getEmailField().getText();
        List<String> lignes = new ArrayList<>();
        boolean userSupprime = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fichierCSV))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] details = ligne.split(",");
                if (details[2].equals(email)) {
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
 // Dans le constructeur du contrôleur
    this.userView.getAjouterButton().addActionListener(e -> ajouterUser());
    this.userView.getModifierButton().addActionListener(e -> modifierUser());
    this.userView.getSupprimerButton().addActionListener(e -> supprimerUser());

    // Ajouter une méthode pour afficher les utilisateurs dans la table
    private void afficherUsersDansTable() {
        List<User> users = lireTousLesUsers(); // Méthode existante
        DefaultTableModel model = userView.getTableModel();
        model.setRowCount(0); // Réinitialise la table

        for (User user : users) {
            model.addRow(new Object[]{user.getNom(), user.getPrenom(), user.getEmail(), user.getRole()});
        }
    }
}
