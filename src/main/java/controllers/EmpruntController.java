package controllers;

import model.Emprunt;
import model.Livre;
import model.Role;
import model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntController {

    private List<Emprunt> emprunts; // Liste pour stocker les emprunts
    private List<Livre> livres; // Liste pour stocker les livres
    private List<User> users; // Liste pour stocker les utilisateurs
    private static final String FILE_PATH = "src/main/java/database/emprunt.csv"; // Chemin relatif vers le fichier CSV

    // Constructeur
    public EmpruntController() {
        this.emprunts = new ArrayList<>();
        this.livres = new ArrayList<>(); // Initialiser la liste des livres
        this.users = new ArrayList<>(); // Initialiser la liste des utilisateurs
        loadEmpruntsFromCSV(); // Charger les emprunts depuis le fichier CSV au démarrage
        loadLivresFromCSV(); // Charger les livres depuis le fichier CSV
        loadUsersFromCSV(); // Charger les utilisateurs depuis le fichier CSV
    }
    /**
     * Enregistre un nouvel emprunt dans la liste.
     * @param utilisateurId L'ID de l'utilisateur qui emprunte le livre.
     * @param livreId L'ID du livre emprunté.
     * @param dateEmprunt La date d'emprunt du livre.
     * @param dateRetour La date de retour prévue pour l'emprunt.
     */
    // Méthodes pour récupérer les utilisateurs et les livres
    public List<User> getUsers() {
        return users;
    }

    public List<Livre> getLivres() {
        return livres;
    }
    // Ajoutez ici les méthodes pour charger les livres et les utilisateurs depuis leurs fichiers CSV respectifs
private void loadLivresFromCSV() {
    try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/ressources/books.csv"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 6) { // Ajustez en fonction de votre structure de données
                String id = data[0];
                String titre = data[1];
                String auteur = data[2];
                String genre = data[3];
                int anneePublication = Integer.parseInt(data[4]);
                boolean disponible = Boolean.parseBoolean(data[5]);
                Livre livre = new Livre(id, titre, auteur, genre, anneePublication, disponible, null); // Assurez-vous que le constructeur existe
                livres.add(livre);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Erreur lors du chargement des livres.");
    }
}

private void loadUsersFromCSV() {
    try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/ressources/users.csv"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 8) { // Ajustez en fonction de votre structure de données
                String id = data[0];
                String nom = data[1];
                String prenom = data[2];
                String email = data[3];
                String numeroTel = data[4];
                String motDePasse = data[5];
                Role role = Role.valueOf(data[6]); // Assurez-vous que le rôle est bien défini
                boolean statut = Boolean.parseBoolean(data[7]);
                User user = new User(id, nom, prenom, email, numeroTel, motDePasse, role, statut);
                users.add(user);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Erreur lors du chargement des utilisateurs.");
    }
}
    public void ajouterEmprunt(int utilisateurId, String livreId, LocalDate dateEmprunt, LocalDate dateRetour) {
        // Validation des données
        if (!utilisateurExists(utilisateurId)) {
            System.out.println("L'utilisateur avec l'ID " + utilisateurId + " n'existe pas.");
            return;
        }
        if (!livreExists(livreId)) {
            System.out.println("Le livre avec l'ID " + livreId + " n'existe pas.");
            return;
        }
        if (!isLivreDisponible(livreId)) {
            System.out.println("Le livre avec l'ID " + livreId + " n'est pas disponible.");
            return;
        }

        int id = emprunts.size() + 1; // Générer un ID automatique
        Emprunt emprunt = new Emprunt(id, utilisateurId, livreId, dateEmprunt, dateRetour, false);
        emprunts.add(emprunt);
        saveEmpruntsToCSV(); // Sauvegarder les emprunts dans le fichier CSV
        System.out.println("Emprunt ajouté : " + emprunt);
    }

    /**
     * Supprime un emprunt de la liste si celui-ci a été marqué comme retourné.
     * @param idEmprunt L'identifiant de l'emprunt à supprimer.
     */
    public void supprimerEmprunt(int idEmprunt) {
        for (int i = 0; i < emprunts.size(); i++) {
            Emprunt emprunt = emprunts.get(i);
            if (emprunt.getId() == idEmprunt && emprunt.isEstRendu()) {
                emprunts.remove(i);
                saveEmpruntsToCSV(); // Sauvegarder les emprunts dans le fichier CSV
                System.out.println("Emprunt supprimé : " + emprunt);
                return;
            }
        }
        System.out.println("Aucun emprunt trouvé avec cet ID ou il n'est pas encore retourné.");
    }

    /**
     * Modifie la date de retour d'un emprunt existant.
     * @param idEmprunt L'identifiant de l'emprunt à modifier.
     * @param nouvelleDateRetour La nouvelle date de retour prévue.
     */
    public void modifierDateRetour(int idEmprunt, LocalDate nouvelleDateRetour) {
        for (Emprunt emprunt : emprunts) {
            if (emprunt.getId() == idEmprunt && !emprunt.isEstRendu()) {
                emprunt.setDateRetour(nouvelleDateRetour);
                saveEmpruntsToCSV(); // Sauvegarder les emprunts dans le fichier CSV
                System.out.println("Date de retour modifiée pour l'emprunt : " + emprunt);
                return;
            }
        }
        System.out.println("Aucun emprunt trouvé avec cet ID ou il est déjà retourné.");
    }

    /**
     * Affiche l'historique de tous les emprunts, incluant ceux qui ont été retournés.
     * @return La liste des emprunts.
     */
    public List<Emprunt> afficherHistoriqueEmprunts() {
        return this.emprunts;
    }

    /**
     * Charge les emprunts depuis le fichier CSV.
     */
    private void loadEmpruntsFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    int id = Integer.parseInt(data[0]);
                    int utilisateurId = Integer.parseInt(data[1]);
                    String livreId = data[2];
                    LocalDate dateEmprunt = LocalDate.parse(data[3]);
                    LocalDate dateRetour = LocalDate.parse(data[4]);
                    boolean estRendu = Boolean.parseBoolean(data[5]);
                    Emprunt emprunt = new Emprunt(id, utilisateurId, livreId, dateEmprunt, dateRetour, estRendu);
                    emprunts.add(emprunt);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement des emprunts.");
        }
    }

    /**
     * Sauvegarde les emprunts dans le fichier CSV.
     */
    private void saveEmpruntsToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Emprunt emprunt : emprunts) {
                bw.write(emprunt.getId() + "," + emprunt.getUtilisateurId() + "," + emprunt.getLivreId() + "," +
                        emprunt.getDateEmprunt() + "," + emprunt.getDateRetour() + "," + emprunt.isEstRendu());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la sauvegarde des emprunts.");
        }
    }

    // Méthodes de validation des données
    private boolean utilisateurExists(int utilisateurId) {
        // Vérifier si l'utilisateur existe dans la base de données
        return true; // À remplacer par la logique de vérification de l'existence de l'utilisateur
    }

    private boolean livreExists(String livreId) {
        // Vérifier si le livre existe dans la liste des livres
        for (Livre livre : livres) {
            if (livre.getId().equals(livreId)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLivreDisponible(String livreId) {
        // Vérifier si le livre est disponible
        for (Livre livre : livres) {
            if (livre.getId().equals(livreId)) {
                return livre.isDisponible();
            }
        }
        return false; // Livre non trouvé ou non disponible
    }
}