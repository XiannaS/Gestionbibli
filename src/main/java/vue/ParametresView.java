package vue;

import javax.swing.*;

import controllers.UserController;
import model.User;

import java.util.List;
import java.awt.*;

public class ParametresView extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField currentRoleField; // Champ pour afficher le rôle actuel
    public ParametresView(User currentUser , UserController userController, List<User> userList) {
        // Initialisation des composants
        setLayout(new BorderLayout()); // Utilisation d'un layout pour organiser les composants

        // Panneau pour afficher le rôle actuel
        JPanel rolePanel = new JPanel();
        rolePanel.add(new JLabel("Rôle actuel :"));
        
        currentRoleField = new JTextField(20);
        currentRoleField.setEditable(false); // Rendre le champ non modifiable
        currentRoleField.setText(currentUser .getRole().getLabel()); // Afficher le rôle actuel
        rolePanel.add(currentRoleField);

        add(rolePanel, BorderLayout.NORTH); // Ajouter le panneau de rôle en haut

        // Exemple d'un bouton pour fermer les paramètres
        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(e -> {
            // Action à effectuer lors de la fermeture, par exemple, masquer le panneau
            // ou effectuer d'autres actions.
            // Si vous avez un parent JFrame, vous pouvez le fermer ou le masquer ici.
            System.out.println("Fermeture des paramètres");
        });
        add(closeButton, BorderLayout.SOUTH); // Ajouter le bouton en bas
    }

 
}