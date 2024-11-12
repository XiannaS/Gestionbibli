package controllers;

import javax.swing.SwingUtilities;
import vue.MainFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserController userController = new UserController();
            MainFrame mainFrame = new MainFrame(userController);
            mainFrame.setVisible(true);
        });
    }
}
