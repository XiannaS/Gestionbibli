package controllers;
import vue.DashboardView;
import vue.EmpruntView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Main() {
        setTitle("Application Principale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Ajoutez vos vues ici
        mainPanel.add(new DashboardView(), "Home");
        mainPanel.add(new UserView(), "Users");
        mainPanel.add(new EmpruntView(), "Emprunt");
        mainPanel.add(new RapportView(), "Rapports");
        mainPanel.add(new ParamètresView(), "Paramètres");

        // Créer la barre de navigation
        ModernNavBar navBar = new ModernNavBar(new MenuActionListener());

        // Ajouter la barre de navigation et le panneau principal à la fenêtre
        add(navBar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    private class MenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            cardLayout.show(mainPanel, command);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}