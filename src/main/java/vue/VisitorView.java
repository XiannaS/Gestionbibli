package vue;

import model.Livre;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VisitorView extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel booksPanel;

    public VisitorView() {
        setTitle("Livres Disponibles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panneau pour afficher les livres
        booksPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        JScrollPane scrollPane = new JScrollPane(booksPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayBooks(List<Livre> livres) {
        booksPanel.removeAll();

        for (Livre livre : livres) {
            JPanel bookPanel = createBookPanel(livre);
            booksPanel.add(bookPanel);
        }

        booksPanel.revalidate();
        booksPanel.repaint();
    }

    private JPanel createBookPanel(Livre livre) {
        JPanel bookPanel = new JPanel(new BorderLayout());
        bookPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        ImageIcon bookImage = new ImageIcon(new ImageIcon(livre.getImageUrl()).getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
        JLabel imageLabel = new JLabel(bookImage);

        JLabel titleLabel = new JLabel(livre.getTitre());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        bookPanel.add(imageLabel, BorderLayout.CENTER);
        bookPanel.add(titleLabel, BorderLayout.SOUTH);

        return bookPanel;
    }
}