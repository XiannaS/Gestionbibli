package com.gestionbibli.app.gestionbibli;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Role;
import model.User;

public class App {
    private String fichierCSV;

    public App(String fichierCSV) {
        this.fichierCSV = fichierCSV;
    }

    public List<User> lireTousLesUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fichierCSV))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                System.out.println("Ligne lue : " + ligne); // Ajoutez cette ligne pour déboguer
                String[] donnees = ligne.split(",");
                if (donnees.length == 6) {
                    String id = donnees[0];
                    String nom = donnees[1];
                    String prenom = donnees[2];
                    String email = donnees[3];
                    String motDePasse = donnees[4];
                    Role role = Role.valueOf(donnees[5]);
                    User user = new User(id,nom, prenom, email, motDePasse, motDePasse, role, false);
                    user.setId(id);
                    users.add(user);
                } else {
                    System.out.println("Format de ligne invalide : " + ligne); // Ajoutez cette ligne pour déboguer
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void afficherUsersAvecMdpHache() {
        List<User> users = lireTousLesUsers();
        for (User  user : users) {
            System.out.println("ID: " + user.getId());
            System.out.println("Nom: " + user.getNom());
            System.out.println("Prénom: " + user.getPrenom());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Mot de passe haché: " + user.getMotDePasse());
            System.out.println("Rôle: " + user.getRole());
            System.out.println("-----------------------------------");
        }
    }

    public static void main(String[] args) {
        System.out.println("Démarrage de l'application..."); // Ajoutez cette ligne
        App userManager = new App("src/main/resources/ressources/users.csv");
        userManager.afficherUsersAvecMdpHache();
    }
}