package model;

import java.time.LocalDate;

public class Emprunt {
    private int id;             // Identifiant unique de l'emprunt
    private int utilisateurId;  // Référence à l'utilisateur (ID)
    private int livreId;        // Référence au livre emprunté (ID)
    private LocalDate dateEmprunt;   // Date d'emprunt
    private LocalDate dateRetour;    // Date de retour prévue
    private boolean estRendu;   // Indicateur si le livre a été retourné ou non

    // Constructeur
    public Emprunt(int id, int utilisateurId, int livreId, LocalDate dateEmprunt, LocalDate dateRetour, boolean estRendu) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.livreId = livreId;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.estRendu = estRendu;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public int getLivreId() {
        return livreId;
    }

    public void setLivreId(int livreId) {
        this.livreId = livreId;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public boolean isEstRendu() {
        return estRendu;
    }

    public void setEstRendu(boolean estRendu) {
        this.estRendu = estRendu;
    }

    // Méthode pour afficher les informations sous forme CSV
    public String toCsvFormat() {
        return String.join(",", 
            String.valueOf(id), 
            String.valueOf(utilisateurId), 
            String.valueOf(livreId), 
            dateEmprunt.toString(), 
            dateRetour.toString(), 
            String.valueOf(estRendu)
        );
    }

    @Override
    public String toString() {
        return "Emprunt{id=" + id + ", utilisateurId=" + utilisateurId + ", livreId=" + livreId +
               ", dateEmprunt=" + dateEmprunt + ", dateRetour=" + dateRetour + ", estRendu=" + estRendu + '}';
    }
}
