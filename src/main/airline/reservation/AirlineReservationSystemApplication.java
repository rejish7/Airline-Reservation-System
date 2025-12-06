package airline.reservation;

import airline.reservation.entity.Booking;
import airline.reservation.entity.Flight;
import airline.reservation.entity.Passenger;
import airline.reservation.entity.User;
import airline.reservation.service.BookingService;
import airline.reservation.service.FlightService;
import airline.reservation.service.UserService;
import airline.reservation.util.ConsoleUtil;
import airline.reservation.util.DateUtil;
import airline.reservation.util.ValidationUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AirlineReservationSystemApplication {
    private UserService userService;
    private FlightService flightService;
    private BookingService bookingService;
    private boolean running;
    
    public AirlineReservationSystemApplication() {
        this.userService = new UserService();
        this.flightService = new FlightService();
        this.bookingService = new BookingService(
                new airline.reservation.repository.FlightRepository());
        this.running = true;
    }
    
    public static void main(String[] args) {
        AirlineReservationSystemApplication app = new AirlineReservationSystemApplication();
        app.run();
    }
    
    public void run() {
        ConsoleUtil.printDoubleLine();
        ConsoleUtil.printHeader("AIRLINE RESERVATION SYSTEM");
        ConsoleUtil.printDoubleLine();
        
        while (running) {
            if (!userService.isLoggedIn()) {
                showMainMenu();
            } else if (userService.isAdmin()) {
                showAdminMenu();
            } else {
                showCustomerMenu();
            }
        }
        
        ConsoleUtil.closeScanner();
    }
    
    private void showMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Search Flights (Guest)");
        System.out.println("4. Exit");
        
        int choice = ConsoleUtil.readInt("\nEnter your choice: ");
        
        switch (choice) {
            case 1:
                handleLogin();
                break;
            case 2:
                handleRegistration();
                break;
            case 3:
                handleGuestFlightSearch();
                break;
            case 4:
                running = false;
                System.out.println("\nThank you for using Airline Reservation System!");
                break;
            default:
                ConsoleUtil.printError("Invalid choice. Please try again.");
        }
    }
    
    private void showAdminMenu() {
        System.out.println("\n=== ADMIN DASHBOARD ===");
        System.out.println("Welcome, " + userService.getCurrentUser().getFullName());
        System.out.println("\n1. Manage Flights");
        System.out.println("2. View All Bookings");
        System.out.println("3. View Statistics");
        System.out.println("4. View All Users");
        System.out.println("5. Logout");
        
        int choice = ConsoleUtil.readInt("\nEnter your choice: ");
        
        switch (choice) {
            case 1:
                handleFlightManagement();
                break;
            case 2:
                handleViewAllBookings();
                break;
            case 3:
                handleViewStatistics();
                break;
            case 4:
                handleViewAllUsers();
                break;
            case 5:
                userService.logout();
                ConsoleUtil.printSuccess("Logged out successfully!");
                break;
            default:
                ConsoleUtil.printError("Invalid choice. Please try again.");
        }
    }
    
    private void showCustomerMenu() {
        System.out.println("\n=== CUSTOMER DASHBOARD ===");
        System.out.println("Welcome, " + userService.getCurrentUser().getFullName());
        System.out.println("\n1. Search Flights");
        System.out.println("2. Book Flight");
        System.out.println("3. My Bookings");
        System.out.println("4. Cancel Booking");
        System.out.println("5. View Profile");
        System.out.println("6. Logout");
        
        int choice = ConsoleUtil.readInt("\nEnter your choice: ");
        
        switch (choice) {
            case 1:
                handleFlightSearch();
                break;
            case 2:
                handleBookFlight();
                break;
            case 3:
                handleViewMyBookings();
                break;
            case 4:
                handleCancelBooking();
                break;
            case 5:
                handleViewProfile();
                break;
            case 6:
                userService.logout();
                ConsoleUtil.printSuccess("Logged out successfully!");
                break;
            default:
                ConsoleUtil.printError("Invalid choice. Please try again.");
        }
    }
    
    // Authentication handlers
    private void handleLogin() {
        ConsoleUtil.printHeader("LOGIN");
        String username = ConsoleUtil.readString("Username: ");
        String password = ConsoleUtil.readString("Password: ");
        
        if (userService.login(username, password)) {
            ConsoleUtil.printSuccess("Login successful!");
        } else {
            ConsoleUtil.printError("Invalid username or password.");
        }
    }
    
    private void handleRegistration() {
        ConsoleUtil.printHeader("USER REGISTRATION");
        
        try {
            String username = ConsoleUtil.readString("Username: ");
            String password = ConsoleUtil.readString("Password (min 6 characters): ");
            String email = ConsoleUtil.readString("Email: ");
            String fullName = ConsoleUtil.readString("Full Name: ");
            String phoneNumber = ConsoleUtil.readString("Phone Number: ");
            
            User user = userService.registerUser(username, password, email, fullName, phoneNumber);
            ConsoleUtil.printSuccess("Registration successful! You can now login.");
            System.out.println("Your User ID: " + user.getUserId());
        } catch (Exception e) {
            ConsoleUtil.printError(e.getMessage());
        }
    }
    
    // Flight handlers
    private void handleFlightManagement() {
        System.out.println("\n=== FLIGHT MANAGEMENT ===");
        System.out.println("1. Add New Flight");
        System.out.println("2. View All Flights");
        System.out.println("3. Update Flight Status");
        System.out.println("4. Delete Flight");
        System.out.println("5. Back");
        
        int choice = ConsoleUtil.readInt("\nEnter your choice: ");
        
        switch (choice) {
            case 1:
                handleAddFlight();
                break;
            case 2:
                handleViewAllFlights();
                break;
            case 3:
                handleUpdateFlightStatus();
                break;
            case 4:
                handleDeleteFlight();
                break;
            case 5:
                return;
            default:
                ConsoleUtil.printError("Invalid choice.");
        }
    }
    
    private void handleAddFlight() {
        ConsoleUtil.printHeader("ADD NEW FLIGHT");
        
        try {
            String flightNumber = ConsoleUtil.readString("Flight Number (e.g., AI101): ").toUpperCase();
            String airline = ConsoleUtil.readString("Airline Name: ");
            String source = ConsoleUtil.readString("Source City: ");
            String destination = ConsoleUtil.readString("Destination City: ");
            
            System.out.println("Enter departure date and time (dd-MM-yyyy HH:mm):");
            String departureStr = ConsoleUtil.readString("Departure: ");
            Date departureTime = DateUtil.parseDateTime(departureStr);
            
            System.out.println("Enter arrival date and time (dd-MM-yyyy HH:mm):");
            String arrivalStr = ConsoleUtil.readString("Arrival: ");
            Date arrivalTime = DateUtil.parseDateTime(arrivalStr);
            
            int totalSeats = ConsoleUtil.readInt("Total Seats: ");
            double pricePerSeat = ConsoleUtil.readDouble("Price per Seat: ");
            
            Flight flight = flightService.addFlight(flightNumber, airline, source, destination,
                    departureTime, arrivalTime, totalSeats, pricePerSeat);
            
            ConsoleUtil.printSuccess("Flight added successfully!");
            System.out.println(flight);
        } catch (ParseException e) {
            ConsoleUtil.printError("Invalid date format. Use dd-MM-yyyy HH:mm");
        } catch (Exception e) {
            ConsoleUtil.printError(e.getMessage());
        }
    }
    
    private void handleViewAllFlights() {
        ConsoleUtil.printHeader("ALL FLIGHTS");
        List<Flight> flights = flightService.getAllFlights();
        
        if (flights.isEmpty()) {
            ConsoleUtil.printInfo("No flights available.");
        } else {
            for (Flight flight : flights) {
                System.out.println(flight);
                System.out.println();
            }
        }
        ConsoleUtil.pause();
    }
    
    private void handleUpdateFlightStatus() {
        long flightId = ConsoleUtil.readLong("Enter Flight ID: ");
        
        System.out.println("\nSelect Status:");
        System.out.println("1. SCHEDULED");
        System.out.println("2. DELAYED");
        System.out.println("3. CANCELLED");
        System.out.println("4. DEPARTED");
        System.out.println("5. ARRIVED");
        
        int choice = ConsoleUtil.readInt("Enter choice: ");
        
        Flight.FlightStatus status = null;
        switch (choice) {
            case 1: status = Flight.FlightStatus.SCHEDULED; break;
            case 2: status = Flight.FlightStatus.DELAYED; break;
            case 3: status = Flight.FlightStatus.CANCELLED; break;
            case 4: status = Flight.FlightStatus.DEPARTED; break;
            case 5: status = Flight.FlightStatus.ARRIVED; break;
            default:
                ConsoleUtil.printError("Invalid choice.");
                return;
        }
        
        try {
            flightService.updateFlightStatus(flightId, status);
            ConsoleUtil.printSuccess("Flight status updated successfully!");
        } catch (Exception e) {
            ConsoleUtil.printError(e.getMessage());
        }
    }
    
    private void handleDeleteFlight() {
        long flightId = ConsoleUtil.readLong("Enter Flight ID to delete: ");
        boolean confirm = ConsoleUtil.readBoolean("Are you sure you want to delete this flight?");
        
        if (confirm) {
            flightService.deleteFlight(flightId);
            ConsoleUtil.printSuccess("Flight deleted successfully!");
        }
    }
    
    private void handleFlightSearch() {
        ConsoleUtil.printHeader("SEARCH FLIGHTS");
        
        try {
            String source = ConsoleUtil.readString("From (Source City): ");
            String destination = ConsoleUtil.readString("To (Destination City): ");
            String dateStr = ConsoleUtil.readString("Departure Date (dd-MM-yyyy): ");
            Date departureDate = DateUtil.parseDate(dateStr);
            
            List<Flight> flights = flightService.searchFlights(source, destination, departureDate);
            
            if (flights.isEmpty()) {
                ConsoleUtil.printInfo("No flights found for the given criteria.");
            } else {
                System.out.println("\n=== SEARCH RESULTS ===");
                for (Flight flight : flights) {
                    System.out.println(flight);
                    System.out.println();
                }
            }
        } catch (ParseException e) {
            ConsoleUtil.printError("Invalid date format. Use dd-MM-yyyy");
        } catch (Exception e) {
            ConsoleUtil.printError(e.getMessage());
        }
        ConsoleUtil.pause();
    }
    
    private void handleGuestFlightSearch() {
        handleFlightSearch();
    }
    
    // Booking handlers
    private void handleBookFlight() {
        ConsoleUtil.printHeader("BOOK FLIGHT");
        
        try {
            long flightId = ConsoleUtil.readLong("Enter Flight ID: ");
            Optional<Flight> flightOpt = flightService.getFlightById(flightId);
            
            if (!flightOpt.isPresent()) {
                ConsoleUtil.printError("Flight not found.");
                return;
            }
            
            Flight flight = flightOpt.get();
            System.out.println("\nFlight Details:");
            System.out.println(flight);
            
            int passengerCount = ConsoleUtil.readInt("\nNumber of Passengers: ");
            
            if (passengerCount > flight.getAvailableSeats()) {
                ConsoleUtil.printError("Not enough seats available.");
                return;
            }
            
            List<Passenger> passengers = new ArrayList<>();
            for (int i = 1; i <= passengerCount; i++) {
                System.out.println("\n--- Passenger " + i + " Details ---");
                String firstName = ConsoleUtil.readString("First Name: ");
                String lastName = ConsoleUtil.readString("Last Name: ");
                int age = ConsoleUtil.readInt("Age: ");
                String gender = ConsoleUtil.readString("Gender (M/F/Other): ");
                String idProof = ConsoleUtil.readString("ID Proof Type (Passport/Aadhaar/DL): ");
                String idNumber = ConsoleUtil.readString("ID Number: ");
                
                Passenger passenger = new Passenger((long) i, firstName, lastName, age, gender, idProof, idNumber);
                passengers.add(passenger);
            }
            
            String paymentMethod = ConsoleUtil.readString("\nPayment Method (Credit Card/Debit Card/UPI): ");
            
            double totalAmount = flight.getPricePerSeat() * passengerCount;
            System.out.println("\nTotal Amount: Rs" + totalAmount);
            
            boolean confirm = ConsoleUtil.readBoolean("Confirm booking?");
            
            if (confirm) {
                Booking booking = bookingService.createBooking(
                        userService.getCurrentUser().getUserId(),
                        flightId,
                        passengers,
                        paymentMethod
                );
                
                ConsoleUtil.printSuccess("Booking confirmed!");
                System.out.println("\n=== BOOKING DETAILS ===");
                System.out.println("Booking Reference: " + booking.getBookingReference());
                System.out.println("Booking ID: " + booking.getBookingId());
                System.out.println("Total Amount: Rs" + booking.getTotalAmount());
                System.out.println("Status: " + booking.getStatus());
                System.out.println("\nPlease save your booking reference for future use.");
            }
        } catch (Exception e) {
            ConsoleUtil.printError(e.getMessage());
        }
        ConsoleUtil.pause();
    }
    
    private void handleViewMyBookings() {
        ConsoleUtil.printHeader("MY BOOKINGS");
        
        List<Booking> bookings = bookingService.getUserBookings(userService.getCurrentUser().getUserId());
        
        if (bookings.isEmpty()) {
            ConsoleUtil.printInfo("You have no bookings yet.");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking);
                Optional<Flight> flightOpt = flightService.getFlightById(booking.getFlightId());
                if (flightOpt.isPresent()) {
                    System.out.println("  Flight: " + flightOpt.get().getFlightNumber() 
                            + " (" + flightOpt.get().getSource() + " -> " + flightOpt.get().getDestination() + ")");
                }
                System.out.println("  Passengers: " + booking.getPassengerCount());
                System.out.println();
            }
        }
        ConsoleUtil.pause();
    }
    
    private void handleCancelBooking() {
        ConsoleUtil.printHeader("CANCEL BOOKING");
        
        String reference = ConsoleUtil.readString("Enter Booking Reference: ");
        Optional<Booking> bookingOpt = bookingService.getBookingByReference(reference);
        
        if (!bookingOpt.isPresent()) {
            ConsoleUtil.printError("Booking not found.");
            return;
        }
        
        Booking booking = bookingOpt.get();
        
        if (!booking.getUserId().equals(userService.getCurrentUser().getUserId())) {
            ConsoleUtil.printError("This booking does not belong to you.");
            return;
        }
        
        System.out.println("\nBooking Details:");
        System.out.println(booking);
        
        boolean confirm = ConsoleUtil.readBoolean("\nAre you sure you want to cancel this booking?");
        
        if (confirm) {
            if (bookingService.cancelBooking(booking.getBookingId())) {
                ConsoleUtil.printSuccess("Booking cancelled successfully!");
            } else {
                ConsoleUtil.printError("Failed to cancel booking.");
            }
        }
        ConsoleUtil.pause();
    }
    
    private void handleViewAllBookings() {
        ConsoleUtil.printHeader("ALL BOOKINGS");
        
        List<Booking> bookings = bookingService.getAllBookings();
        
        if (bookings.isEmpty()) {
            ConsoleUtil.printInfo("No bookings found.");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking);
                System.out.println();
            }
        }
        ConsoleUtil.pause();
    }
    
    // Other handlers
    private void handleViewProfile() {
        ConsoleUtil.printHeader("MY PROFILE");
        User user = userService.getCurrentUser();
        System.out.println(user);
        ConsoleUtil.pause();
    }
    
    private void handleViewStatistics() {
        ConsoleUtil.printHeader("SYSTEM STATISTICS");
        
        System.out.println("Total Users: " + userService.getTotalUsers());
        System.out.println("Total Flights: " + flightService.getTotalFlights());
        System.out.println("Total Bookings: " + bookingService.getTotalBookings());
        System.out.println("Total Revenue: Rs" + String.format("%.2f", bookingService.getTotalRevenue()));
        System.out.println("Confirmed Bookings: " + bookingService.getConfirmedBookings().size());
        
        ConsoleUtil.pause();
    }
    
    private void handleViewAllUsers() {
        ConsoleUtil.printHeader("ALL USERS");
        
        List<User> users = userService.getAllUsers();
        
        if (users.isEmpty()) {
            ConsoleUtil.printInfo("No users found.");
        } else {
            for (User user : users) {
                System.out.println(user);
                System.out.println();
            }
        }
        ConsoleUtil.pause();
    }
}