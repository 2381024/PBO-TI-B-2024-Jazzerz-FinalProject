package views;

import entities.User;
import services.UserService;
import services.UserServiceImpl;
import repositories.UserRepositoryImpl;

import java.util.Scanner;

public class MainMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to Jazzerz Suit Renting Store!");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 3 -> {
                    System.out.println("Thank you for visiting Jazzerz!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userService.login(username, password);
        if (user != null) {
            if (user.isAdmin()) {
                AdminView.show(user);
            } else {
                UserView.show(user);
            }
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Are you an admin? (yes/no): ");
        boolean isAdmin = scanner.nextLine().equalsIgnoreCase("yes");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAdmin(isAdmin);

        userService.register(user);
        System.out.println("Registration successful!");
    }
}
