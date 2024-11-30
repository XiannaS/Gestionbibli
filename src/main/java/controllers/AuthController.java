package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

import model.Role;
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
        //this.connexionView.getInscriptionButton().addActionListener(e -> afficherInscriptionView());

        // Action pour le bouton d'inscription dans la vue d'inscription
        this.inscriptionView.getInscriptionButton().addActionListener(e -> inscrireUser ());
    }

    private void seConnecter() {
        String email = connexionView.getEmail();
        String motDePasse = connexionView.getMotDePasse();
        List<User> users = lireTousLesUsers();

        for (User  user : users) {
            if (user.getEmail().equals(email)) {
                // Vérifiez le mot de passe en utilisant BCrypt
                if (BCrypt.checkpw(motDePasse, user.getMotDePasse())) {
                    this.currentUser  = user; // Stocker l'utilisateur connecté
                    stylishWindow = new StylishWindow(currentUser ); // Créer StylishWindow avec l'utilisateur connecté
                    stylishWindow.setVisible(true); // Afficher StylishWindow

                    connexionView.dispose(); // Fermer la fenêtre de connexion
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "Mot de passe incorrect.");
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Email non trouvé.");
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
        Role role = Role.MEMBRE;  // Le rôle est maintenant toujours "Membre"

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

        // Hacher le mot de passe avant de créer l'utilisateur
        String hashedPassword = hashPassword(motDePasse);
        
        // Générer un identifiant unique pour l'utilisateur
        String id = UUID.randomUUID().toString(); // Génération d'un UUID

        // Création de l'utilisateur avec le mot de passe haché
        User newUser  = new User(id, nom, prenom, email, "", hashedPassword, role, true); // Assurez-vous que le constructeur de User accepte ces paramètres

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierCSV, true))) {
            writer.write(newUser .getId() + "," + newUser .getNom() + "," + newUser .getPrenom() + "," +
                         newUser .getEmail() + "," + newUser .getNumeroTel() + "," + // Assurez-vous que ce champ est géré
                         newUser .getMotDePasse() + "," + newUser .getRole() + "," + newUser .isStatut());
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
	private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

	private List<User> lireTousLesUsers() {
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
}

