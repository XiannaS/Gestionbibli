package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.User;
import style.StylishWindow;
import vue.ConnexionView;
import vue.InscriptionView;

public class AuthController {
    private ConnexionView connexionView;
    private InscriptionView inscriptionView;
    private StylishWindow stylishWindow;
    private final String fichierCSV = "src/main/resources/ressources/users.csv"; // Nom du fichier CSV contenant les utilisateurs
    private User currentUser ;
    
    public AuthController(ConnexionView connexionView, InscriptionView inscriptionView) {
        this.connexionView = connexionView;
        this.inscriptionView = inscriptionView;
        this.stylishWindow = stylishWindow; // Initialisez ici

        // Action pour le bouton de connexion
        this.connexionView.getConnexionButton().addActionListener(e -> seConnecter());

        // Action pour le bouton d'inscription
        this.connexionView.getInscriptionButton().addActionListener(e -> afficherInscriptionView());

        // Action pour le bouton d'inscription dans la vue d'inscription
        this.inscriptionView.getInscriptionButton().addActionListener(e -> inscrireUser ());
    }

    private void seConnecter() {
        String email = connexionView.getEmail();
        String motDePasse = connexionView.getMotDePasse();
        List<User> users = lireTousLesUsers();

        for (User  user : users) {
            if (user.getEmail().equals(email) && user.getMotDePasse().equals(motDePasse)) {
                this.currentUser  = user; // Stocker l'utilisateur connecté
                stylishWindow = new StylishWindow(currentUser ); // Créer StylishWindow avec l'utilisateur connecté
                stylishWindow.setVisible(true); // Afficher StylishWindow

                connexionView.dispose(); // Fermer la fenêtre de connexion
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Email ou mot de passe incorrect.");
    }

    private void afficherInscriptionView() {
        inscriptionView.setVisible(true);
        connexionView.dispose(); // Fermer la fenêtre de connexion
    }

    private void inscrireUser () {
        String nom = inscriptionView.getNom();
        String prenom = inscriptionView.getPrenom();
        String email = inscriptionView.getEmail();
        String motDePasse = inscriptionView.getMotDePasse();
        String role = "Membre";  // Le rôle est maintenant toujours "Membre"

        // Vérification des champs vides
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tous les champs doivent être remplis.");
            return;
        }

        // Vérification de l'unicité de l'email
        List<User> users = lireTousLesUsers();
        for (User  user : users) {
            if (user.getEmail().equals(email)) {
                JOptionPane.showMessageDialog(null, "L'email est déjà utilisé.");
                return;
            }
        }

        // Création de l'utilisateur
        User newUser  = new User(nom, prenom, email, motDePasse, role);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierCSV, true))) {
            writer.write(newUser .getNom() + "," + newUser .getPrenom() + "," + newUser .getEmail() + "," +
                         newUser .getMotDePasse() + "," + newUser .getRole());
            writer.newLine();
            System.out.println("Utilisateur inscrit : " + newUser .getNom() + " " + newUser .getPrenom());
            JOptionPane.showMessageDialog(null, "Inscription réussie !");
            inscriptionView.dispose(); // Fermer la fenêtre d'inscription
            connexionView.setVisible(true); // Retourner à la vue de connexion
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'inscription : " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private List<User> lireTousLesUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fichierCSV))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] details = ligne.split(",");
                if (details.length == 5) {
                    User user = new User(details[0], details[1], details[2], details[3], details[4]);
                    users.add(user);
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

}
