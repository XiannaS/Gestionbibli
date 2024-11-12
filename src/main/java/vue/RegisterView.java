package vue;

import javax.swing.*;
import java.awt.*;
import controllers.UserController;

public class RegisterView extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
    private UserController userController;

    public RegisterView(MainFrame mainFrame, UserController userController) {
        this.mainFrame = mainFrame;
        this.userController = userController;
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Ajoutez les champs pour prénom, nom, email, mot de passe, confirmation mot de passe, et bouton d'inscription

        JButton backButton = new JButton("Retour");
        backButton.addActionListener(e -> mainFrame.showView("login"));
        gbc.gridy = 6;
        add(backButton, gbc);
    }
}
