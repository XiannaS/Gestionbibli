package vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.swing.Box;


public class InscriptionView extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton inscriptionButton;
    private JButton retourButton; // Nouveau bouton pour revenir à la connexion

    public InscriptionView() {
        setTitle("Inscription");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Inscription", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel nomLabel = new JLabel("Nom:");
        nomLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(nomLabel);

        nomField = new JTextField();
        nomField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nomField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(nomField);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel prenomLabel = new JLabel("Prénom:");
        prenomLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(prenomLabel);

        prenomField = new JTextField();
        prenomField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        prenomField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(prenomField);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

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

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Ajout du bouton "Retour"
        retourButton = new JButton("Retour");
        retourButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        retourButton.setBackground(new Color(100, 150, 200));
        retourButton.setForeground(Color.WHITE);
        retourButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(retourButton);
        
        // Supprimez la partie JComboBox pour le rôle

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        inscriptionButton = new JButton("Inscription");
        inscriptionButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        inscriptionButton.setBackground(new Color(100, 150, 200));
        inscriptionButton.setForeground(Color.WHITE);
        inscriptionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(inscriptionButton);

        add(panel);
    }
    public JButton getRetourButton() {
        return retourButton;
    }
    public String getNom() {
        return nomField.getText();
    }

    public String getPrenom() {
        return prenomField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getMotDePasse() {
        return new String(passwordField.getPassword());
    }

    // Le rôle est maintenant fixé à "Membre", donc cette méthode est inutile
    // public String getRole() {
    //    return (String) roleComboBox.getSelectedItem();
    // }

    public JButton getInscriptionButton() {
        return inscriptionButton;
    }
}
