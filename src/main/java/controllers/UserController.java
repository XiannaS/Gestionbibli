package controllers;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserController {
    private static final String USER_FILE = "users.csv";
    private Map<String, String> users = new HashMap<>();

    public UserController() {
        loadUsers();
    }

    private void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]); // username, password
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load users.");
        }
    }

    private void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save users.");
        }
    }

    public boolean login(String username, String password) {
        String storedPassword = users.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false; // User already exists
        }
        users.put(username, password);
        saveUsers();
        return true;
    }

 // Dans UserController
    public boolean resetPassword(String email) {
        if (users.containsKey(email)) {
            // Logique pour envoyer un email de réinitialisation
            System.out.println("Instructions de réinitialisation envoyées à " + email);
            return true;
        }
        return false; // L'email n'existe pas
    }

}
