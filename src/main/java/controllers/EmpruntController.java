package controllers;

import model.Emprunt;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmpruntController {

    private List<Emprunt> emprunts; // Liste pour stocker les emprunts

    // Constructeur
    public EmpruntController() {
        this.emprunts = new ArrayList<>();
    }

    // Ajouter un emprunt
    public void ajouterEmprunt(int id, int utilisateurId, int livreId, Date dateEmprunt, Date dateRetour) {
        Emprunt emprunt = new Emprunt(id, utilisateurId, livreId, dateEmprunt, dateRetour, false);
        emprunts.add(emprunt);
        System.out.println("Emprunt ajouté : " + emprunt);
    }

    // Marquer un emprunt comme retourné
    public void marquerCommeRetourne(int idEmprunt) {
        for (Emprunt emprunt : emprunts) {
            if (emprunt.getId() == idEmprunt && !emprunt.isEstRendu()) {
                emprunt.setEstRendu(true);
                System.out.println("Emprunt retourné : " + emprunt);
                return;
            }
        }
        System.out.println("Aucun emprunt trouvé avec cet ID ou il est déjà retourné.");
    }

    // Afficher la liste de tous les emprunts
    public void afficherEmprunts() {
        if (emprunts.isEmpty()) {
            System.out.println("Aucun emprunt à afficher.");
        } else {
            for (Emprunt emprunt : emprunts) {
                System.out.println(emprunt);
            }
        }
    }

    // Rechercher un emprunt par utilisateur
    public void rechercherParUtilisateur(int utilisateurId) {
        boolean trouve = false;
        for (Emprunt emprunt : emprunts) {
            if (emprunt.getUtilisateurId() == utilisateurId) {
                System.out.println(emprunt);
                trouve = true;
            }
        }
        if (!trouve) {
            System.out.println("Aucun emprunt trouvé pour cet utilisateur.");
        }
    }

    // Rechercher un emprunt par livre
    public void rechercherParLivre(int livreId) {
        boolean trouve = false;
        for (Emprunt emprunt : emprunts) {
            if (emprunt.getLivreId() == livreId) {
                System.out.println(emprunt);
                trouve = true;
            }
        }
        if (!trouve) {
            System.out.println("Aucun emprunt trouvé pour ce livre.");
        }
    }

    // Exporter tous les emprunts au format CSV
    public void exporterEmpruntsCsv() {
        for (Emprunt emprunt : emprunts) {
            System.out.println(emprunt.toCsvFormat());
        }
    }

}
