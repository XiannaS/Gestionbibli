package model;

public enum Role {
    MEMBRE("Membre"),
    BIBLIOTHECAIRE("Bibliothécaire"),
    ADMINISTRATEUR("Administrateur");
	

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    // Vérification si un rôle est valide
    public static boolean isValidRole(String role) {
        for (Role r : Role.values()) {
            if (r.getLabel().equalsIgnoreCase(role)) {
                return true;
            }
        }
        return false;
    }
}
