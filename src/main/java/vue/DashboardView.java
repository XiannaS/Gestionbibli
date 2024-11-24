package vue;

import javax.swing.*;

import model.User;

import java.awt.*;

public class DashboardView extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DashboardView(User user) {
        setLayout(new BorderLayout()); // Layout pour organiser les composants
        JLabel label = new JLabel("Bienvenue dans le Dashboard");
        add(label, BorderLayout.CENTER);
    }
}
