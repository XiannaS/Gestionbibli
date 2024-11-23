package vue;

import javax.swing.*;
import java.awt.*;

public class ConnexionView extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField emailField;
    private JPasswordField passwordField;
    private JButton connexionButton;
    private JButton inscriptionButton;

    public ConnexionView() {
        setTitle("Connexion");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Connexion", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(emailField);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(passwordField);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        connexionButton = new JButton("Connexion");
        connexionButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        connexionButton.setBackground(new Color(100, 150, 200));
        connexionButton.setForeground(Color.WHITE);
        connexionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(connexionButton);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        inscriptionButton = new JButton("Inscription");
        inscriptionButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        inscriptionButton.setBackground(new Color(100, 150, 200));
        inscriptionButton.setForeground(Color.WHITE);
        inscriptionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(inscriptionButton);

        add(panel);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getMotDePasse() {
        return new String(passwordField.getPassword());
    }

    public JButton getConnexionButton() {
        return connexionButton;
    }

    public JButton getInscriptionButton() {
        return inscriptionButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConnexionView connexionView = new ConnexionView();
            connexionView.setVisible(true);
        });
    }
}
