package test; // test imane


import controllers.EmpruntController;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        // Création de l'instance du contrôleur des emprunts
        EmpruntController empruntController = new EmpruntController();
        
        // Format de date pour créer des dates d'emprunt et de retour
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Ajouter des emprunts
            Date dateEmprunt1 = dateFormat.parse("2024-11-01");
            Date dateRetour1 = dateFormat.parse("2024-11-15");
            empruntController.ajouterEmprunt(1, 101, 1001, dateEmprunt1, dateRetour1);

            Date dateEmprunt2 = dateFormat.parse("2024-11-05");
            Date dateRetour2 = dateFormat.parse("2024-11-20");
            empruntController.ajouterEmprunt(2, 102, 1002, dateEmprunt2, dateRetour2);

            // Afficher l'historique des emprunts
            empruntController.afficherHistoriqueEmprunts();

            // Modifier la date de retour de l'emprunt avec l'ID 1
            Date nouvelleDateRetour = dateFormat.parse("2024-11-25");
            empruntController.modifierDateRetour(1, nouvelleDateRetour);

            // Marquer l'emprunt avec l'ID 1 comme retourné
            empruntController.marquerCommeRetourne(1);

            // Supprimer l'emprunt avec l'ID 1 (il doit être retourné)
            empruntController.supprimerEmpruntRetourne(1);

            // Afficher à nouveau l'historique des emprunts après les modifications
            empruntController.afficherHistoriqueEmprunts();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
