package airline.reservation.entity;

import java.util.Date;

public class Flight {
    private Long flightId;
    private String flightNumber;
    private String airline;
    private String source;
    private String destination;
    private Date departureTime;
    private Date arrivalTime;
    private int totalSeats;
    private int availableSeats;
    private double pricePerSeat;
    private FlightStatus status;
    
    public enum FlightStatus {
        SCHEDULED, DELAYED, CANCELLED, DEPARTED, ARRIVED
    }
    
    public Flight() {
        this.status = FlightStatus.SCHEDULED;
    }
    
    public Flight(Long flightId, String flightNumber, String airline, String source,
                  String destination, Date departureTime, Date arrivalTime,
                  int totalSeats, double pricePerSeat) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.pricePerSeat = pricePerSeat;
        this.status = FlightStatus.SCHEDULED;
    }
    
    // Getters and Setters
    public Long getFlightId() {
        return flightId;
    }
    
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
    
    public String getFlightNumber() {
        return flightNumber;
    }
    
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    public String getAirline() {
        return airline;
    }
    
    public void setAirline(String airline) {
        this.airline = airline;
    }
    
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public Date getDepartureTime() {
        return departureTime;
    }
    
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }
    
    public Date getArrivalTime() {
        return arrivalTime;
    }
    
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    public int getTotalSeats() {
        return totalSeats;
    }
    
    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
    
    public int getAvailableSeats() {
        return availableSeats;
    }
    
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
    
    public double getPricePerSeat() {
        return pricePerSeat;
    }
    
    public void setPricePerSeat(double pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }
    
    public FlightStatus getStatus() {
        return status;
    }
    
    public void setStatus(FlightStatus status) {
        this.status = status;
    }
    
    public boolean bookSeats(int numberOfSeats) {
        if (availableSeats >= numberOfSeats && status == FlightStatus.SCHEDULED) {
            availableSeats -= numberOfSeats;
            return true;
        }
        return false;
    }
    
    public void releaseSeats(int numberOfSeats) {
        availableSeats += numberOfSeats;
        if (availableSeats > totalSeats) {
            availableSeats = totalSeats;
        }
    }
    
    @Override
    public String toString() {
        return String.format("Flight %s [%s]: %s -> %s | Departure: %s | Arrival: %s | Price: Rs%.2f | Available: %d/%d | Status: %s",
                flightNumber, airline, source, destination, departureTime, arrivalTime, 
                pricePerSeat, availableSeats, totalSeats, status);
    }
}
