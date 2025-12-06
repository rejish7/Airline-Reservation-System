package airline.reservation.util;

import java.util.Scanner;

public class ConsoleUtil {
    private static final Scanner scanner = new Scanner(System.in);
    
    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    
    public static long readLong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    
    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    
    public static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            }
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
        }
    }
    
    public static void printLine() {
        System.out.println("================================================================================");
    }
    
    public static void printDoubleLine() {
        System.out.println("================================================================================");
        System.out.println("================================================================================");
    }
    
    public static void printHeader(String title) {
        printLine();
        System.out.println(centerText(title, 80));
        printLine();
    }
    
    public static void printSuccess(String message) {
        System.out.println("✓ " + message);
    }
    
    public static void printError(String message) {
        System.out.println("✗ ERROR: " + message);
    }
    
    public static void printWarning(String message) {
        System.out.println(" WARNING: " + message);
    }
    
    public static void printInfo(String message) {
        System.out.println("ℹ " + message);
    }
    
    public static void clearScreen() {
        // This works on most terminals
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private static String centerText(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        int padding = (width - text.length()) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        sb.append(text);
        return sb.toString();
    }
    
    public static void closeScanner() {
        scanner.close();
    }
}
