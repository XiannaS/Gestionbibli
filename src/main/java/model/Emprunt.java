package model;

import java.time.LocalDate;

public class Emprunt {
    private int id;
    private int utilisateurId;
    private String livreId;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;
    private boolean estRendu;

    // Constructeur
    public Emprunt(int id, int utilisateurId, String livreId, LocalDate dateEmprunt, LocalDate dateRetour, boolean estRendu) {
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

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public String getLivreId() {
        return livreId;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public boolean isEstRendu() {
        return estRendu;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public void setEstRendu(boolean estRendu) {
        this.estRendu = estRendu;
    }

    @Override
    public String toString() {
        return "Emprunt{" +
                "id=" + id +
                ", utilisateurId=" + utilisateurId +
                ", livreId='" + livreId + '\'' +
                ", dateEmprunt=" + dateEmprunt +
                ", dateRetour=" + dateRetour +
                ", estRendu=" + estRendu +
                '}';
    }
}