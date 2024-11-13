import java.util.Scanner;

import controllers.UserController;

public class LoginView {
    private UserController userController;

    public LoginView(UserController userController) {
        this.userController = userController;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Connexion");
            System.out.println("2. Inscription");
            System.out.println("3. Mot de passe oublié");
            System.out.println("0. Quitter");
            System.out.print("Choisissez une option : ");
            choice = scanner.nextInt();
            scanner.nextLine(); // pour consommer la nouvelle ligne

            switch (choice) {
                case 1 -> login(scanner);
                case 2 -> register(scanner);
                case 3 -> resetPassword(scanner);
                case 0 -> System.out.println("Au revoir !");
                default -> System.out.println("Option invalide. Essayez encore.");
            }
        } while (choice != 0);
    }

    private void login(Scanner scanner) {
        System.out.print("Nom d'utilisateur : ");
        String username = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        if (userController.authenticate(username, password)) {
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

    private void register(Scanner scanner) {
        System.out.print("Choisissez un nom d'utilisateur : ");
        String username = scanner.nextLine();
        System.out.print("Choisissez un mot de passe : ");
        String password = scanner.nextLine();

        userController.register(username, password);
    }

    private void resetPassword(Scanner scanner) {
        System.out.print("Nom d'utilisateur : ");
        String username = scanner.nextLine();
        System.out.print("Nouveau mot de passe : ");
        String newPassword = scanner.nextLine();

        userController.resetPassword(username, newPassword);
    }

    public static void main(String[] args) {
        UserController userController = new UserController();
        LoginView loginView = new LoginView(userController);
        loginView.displayMenu();
    }
}
