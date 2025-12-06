package airline.reservation.service;

import airline.reservation.entity.Booking;
import airline.reservation.entity.Booking.BookingStatus;
import airline.reservation.entity.Flight;
import airline.reservation.entity.Passenger;
import airline.reservation.repository.BookingRepository;
import airline.reservation.repository.FlightRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class BookingService {
    private BookingRepository bookingRepository;
    private FlightRepository flightRepository;
    private Random random;
    
    public BookingService(FlightRepository flightRepository) {
        this.bookingRepository = new BookingRepository();
        this.flightRepository = flightRepository;
        this.random = new Random();
    }
    
    public Booking createBooking(Long userId, Long flightId, List<Passenger> passengers, String paymentMethod) {
        // Validate input
        if (userId == null || flightId == null) {
            throw new IllegalArgumentException("User ID and Flight ID cannot be null");
        }
        if (passengers == null || passengers.isEmpty()) {
            throw new IllegalArgumentException("At least one passenger is required");
        }
        
        // Get flight
        Optional<Flight> flightOpt = flightRepository.findById(flightId);
        if (!flightOpt.isPresent()) {
            throw new IllegalArgumentException("Flight not found");
        }
        
        Flight flight = flightOpt.get();
        int passengerCount = passengers.size();
        
        // Check if seats are available
        if (!flight.bookSeats(passengerCount)) {
            throw new IllegalArgumentException("Not enough seats available");
        }
        
        // Create booking
        String bookingReference = generateBookingReference();
        Booking booking = new Booking(null, bookingReference, userId, flightId);
        booking.setPassengers(passengers);
        booking.setTotalAmount(flight.getPricePerSeat() * passengerCount);
        booking.setPaymentMethod(paymentMethod);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setBookingDate(new Date());
        
        // Save booking and update flight
        flightRepository.save(flight);
        return bookingRepository.save(booking);
    }
    
    public boolean cancelBooking(Long bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (!bookingOpt.isPresent()) {
            return false;
        }
        
        Booking booking = bookingOpt.get();
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return false;
        }
        
        // Release seats
        Optional<Flight> flightOpt = flightRepository.findById(booking.getFlightId());
        if (flightOpt.isPresent()) {
            Flight flight = flightOpt.get();
            flight.releaseSeats(booking.getPassengerCount());
            flightRepository.save(flight);
        }
        
        // Update booking status
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        return true;
    }
    
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }
    
    public Optional<Booking> getBookingByReference(String reference) {
        return bookingRepository.findByReference(reference);
    }
    
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    
    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
    
    public List<Booking> getFlightBookings(Long flightId) {
        return bookingRepository.findByFlightId(flightId);
    }
    
    public List<Booking> getConfirmedBookings() {
        return bookingRepository.findByStatus(BookingStatus.CONFIRMED);
    }
    
    public int getTotalBookings() {
        return bookingRepository.count();
    }
    
    public double getTotalRevenue() {
        return bookingRepository.findAll().stream()
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED || b.getStatus() == BookingStatus.COMPLETED)
                .mapToDouble(Booking::getTotalAmount)
                .sum();
    }
    
    private String generateBookingReference() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder reference = new StringBuilder("BK");
        for (int i = 0; i < 8; i++) {
            reference.append(chars.charAt(random.nextInt(chars.length())));
        }
        return reference.toString();
    }
}
