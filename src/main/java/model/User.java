package model;

public class User {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String numeroTel; // Nouveau champ pour le numéro de téléphone
    private boolean actif; // Nouveau champ pour indiquer si l'utilisateur est actif ou inactif
    private String motDePasse;
    private Role role;
    private boolean statut; // Nouveau champ pour le statut

    // Constructeur principal
    public User(String nom, String prenom, String email, String numeroTel, Role role, boolean actif, boolean statut) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numeroTel = numeroTel;
        this.role = role;
        this.actif = actif;
        this.statut = statut; // Initialiser le statut
    }

    // Ancien constructeur (compatibilité pour les usages existants)
    public User(String nom, String prenom, String email, String motDePasse, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
        this.actif = true; // Par défaut, un utilisateur est actif
        this.statut = true; // Par défaut, le statut est actif
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStatut() {
        return statut; // Retourner le statut
    }

    public void setStatut(boolean statut) {
        this.statut = statut; // Setter pour le statut
    }

    // Méthode pour afficher une représentation lisible de l'utilisateur
    @Override
    public String toString() {
        return "User {" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", numeroTel='" + numeroTel + '\'' +
                ", role=" + role +
                ", actif=" + actif +
                ", statut=" + statut + // Inclure le statut dans la représentation
                '}';
    }
}