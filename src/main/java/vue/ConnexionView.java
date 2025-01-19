package vue;

import javax.swing.*;
import java.awt.*;

public class ConnexionView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public ConnexionView() {
        setTitle("Connexion");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set layout for the frame
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Nom d'utilisateur:");
        JLabel passwordLabel = new JLabel("Mot de passe:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Se connecter");

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());  // Empty label for spacing
        add(loginButton);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}