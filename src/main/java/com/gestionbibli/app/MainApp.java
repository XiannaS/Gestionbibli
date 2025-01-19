package com.gestionbibli.app;

import vue.BibliothequeApp;
import vue.ConnexionView;
import javax.swing.*;

import controllers.BibliothequeAppController;
import controllers.ConnexionController;
import model.UserDAO;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        // Set the look and feel to the default system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create an instance of BibliothequeApp
        BibliothequeApp bibliothequeApp = new BibliothequeApp();

        // Create the controller for the application
        BibliothequeAppController controller = new BibliothequeAppController(bibliothequeApp);

        // Set the application to be visible
        bibliothequeApp.setVisible(true);
    }
}