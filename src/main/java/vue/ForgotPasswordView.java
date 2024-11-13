package vue;

import javax.swing.*;
import java.awt.*;
import controllers.UserController;

public class ForgotPasswordView extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForgotPasswordView(MainFrame mainFrame, UserController userController) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField emailField = new JTextField(15);
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(emailField, gbc);

        JButton resetPasswordButton = new JButton("Réinitialiser mot de passe");
        resetPasswordButton.addActionListener(e -> {
            String email = emailField.getText();
            userController.resetPassword(email);
            JOptionPane.showMessageDialog(this, "Instructions de réinitialisation envoyées par email.");
        });
        gbc.gridy = 1;
        add(resetPasswordButton, gbc);

        JButton backButton = new JButton("Retour");
        backButton.addActionListener(e -> mainFrame.showView("login"));
        gbc.gridy = 2;
        add(backButton, gbc);
    }
}
