package controllers;

import java.util.UUID;
import model.Livre;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LivreController {
	private static final String CSV_FILE = "src/main/resources/ressources/books.csv";    // Méthode pour lire les livres depuis le fichier CSV

	public List<Livre> lireLivres() {
	    try (Stream<String> lines = Files.lines(Paths.get(CSV_FILE))) {
	        return lines
	            .map(line -> line.split(","))
	            .filter(attributes -> attributes.length >= 7) // Vérifie que la ligne a au moins 7 éléments
	            .map(attributes -> {
	                try {
	                    return new Livre(
	                        attributes[0],
	                        attributes[1],
	                        attributes[2],
	                        attributes[3],
	                        Integer.parseInt(attributes[4]),
	                        "Yes".equalsIgnoreCase(attributes[5]), // Vérifiez si la valeur est "Yes"
	                        attributes[6]
	                    );
	                } catch (NumberFormatException e) {
	                    System.err.println("Erreur de format pour la ligne : " + String.join(",", attributes));
	                    return null; // Retourner null pour filtrer plus tard
	                }
	            })
	            .filter(livre -> livre != null) // Filtrer les livres null
	            .collect(Collectors.toList());
	    } catch (IOException e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}

 // Dans la méthode ajouterLivre
 public void ajouterLivre(Livre livre) {
     // Générer un ID unique avec UUID
     String id = UUID.randomUUID().toString();
     livre.setId(id);
     System.out.println("Ajout du livre avec ID : " + id);

     try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
         bw.write(livre.toCsvFormat());
         bw.newLine();
     } catch (IOException e) {
         e.printStackTrace();
     }
 }
    

    // Méthode pour modifier un livre dans le fichier CSV
    public void modifierLivre(Livre livreModifie) {
        List<Livre> livres = lireLivres();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            livres.stream()
                .map(livre -> livre.getId().equals(livreModifie.getId()) ? livreModifie : livre)
                .forEach(livre -> {
                    try {
                        bw.write(livre.toCsvFormat());
                        bw.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour supprimer un livre du fichier CSV
    public void supprimerLivre(String id) {
        List<Livre> livres = lireLivres();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            livres.stream()
                .filter(livre -> !livre.getId().equals(id))
                .forEach(livre -> {
                    try {
                        bw.write(livre.toCsvFormat());
                        bw.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<Livre> filtrerLivres(String searchQuery, boolean onlyAvailable, boolean onlyBorrowed, String selectedGenre) {
        List<Livre> allBooks = lireLivres();
        System.out.println("Total livres chargés: " + allBooks.size());

        return allBooks.stream()
            .filter(livre -> {
                // Vérifier si le livre correspond à la recherche par titre ou auteur
                boolean matchesSearch = livre.getTitre().toLowerCase().contains(searchQuery) || 
                                        livre.getAuteur().toLowerCase().contains(searchQuery);
                System.out.println("Livre: " + livre.getTitre() + ", Correspond à la recherche: " + matchesSearch);

                // Vérifier la disponibilité ou l'emprunt
                boolean matchesAvailability = true; // Par défaut, on considère que ça correspond
                if (onlyAvailable && !onlyBorrowed) {
                    matchesAvailability = livre.isDisponible(); // Seulement les livres disponibles
                } else if (!onlyAvailable && onlyBorrowed) {
                    matchesAvailability = !livre.isDisponible(); // Seulement les livres empruntés
                }

                // Vérifier le genre
                boolean matchesGenre = selectedGenre.equals("Tous") || livre.getGenre().equals(selectedGenre);
                System.out.println("Livre: " + livre.getTitre() + ", Genre correspond: " + matchesGenre);

                return matchesSearch && matchesAvailability && matchesGenre; // Retourner true si toutes les conditions sont remplies
            })
            .peek(livre -> System.out.println("Livre filtré: " + livre.getTitre())) // Pour voir les livres filtrés
            .collect(Collectors.toList());
    }
}