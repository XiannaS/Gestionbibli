package controllers;

import vue.ConnexionView;
import vue.InscriptionView;

public class Main {
    public static void main(String[] args) {
        ConnexionView connexionView = new ConnexionView();
        InscriptionView inscriptionView = new InscriptionView();
        AuthController authController = new AuthController(connexionView, inscriptionView);

        connexionView.setVisible(true); // Afficher la vue de connexion
    }
}