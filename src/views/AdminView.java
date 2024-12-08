package views;

import entities.Suit;
import entities.User;
import repositories.SuitRepositoryImpl;
import services.SuitService;
import services.SuitServiceImpl;

import java.util.Scanner;

public class AdminView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SuitService suitService = new SuitServiceImpl(new SuitRepositoryImpl());

    public static void show(User admin) {
        while (true) {
            System.out.println("\nAdmin Panel:");
            System.out.println("1. View Suits");
            System.out.println("2. Edit Suit");
            System.out.println("3. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> viewSuits();
                case 2 -> editSuit();
                case 3 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewSuits() {
        suitService.getAvailableSuits().forEach(suit -> {
            System.out.printf("ID: %d | Color: %s | Size: %s | Stock: %d | Price/Day: Rp%.2f\n",
                    suit.getId(), suit.getColor(), suit.getSize(), suit.getStock(), suit.getPricePerDay());
        });
    }

    private static void editSuit() {
        System.out.print("Enter Suit ID to edit: ");
        int suitId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Suit suit = suitService.getSuitById(suitId);
        if (suit != null) {
            System.out.print("Enter new color: ");
            suit.setColor(scanner.nextLine());
            System.out.print("Enter new size: ");
            suit.setSize(scanner.nextLine());
            System.out.print("Enter new stock: ");
            suit.setStock(scanner.nextInt());
            System.out.print("Enter new price per day: ");
            suit.setPricePerDay(scanner.nextDouble());

            suitService.updateSuit(suit);
            System.out.println("Suit updated successfully!");
        } else {
            System.out.println("Suit not found.");
        }
    }
}
