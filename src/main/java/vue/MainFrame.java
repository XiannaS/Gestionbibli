package vue;

import javax.swing.*;
import java.awt.*;
import controllers.UserController;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private UserController userController;

    public MainFrame(UserController userController) {
        this.userController = userController;
        
        setTitle("Library Management System");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new CardLayout());
        
        // Ajout des vues de connexion, inscription et récupération de mot de passe
        mainPanel.add(new LoginView(this, userController), "login");
        mainPanel.add(new RegisterView(this, userController), "register");
        mainPanel.add(new ForgotPasswordView(this, userController), "forgotPassword");
        
        add(mainPanel);
        showView("login");  // On commence avec la vue de connexion
        
        setVisible(true);
    }
    
    // Méthode pour afficher une vue spécifique
    public void showView(String viewName) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, viewName);
    }
}
