package views;

import entities.Suit;
import entities.User;
import services.SuitService;
import services.SuitServiceImpl;
import repositories.SuitRepositoryImpl;

import java.util.*;

public class UserView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SuitService suitService = new SuitServiceImpl(new SuitRepositoryImpl());
    private static final Map<Suit, Integer> cart = new HashMap<>();
    private static double totalPrice = 0;

    public static void show(User user) {
        while (true) {
            System.out.println("\nUser Panel:");
            System.out.println("1. View Available Suits");
            System.out.println("2. Rent a Suit");
            System.out.println("3. View Cart");
            System.out.println("4. Proceed to Payment");
            System.out.println("5. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> viewAvailableSuits();
                case 2 -> rentSuit();
                case 3 -> viewCart();
                case 4 -> proceedToPayment();
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewAvailableSuits() {
        System.out.println("\nAvailable Suits:");
        List<Suit> suits = suitService.getAvailableSuits();
        if (suits.isEmpty()) {
            System.out.println("No suits available.");
        } else {
            suits.forEach(suit -> {
                System.out.printf("ID: %d | Color: %s | Size: %s | Stock: %d | Price/Day: Rp%.2f\n",
                        suit.getId(), suit.getColor(), suit.getSize(), suit.getStock(), suit.getPricePerDay());
            });
        }
    }

    private static void rentSuit() {
        System.out.print("Enter Suit ID to rent: ");
        int suitId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Suit suit = suitService.getSuitById(suitId);
        if (suit != null && suit.getStock() > 0) {
            System.out.print("Enter number of days to rent: ");
            int days = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Add to cart
            cart.put(suit, days);

            // Update the total price
            totalPrice += suit.getPricePerDay() * days;

            // Reduce the stock of the suit
            suit.setStock(suit.getStock() - 1);
            suitService.updateSuit(suit);

            System.out.println("Suit added to cart!");
        } else {
            System.out.println("Suit not available or stock is empty.");
        }
    }

    private static void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("\nYour Cart:");
            cart.forEach((suit, days) -> {
                System.out.printf("Color: %s | Size: %s | Days: %d | Price per Day: Rp%.2f | Total: Rp%.2f\n",
                        suit.getColor(), suit.getSize(), days, suit.getPricePerDay(),
                        suit.getPricePerDay() * days);
            });
            System.out.printf("\nTotal Price: Rp%.2f\n", totalPrice);
        }
    }

    private static void proceedToPayment() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Please add suits to your cart first.");
        } else {
            System.out.println("\nProceeding to payment...");
            System.out.println("Total Price: Rp" + totalPrice);
            System.out.println("Select payment option:");
            System.out.println("1. Credit Card");
            System.out.println("2. PayPal");
            System.out.println("3. Cash");

            int paymentChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (paymentChoice) {
                case 1 -> System.out.println("Payment via Credit Card successful.");
                case 2 -> System.out.println("Payment via PayPal successful.");
                case 3 -> System.out.println("Payment via Cash successful.");
                default -> System.out.println("Invalid choice. Payment failed.");
            }

            // After payment, clear the cart
            cart.clear();
            totalPrice = 0;
            System.out.println("Payment completed. Your suits are now rented.");
        }
    }
}
