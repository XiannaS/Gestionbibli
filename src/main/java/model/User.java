package model;

public class User {
	    private String nom;
	    private String prenom;
	    private String email;
	    private String motDePasse;
	    private Role role;
//Constructor
	    public User(String nom, String prenom, String email, String motDePasse, Role role) {
	        this.nom = nom;
	        this.prenom = prenom;
	        this.email = email;
	        this.setMotDePasse(motDePasse);
	        this.role = role;
	    }

	    // Getters and Setters
	    public String getNom() { return nom; }
	    public void setNom(String nom) { this.nom = nom; }

	    public String getPrenom() { return prenom; }
	    public void setPrenom(String prenom) { this.prenom = prenom; }

	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }

	 
	    public Role getRole() { return role; }
	    public void setRole(Role role) { this.role = role; }

		public String getMotDePasse() {
			return motDePasse;
		}

		public void setMotDePasse(String motDePasse) {
			this.motDePasse = motDePasse;
		}

		 
}