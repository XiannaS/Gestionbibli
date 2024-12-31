package controllers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import vue.DashboardView;
import model.Emprunt;
import model.Livre;
import model.User;

public class DashboardController {
    private DashboardView dashboardView;
    private EmpruntController empruntController;
    private LivreController livreController;
    private UserController userController;

    public DashboardController(DashboardView dashboardView, EmpruntController empruntController, LivreController livreController, UserController userController) {
        this.dashboardView = dashboardView;
        this.empruntController = empruntController;
        this.livreController = livreController;
        this.userController = userController;

        // Ajouter des écouteurs pour les boutons de rafraîchissement et les filtres
        dashboardView.getRefreshButton().addActionListener(e -> refreshDashboard());
        dashboardView.getFilterComboBox().addActionListener(e -> filterDashboardData());
        dashboardView.getGenreComboBox().addActionListener(e -> filterDashboardData());
        dashboardView.getPeriodComboBox().addActionListener(e -> filterDashboardData());
        dashboardView.getUserComboBox().addActionListener(e -> filterDashboardData());

        // Initialiser le tableau de bord
        refreshDashboard();
    } // <-- Assurez-vous qu'il y a bien une accolade fermante à la fin


    // Rafraîchir les données du tableau de bord
 
    

    // Rafraîchir les données du tableau de bord
    private void refreshDashboard() {
        // Récupérer les statistiques
        String totalBooks = String.valueOf(livreController.getAllLivres().size());
        String activeUsers = String.valueOf(userController.getNombreUtilisateursActifs());
        String totalLoans = String.valueOf(empruntController.getEmprunts().size());

        // Mettre à jour la vue avec les statistiques
        dashboardView.updateStats(totalBooks, activeUsers, totalLoans);

        // Appliquer les filtres
        filterDashboardData();

        // Mettre à jour les graphiques
        updateCharts(empruntController.getEmprunts()); // Met à jour le graphique à barres
        updateLineChart(empruntController.getEmprunts()); // Met à jour le graphique linéaire
        updateActiveUsersChart(); // Met à jour le camembert des utilisateurs actifs
    }
    
    	private void filterDashboardData() {
    	    String selectedFilter = (String) dashboardView.getFilterComboBox().getSelectedItem();
    	    String selectedGenre = (String) dashboardView.getGenreComboBox().getSelectedItem();
    	    String selectedPeriod = (String) dashboardView.getPeriodComboBox().getSelectedItem();
    	    String selectedUser = (String) dashboardView.getUserComboBox().getSelectedItem();

    	    List<Emprunt> filteredEmprunts = empruntController.getEmprunts();

    	    // Filtrer par période
    	    if (!selectedPeriod.equals("Tout le temps")) {
    	        filteredEmprunts = filteredEmprunts.stream()
    	                .filter(e -> filterByPeriod(e, selectedPeriod))
    	                .collect(Collectors.toList());
    	    }

    	    // Filtrer par genre de livre
    	    if (!selectedGenre.equals("Tous les genres")) {
    	        filteredEmprunts = filteredEmprunts.stream()
    	                .filter(e -> {
    	                    Livre livre = livreController.getLivreById(e.getLivreId());
    	                    return livre != null && livre.getGenre().equals(selectedGenre);
    	                })
    	                .collect(Collectors.toList());
    	    }

    	    // Filtrer par utilisateur
    	    if (!selectedUser.equals("Tous les utilisateurs")) {
    	        filteredEmprunts = filteredEmprunts.stream()
    	                .filter(e -> e.getUserId().equals(selectedUser))
    	                .collect(Collectors.toList());
    	    }

    	    // Mise à jour des graphiques avec les emprunts filtrés
    	    updateCharts(filteredEmprunts);
    	}

    	private void updateCharts(List<Emprunt> emprunts) {
    	    // Exemple de données dynamiques pour les graphiques
    	    List<String> bookTitles = emprunts.stream()
    	        .map(e -> {
    	            Livre livre = livreController.getLivreById(e.getLivreId());
    	            return livre != null ? livre.getTitre() : "Livre inconnu"; // Gestion de l'ID inconnu
    	        })
    	        .collect(Collectors.toList());

    	    List<Double> borrowCounts = emprunts.stream()
    	        .collect(Collectors.groupingBy(Emprunt::getLivreId, Collectors.summingInt(e -> 1)))
    	        .values().stream()
    	        .map(Integer::doubleValue) // Convertir Integer en Double
    	        .collect(Collectors.toList());

    	    if (bookTitles.size() == borrowCounts.size() && !bookTitles.isEmpty()) {
    	        // Créez le graphique à barres
    	        JComponent barChart = dashboardView.createBarChart("Emprunts par Livre", "Livres", "Nombre d'Emprunts", bookTitles, borrowCounts);
    	        dashboardView.setChart(barChart); // Assurez-vous d'appeler setChart ici
    	    } else {
    	        System.out.println("Les tailles des listes ne correspondent pas ou sont vides !");
    	    }
    	}

    // Méthode pour filtrer les emprunts par période (ex. cette année, ce mois)
    private boolean filterByPeriod(Emprunt emprunt, String period) {
        LocalDate now = LocalDate.now();
        switch (period) {
            case "Cette année":
                return emprunt.getDateEmprunt().getYear() == now.getYear();
            case "Ce mois":
                return emprunt.getDateEmprunt().getYear() == now.getYear() &&
                       emprunt.getDateEmprunt().getMonthValue() == now.getMonthValue();
            default:
                return true;
        }
    }
    public void searchUsers(String query) {
        List<User> filteredUsers = userDAO.rechercherParCritere(query);
        // Mettez à jour la vue avec les utilisateurs filtrés
        dashboardView.updateUserList(filteredUsers); // Assurez-vous d'avoir une méthode pour mettre à jour la liste des utilisateurs
    }
    private void updateActiveUsersChart() {
        List<User> utilisateursActifs = userController.getActiveUsers(); // Méthode à définir dans UserController
        List<String> userNames = utilisateursActifs.stream()
            .map(User::getNom) // Remplacez par la méthode appropriée pour obtenir le nom
            .collect(Collectors.toList());

        List<Double> userCounts = utilisateursActifs.stream()
            .map(user -> 1.0) // Chaque utilisateur actif compte comme 1
            .collect(Collectors.toList());

        if (!userNames.isEmpty() && !userCounts.isEmpty()) {
            JComponent pieChart = dashboardView.createPieChart("Utilisateurs Actifs", userNames, userCounts);
            dashboardView.setChart(pieChart);
        } else {
            System.out.println("Aucun utilisateur actif à afficher !");
        }
    }
    
    private void updateLineChart(List<Emprunt> emprunts) {
        // Préparez les données pour le graphique linéaire
        List<Double> xValues = new ArrayList<>(); // Par exemple, les dates ou les mois
        List<Double> yValues = new ArrayList<>(); // Par exemple, le nombre d'emprunts

        // Exemple de remplissage des listes
        for (Emprunt emprunt : emprunts) {
            // Ajoutez la logique pour remplir xValues et yValues
            // Par exemple, vous pouvez utiliser la date d'emprunt pour xValues et le nombre d'emprunts pour yValues
            xValues.add((double) emprunt.getDateEmprunt().getDayOfMonth()); // Exemple : jour du mois
            yValues.add(1.0); // Exemple : chaque emprunt compte comme 1
        }

        // Créez le graphique linéaire
        if (!xValues.isEmpty() && !yValues.isEmpty()) {
            JComponent lineChart = dashboardView.createLineChart("Emprunts au Fil du Temps", "Temps", "Nombre d'Emprunts", xValues, yValues);
            dashboardView.setChart(lineChart);
        } else {
            System.out.println("Aucune donnée à afficher dans le graphique linéaire !");
        }
    }
 
}