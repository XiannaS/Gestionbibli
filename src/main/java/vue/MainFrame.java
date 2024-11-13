package vue;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controllers.UserController;

public class MainFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
    private UserController userController;

    // No-argument constructor
    public MainFrame() {
        this.userController = new UserController(); // Or any default behavior
        initializeUI();  // Initialize the UI components
    }

    // Constructor with a UserController parameter
    public MainFrame(UserController userController) {
        this.userController = userController;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        mainPanel = new JPanel(new CardLayout());
        add(mainPanel);
    }
}
