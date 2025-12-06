package airline.reservation.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    private static final Pattern EMAIL_PATTERN = 
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = 
            Pattern.compile("^[0-9]{10}$");
    private static final Pattern FLIGHT_NUMBER_PATTERN = 
            Pattern.compile("^[A-Z0-9]{2}[0-9]{3,4}$");
    
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isValidPhone(String phone) {
        if (phone == null) {
            return false;
        }
        String cleanPhone = phone.replaceAll("[\\s-()]", "");
        return PHONE_PATTERN.matcher(cleanPhone).matches();
    }
    
    public static boolean isValidFlightNumber(String flightNumber) {
        if (flightNumber == null || flightNumber.trim().isEmpty()) {
            return false;
        }
        return FLIGHT_NUMBER_PATTERN.matcher(flightNumber.toUpperCase()).matches();
    }
    
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
    
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.trim().length() >= 2;
    }
    
    public static boolean isPositiveNumber(int number) {
        return number > 0;
    }
    
    public static boolean isPositiveNumber(double number) {
        return number > 0.0;
    }
    
    public static boolean isWithinRange(int value, int min, int max) {
        return value >= min && value <= max;
    }
    
    public static String cleanString(String input) {
        return input == null ? "" : input.trim();
    }
}
