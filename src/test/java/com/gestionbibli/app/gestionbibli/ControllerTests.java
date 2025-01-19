package com.gestionbibli.app.gestionbibli;

import controllers.EmpruntController;
import controllers.LivreController;
import controllers.UserController;
import model.Emprunt;
import model.Livre;
import model.User;
import model.EmpruntDAO;
import model.LivreDAO;
import model.Role;
import model.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vue.EmpruntView;
import vue.LivreView;
import vue.UserView;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTests {

    private EmpruntController empruntController;
    private LivreController livreController;
    private UserController userController;

    @BeforeEach
    public void setUp() {
        // Chargement des fichiers CSV avec des chemins relatifs
        String empruntFilePath = loadResourcePath("data/emprunt.csv");
        String booksFilePath = loadResourcePath("data/books.csv");
        String usersFilePath = loadResourcePath("data/users.csv");

        new EmpruntDAO(empruntFilePath);
        new LivreDAO(booksFilePath);
        UserDAO userDAO = new UserDAO(usersFilePath);

        EmpruntView empruntView = new EmpruntView();
        LivreView livreView = new LivreView();
        UserView userView = new UserView();

        // Initialize controllers
        empruntController = new EmpruntController(empruntView, empruntFilePath, booksFilePath, usersFilePath);
        livreController = new LivreController(livreView, booksFilePath, empruntController);
        userController = new UserController(userView, userDAO);
    }

    @Test
    public void testAjouterEmprunt() {
        new Emprunt(1, 1, "user1", LocalDate.now(), LocalDate.now().plusDays(7), LocalDate.now().plusDays(7),false, 0);
        Emprunt emprunt1 = new Emprunt(2, 1, "user1", LocalDate.now(), LocalDate.now().plusDays(7), LocalDate.now().plusDays(7),false, 5);

        // Vérifier que l'ajout de l'emprunt ne lance pas d'exception
        assertDoesNotThrow(() -> empruntController.ajouterEmprunt(emprunt1));
        List<Emprunt> emprunts = empruntController.getEmprunts();
        assertTrue(emprunts.contains(emprunt1), "L'emprunt devrait être ajouté à la liste.");
    }

    @Test
    public void testAjouterLivre() {
        new Livre(1, "Titre Test", "Auteur Test", "Genre Test", 2023, "", "123-456", "Description Test", "Editeur Test", 10);

        // Vérifier que l'ajout du livre ne lance pas d'exception
        assertDoesNotThrow(() -> livreController.ajouterLivre());
        List<Livre> livres = livreController.getAllLivres();
        assertTrue(livres.stream().anyMatch(l -> l.getTitre().equals("Titre Test")), "Le livre devrait être ajouté à la liste.");
    }

    @Test
    public void testAjouterUtilisateur() {
        new User("129393", "Nom Test", "Prenom Test", "email@test.com", "123456789", "password", Role.MEMBRE, true);

        // Vérifier que l'ajout de l'utilisateur ne lance pas d'exception
        assertDoesNotThrow(() -> userController.ajouterUtilisateur());
        
        // Vérifier le nombre d'utilisateurs actifs après l'ajout
        int nombreUtilisateursActifs = userController.getNombreUtilisateursActifs();
        assertEquals(1, nombreUtilisateursActifs, "Il devrait y avoir 1 utilisateur actif.");
    }

    // Méthode pour charger les ressources
    private String loadResourcePath(String resourceName) {
        try {
            // Utilisation de getResource pour obtenir le chemin de la ressource
            return getClass().getClassLoader().getResource(resourceName).toURI().getPath();
        } catch (Exception e) {
            // Afficher un message d'erreur si la ressource n'est pas trouvée
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement de la ressource : " + resourceName, "Erreur", JOptionPane.ERROR_MESSAGE);
            return null; // Retourne null si la ressource n'est pas trouvée
        }
        
    }
}