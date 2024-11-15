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

    /**
     * Enregistre un nouvel emprunt dans la liste.
     * @param id L'identifiant de l'emprunt.
     * @param utilisateurId L'ID de l'utilisateur qui emprunte le livre.
     * @param livreId L'ID du livre emprunté.
     * @param dateEmprunt La date d'emprunt du livre.
     * @param dateRetour La date de retour prévue pour l'emprunt.
     */
    public void ajouterEmprunt(int id, int utilisateurId, int livreId, Date dateEmprunt, Date dateRetour) {
        Emprunt emprunt = new Emprunt(id, utilisateurId, livreId, dateEmprunt, dateRetour, false);
        emprunts.add(emprunt);
        System.out.println("Emprunt ajouté : " + emprunt);
    }

    /**
     * Rallonge la date de retour d'un emprunt existant.
     * @param idEmprunt L'identifiant de l'emprunt à modifier.
     * @param nouvelleDateRetour La nouvelle date de retour prévue.
     */
    public void modifierDateRetour(int idEmprunt, Date nouvelleDateRetour) {
        for (Emprunt emprunt : emprunts) {
            if (emprunt.getId() == idEmprunt && !emprunt.isEstRendu()) {
                emprunt.setDateRetour(nouvelleDateRetour);
                System.out.println("Date de retour modifiée pour l'emprunt : " + emprunt);
                return;
            }
        }
        System.out.println("Aucun emprunt trouvé avec cet ID ou il est déjà retourné.");
    }

    /**
     * Marque un emprunt comme retourné et permet ensuite sa suppression.
     * @param idEmprunt L'identifiant de l'emprunt à marquer comme retourné.
     */
    public void marquerCommeRetourne(int idEmprunt) {
        for (Emprunt emprunt : emprunts) {
            if (emprunt.getId() == idEmprunt && !emprunt.isEstRendu()) {
                emprunt.setEstRendu(true);
                System.out.println("Emprunt marqué comme retourné : " + emprunt);
                return;
            }
        }
        System.out.println("Aucun emprunt trouvé avec cet ID ou il est déjà retourné.");
    }

    /**
     * Supprime un emprunt de la liste si celui-ci a été marqué comme retourné.
     * @param idEmprunt L'identifiant de l'emprunt à supprimer.
     */
    public void supprimerEmpruntRetourne(int idEmprunt) {
        for (int i = 0; i < emprunts.size(); i++) {
            Emprunt emprunt = emprunts.get(i);
            if (emprunt.getId() == idEmprunt && emprunt.isEstRendu()) {
                emprunts.remove(i);
                System.out.println("Emprunt supprimé : " + emprunt);
                return;
            }
        }
        System.out.println("Aucun emprunt trouvé avec cet ID ou il n'est pas encore retourné.");
    }

    /**
     * Affiche l'historique de tous les emprunts, incluant ceux qui ont été retournés.
     */
    public void afficherHistoriqueEmprunts() {
        if (emprunts.isEmpty()) {
            System.out.println("Aucun emprunt à afficher.");
        } else {
            System.out.println("Historique des emprunts :");
            for (Emprunt emprunt : emprunts) {
                System.out.println(emprunt);
            }
        }
    }
}
