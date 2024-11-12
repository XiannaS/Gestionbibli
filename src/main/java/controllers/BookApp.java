package controllers;

import javax.swing.*;
import java.awt.event.*;

public class BookApp extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Menu Bar
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem discoverMenuItem;
    private JMenuItem categoryMenuItem;
    private JMenuItem myLibraryMenuItem;
    private JMenuItem downloadMenuItem;
    private JMenuItem favoriteMenuItem;
    private JMenuItem settingMenuItem;
    private JMenuItem helpMenuItem;
    private JMenuItem logoutMenuItem;

    // Discover Section
    private JLabel discoverLabel;
    private JComboBox<String> allCategoriesDropdown;
    private JTextField searchField;
    private JButton searchButton;

    // Book Recommendation Section
    private JLabel bookRecommendationLabel;
    private JPanel bookPanel1;
    private JPanel bookPanel2;
    private JPanel bookPanel3;
    private JLabel book1TitleLabel;
    private JLabel book1AuthorLabel;
    private JLabel book1DescriptionLabel;
    private JLabel book2TitleLabel;
    private JLabel book2AuthorLabel;
    private JLabel book2DescriptionLabel;
    private JLabel book3TitleLabel;
    private JLabel book3AuthorLabel;
    private JLabel book3DescriptionLabel;

    // Book Category Section
    private JLabel bookCategoryLabel;
    private JPanel categoryPanel1;
    private JPanel categoryPanel2;
    private JPanel categoryPanel3;
    private JPanel categoryPanel4;
    private JLabel category1TitleLabel;
    private JLabel category2TitleLabel;
    private JLabel category3TitleLabel;
    private JLabel category4TitleLabel;

    // Book Library Section
    private JLabel bookLibraryLabel;
    private JPanel bookLibraryPanel;

    // User Profile Section
    private JLabel userProfileLabel;
    private JButton viewAllButton;

    public BookApp() {
        // Set up JFrame
        super("Book App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Menu Bar
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        discoverMenuItem = new JMenuItem("Discover");
        categoryMenuItem = new JMenuItem("Category");
        myLibraryMenuItem = new JMenuItem("My Library");
        downloadMenuItem = new JMenuItem("Download");
        favoriteMenuItem = new JMenuItem("Favorite");
        settingMenuItem = new JMenuItem("Settings");
        helpMenuItem = new JMenuItem("Help");
        logoutMenuItem = new JMenuItem("Logout");

        menu.add(discoverMenuItem);
        menu.add(categoryMenuItem);
        menu.add(myLibraryMenuItem);
        menu.add(downloadMenuItem);
        menu.add(favoriteMenuItem);
        menu.add(settingMenuItem);
        menu.add(helpMenuItem);
        menu.add(logoutMenuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Discover Section
        discoverLabel = new JLabel("Discover Books:");
        discoverLabel.setBounds(20, 20, 200, 30);
        add(discoverLabel);

        allCategoriesDropdown = new JComboBox<>(new String[]{"All Categories", "Fiction", "Non-Fiction", "Science", "History"});
        allCategoriesDropdown.setBounds(20, 60, 200, 30);
        add(allCategoriesDropdown);

        searchField = new JTextField();
        searchField.setBounds(240, 60, 200, 30);
        add(searchField);

        searchButton = new JButton("Search");
        searchButton.setBounds(460, 60, 100, 30);
        searchButton.addActionListener(this);
        add(searchButton);

        // Book Recommendation Section
        bookRecommendationLabel = new JLabel("Recommended Books:");
        bookRecommendationLabel.setBounds(20, 100, 200, 30);
        add(bookRecommendationLabel);

        bookPanel1 = new JPanel();
        bookPanel1.setBounds(20, 140, 200, 100);
        book1TitleLabel = new JLabel("Book 1 Title");
        book1AuthorLabel = new JLabel("Author 1");
        book1DescriptionLabel = new JLabel("Description 1");
        bookPanel1.add(book1TitleLabel);
        bookPanel1.add(book1AuthorLabel);
        bookPanel1.add(book1DescriptionLabel);
        add(bookPanel1);

        bookPanel2 = new JPanel();
        bookPanel2.setBounds(240, 140, 200, 100);
        book2TitleLabel = new JLabel("Book 2 Title");
        book2AuthorLabel = new JLabel("Author 2");
        book2DescriptionLabel = new JLabel("Description 2");
        bookPanel2.add(book2TitleLabel);
        bookPanel2.add(book2AuthorLabel);
        bookPanel2.add(book2DescriptionLabel);
        add(bookPanel2);

        bookPanel3 = new JPanel();
        bookPanel3.setBounds(460, 140, 200, 100);
        book3TitleLabel = new JLabel("Book 3 Title");
        book3AuthorLabel = new JLabel("Author 3");
        book3DescriptionLabel = new JLabel("Description 3");
        bookPanel3.add(book3TitleLabel);
        bookPanel3.add(book3AuthorLabel);
        bookPanel3.add(book3DescriptionLabel);
        add(bookPanel3);

        // Book Category Section
        bookCategoryLabel = new JLabel("Book Categories:");
        bookCategoryLabel.setBounds(20, 260, 200, 30);
        add(bookCategoryLabel);

        categoryPanel1 = new JPanel();
        categoryPanel1.setBounds(20, 300, 200, 100);
        category1TitleLabel = new JLabel("Category 1");
        categoryPanel1.add(category1TitleLabel);
        add(categoryPanel1);

        categoryPanel2 = new JPanel();
        categoryPanel2.setBounds(240, 300, 200, 100);
        category2TitleLabel = new JLabel("Category 2");
        categoryPanel2.add(category2TitleLabel);
        add(categoryPanel2);

        categoryPanel3 = new JPanel();
        categoryPanel3.setBounds(460, 300, 200, 100);
        category3TitleLabel = new JLabel("Category 3");
        categoryPanel3.add(category3TitleLabel);
        add(categoryPanel3);

        categoryPanel4 = new JPanel();
        categoryPanel4.setBounds(680, 300, 200, 100);
        category4TitleLabel = new JLabel("Category 4");
        categoryPanel4.add(category4TitleLabel);
        add(categoryPanel4);

        // Book Library Section
        bookLibraryLabel = new JLabel("My Library:");
        bookLibraryLabel.setBounds(20, 420, 200, 30);
        add(bookLibraryLabel);

        bookLibraryPanel = new JPanel();
        bookLibraryPanel.setBounds(20, 460, 760, 100);
        add(bookLibraryPanel);

        // User Profile Section
        userProfileLabel = new JLabel("User  Profile:");
        userProfileLabel.setBounds(20, 580, 200, 30);
        add(userProfileLabel);

        viewAllButton = new JButton("View All");
        viewAllButton.setBounds(240, 580, 100, 30);
        viewAllButton.addActionListener(this);
        add(viewAllButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            // Handle search action
            String searchTerm = searchField.getText();
            // Implement search logic here
        } else if (e.getSource() == viewAllButton) {
            // Handle view all action
            // Implement view all logic here
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookApp app = new BookApp();
            app.setVisible(true);
        });
    }
} 