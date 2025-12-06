package airline.reservation.repository;

import airline.reservation.entity.Flight;
import airline.reservation.entity.Flight.FlightStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlightRepository {
    private Map<Long, Flight> flights;
    private Map<String, Flight> flightsByNumber;
    private Long currentId;
    
    public FlightRepository() {
        this.flights = new HashMap<>();
        this.flightsByNumber = new HashMap<>();
        this.currentId = 1L;
        initializeSampleFlights();
    }
    
    private void initializeSampleFlights() {
        // Create some sample flights
        long oneDay = 24 * 60 * 60 * 1000;
        Date now = new Date();
        
        Flight f1 = new Flight(currentId++, "AI101", "Nepal Airline ", "Kathmandu", "Mumbai",
                new Date(now.getTime() + oneDay), new Date(now.getTime() + oneDay + 2 * 60 * 60 * 1000),
                180, 5500.0);
        save(f1);
        
        Flight f2 = new Flight(currentId++, "SG202", "Buddha Airline", "Kathmandu", "Bhadrapur",
                new Date(now.getTime() + 2 * oneDay), new Date(now.getTime() + 2 * oneDay + 90 * 60 * 1000),
                150, 4200.0);
        save(f2);
        
        Flight f3 = new Flight(currentId++, "6E303", "Shree airline", "Kathmandu", "Biratnagar",
                new Date(now.getTime() + 3 * oneDay), new Date(now.getTime() + 3 * oneDay + 3 * 60 * 60 * 1000),
                200, 6800.0);
        save(f3);
        
        Flight f4 = new Flight(currentId++, "UK404", "Yeti Airline", "Kathmandu", "Pokhara",
                new Date(now.getTime() + oneDay), new Date(now.getTime() + oneDay + 2 * 60 * 60 * 1000),
                160, 5200.0);
        save(f4);
    }
    
    public Flight save(Flight flight) {
        if (flight.getFlightId() == null) {
            flight.setFlightId(currentId++);
        }
        flights.put(flight.getFlightId(), flight);
        flightsByNumber.put(flight.getFlightNumber(), flight);
        return flight;
    }
    
    public Optional<Flight> findById(Long id) {
        return Optional.ofNullable(flights.get(id));
    }
    
    public Optional<Flight> findByFlightNumber(String flightNumber) {
        return Optional.ofNullable(flightsByNumber.get(flightNumber));
    }
    
    public List<Flight> findAll() {
        return new ArrayList<>(flights.values());
    }
    
    public List<Flight> searchFlights(String source, String destination, Date departureDate) {
        return flights.values().stream()
                .filter(f -> f.getSource().equalsIgnoreCase(source))
                .filter(f -> f.getDestination().equalsIgnoreCase(destination))
                .filter(f -> isSameDay(f.getDepartureTime(), departureDate))
                .filter(f -> f.getStatus() == FlightStatus.SCHEDULED)
                .filter(f -> f.getAvailableSeats() > 0)
                .collect(Collectors.toList());
    }
    
    public List<Flight> findBySource(String source) {
        return flights.values().stream()
                .filter(f -> f.getSource().equalsIgnoreCase(source))
                .collect(Collectors.toList());
    }
    
    public List<Flight> findByDestination(String destination) {
        return flights.values().stream()
                .filter(f -> f.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }
    
    public List<Flight> findByStatus(FlightStatus status) {
        return flights.values().stream()
                .filter(f -> f.getStatus() == status)
                .collect(Collectors.toList());
    }
    
    public void delete(Long id) {
        Flight flight = flights.remove(id);
        if (flight != null) {
            flightsByNumber.remove(flight.getFlightNumber());
        }
    }
    
    public int count() {
        return flights.size();
    }
    
    private boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        
        long diff = Math.abs(date1.getTime() - date2.getTime());
        return diff < 24 * 60 * 60 * 1000; // Within 24 hours
    }
}
