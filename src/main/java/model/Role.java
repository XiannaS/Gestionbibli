package model;
public enum Role {
    MEMBRE("Membre"),
    BIBLIOTHECAIRE("Bibliothécaire"),
    ADMINISTRATEUR("Administrateur"); // Assurez-vous que cette constante existe

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Role fromLabel(String label) {
        System.out.println("Recherche du rôle pour l'étiquette : " + label); // Ajoutez cette ligne
        for (Role role : Role.values()) {
            if (role.getLabel().equalsIgnoreCase(label.trim())) {
            	System.out.println("  rôle trouve l'étiquette : " + label); 
                return role;
            }
        }
        throw new IllegalArgumentException("Aucun rôle trouvé pour l'étiquette : " + label);
    }
    
}