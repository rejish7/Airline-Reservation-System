package airline.reservation.service;

import airline.reservation.entity.Flight;
import airline.reservation.entity.Flight.FlightStatus;
import airline.reservation.repository.FlightRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class FlightService {
    private FlightRepository flightRepository;
    
    public FlightService() {
        this.flightRepository = new FlightRepository();
    }
    
    public Flight addFlight(String flightNumber, String airline, String source, String destination,
                           Date departureTime, Date arrivalTime, int totalSeats, double pricePerSeat) {
        // Validate input
        if (flightNumber == null || flightNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Flight number cannot be empty");
        }
        if (source == null || source.trim().isEmpty()) {
            throw new IllegalArgumentException("Source cannot be empty");
        }
        if (destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination cannot be empty");
        }
        if (departureTime == null || arrivalTime == null) {
            throw new IllegalArgumentException("Departure and arrival times cannot be null");
        }
        if (departureTime.after(arrivalTime)) {
            throw new IllegalArgumentException("Departure time must be before arrival time");
        }
        if (totalSeats <= 0) {
            throw new IllegalArgumentException("Total seats must be positive");
        }
        if (pricePerSeat <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        
        // Check if flight number already exists
        Optional<Flight> existing = flightRepository.findByFlightNumber(flightNumber);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Flight number already exists");
        }
        
        Flight flight = new Flight(null, flightNumber, airline, source, destination,
                                  departureTime, arrivalTime, totalSeats, pricePerSeat);
        return flightRepository.save(flight);
    }
    
    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }
    
    public Optional<Flight> getFlightByNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }
    
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
    
    public List<Flight> searchFlights(String source, String destination, Date departureDate) {
        if (source == null || destination == null || departureDate == null) {
            throw new IllegalArgumentException("Search parameters cannot be null");
        }
        return flightRepository.searchFlights(source, destination, departureDate);
    }
    
    public List<Flight> getFlightsBySource(String source) {
        return flightRepository.findBySource(source);
    }
    
    public List<Flight> getFlightsByDestination(String destination) {
        return flightRepository.findByDestination(destination);
    }
    
    public List<Flight> getAvailableFlights() {
        return flightRepository.findByStatus(FlightStatus.SCHEDULED);
    }
    
    public Flight updateFlight(Flight flight) {
        if (flight.getFlightId() == null) {
            throw new IllegalArgumentException("Cannot update flight without ID");
        }
        return flightRepository.save(flight);
    }
    
    public void updateFlightStatus(Long flightId, FlightStatus status) {
        Optional<Flight> flightOpt = flightRepository.findById(flightId);
        if (flightOpt.isPresent()) {
            Flight flight = flightOpt.get();
            flight.setStatus(status);
            flightRepository.save(flight);
        } else {
            throw new IllegalArgumentException("Flight not found");
        }
    }
    
    public void deleteFlight(Long id) {
        flightRepository.delete(id);
    }
    
    public int getTotalFlights() {
        return flightRepository.count();
    }
    
    public boolean hasAvailableSeats(Long flightId, int requiredSeats) {
        Optional<Flight> flightOpt = flightRepository.findById(flightId);
        return flightOpt.isPresent() && flightOpt.get().getAvailableSeats() >= requiredSeats;
    }
}
