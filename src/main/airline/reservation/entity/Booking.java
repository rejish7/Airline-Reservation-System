package airline.reservation.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Booking {
    private Long bookingId;
    private String bookingReference;
    private Long userId;
    private Long flightId;
    private List<Passenger> passengers;
    private Date bookingDate;
    private double totalAmount;
    private BookingStatus status;
    private String paymentMethod;
    
    public enum BookingStatus {
        CONFIRMED, CANCELLED, PENDING, COMPLETED
    }
    
    public Booking() {
        this.passengers = new ArrayList<>();
        this.bookingDate = new Date();
        this.status = BookingStatus.PENDING;
    }
    
    public Booking(Long bookingId, String bookingReference, Long userId, Long flightId) {
        this.bookingId = bookingId;
        this.bookingReference = bookingReference;
        this.userId = userId;
        this.flightId = flightId;
        this.passengers = new ArrayList<>();
        this.bookingDate = new Date();
        this.status = BookingStatus.PENDING;
    }
    
    // Getters and Setters
    public Long getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    
    public String getBookingReference() {
        return bookingReference;
    }
    
    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getFlightId() {
        return flightId;
    }
    
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
    
    public List<Passenger> getPassengers() {
        return passengers;
    }
    
    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }
    
    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }
    
    public Date getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public BookingStatus getStatus() {
        return status;
    }
    
    public void setStatus(BookingStatus status) {
        this.status = status;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public int getPassengerCount() {
        return passengers.size();
    }
    
    @Override
    public String toString() {
        return String.format("Booking %s | Reference: %s | Flight ID: %d | Passengers: %d | Amount: Rs%.2f | Status: %s | Date: %s",
                bookingId, bookingReference, flightId, passengers.size(), totalAmount, status, bookingDate);
    }
}
