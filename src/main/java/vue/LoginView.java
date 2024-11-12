package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controllers.UserController;

public class LoginView extends JPanel {
    private MainFrame mainFrame;
    private UserController userController;

    public LoginView(MainFrame mainFrame, UserController userController) {
        this.mainFrame = mainFrame;
        this.userController = userController;
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Champ pour l'email
        JTextField emailField = new JTextField(15);
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(emailField, gbc);

        // Champ pour le mot de passe
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBorder(BorderFactory.createTitledBorder("Mot de passe"));
        gbc.gridy = 1;
        add(passwordField, gbc);

        // Bouton de connexion
        JButton loginButton = new JButton("Connexion");
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (userController.login(email, password)) {
                JOptionPane.showMessageDialog(this, "Connexion réussie !");
            } else {
                JOptionPane.showMessageDialog(this, "Échec de la connexion. Veuillez réessayer.");
            }
        });
        gbc.gridy = 2;
        add(loginButton, gbc);

        // Lien pour s'inscrire
        JButton registerButton = new JButton("Inscription");
        registerButton.setForeground(Color.BLUE);
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.addActionListener(e -> mainFrame.showView("register"));
        gbc.gridy = 3;
        add(registerButton, gbc);

        // Lien pour mot de passe oublié
        JButton forgotPasswordButton = new JButton("Mot de passe oublié ?");
        forgotPasswordButton.setForeground(Color.BLUE);
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setContentAreaFilled(false);
        forgotPasswordButton.addActionListener(e -> mainFrame.showView("forgotPassword"));
        gbc.gridy = 4;
        add(forgotPasswordButton, gbc);
    }
}
