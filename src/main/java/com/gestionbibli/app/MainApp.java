package com.gestionbibli.app;

import vue.ConnexionView;
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

        // Create an instance of ConnexionView
        ConnexionView connexionView = new ConnexionView();

        // Define the path for the user data file 
        String userDataFilePath = "src/main/resources/data/users.csv"; // Replace with your actual file path

        // Create an instance of UserDAO with the file path
        UserDAO userDAO = new UserDAO(userDataFilePath);

        // Create the controller for the connection view with UserDAO
        ConnexionController connexionController = new ConnexionController(connexionView, userDAO);

        // Set the connection view to be visible
        connexionView.setVisible(true);
    }
}
