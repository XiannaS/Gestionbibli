// Main.java
package controllers;

import javax.swing.SwingUtilities;
import vue.DashboardView;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashboardView dashboardView = new DashboardView();
            dashboardView.setVisible(true);
        });
    }
}
