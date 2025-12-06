package airline.reservation.repository;

import airline.reservation.entity.Booking;
import airline.reservation.entity.Booking.BookingStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingRepository {
    private Map<Long, Booking> bookings;
    private Map<String, Booking> bookingsByReference;
    private Long currentId;
    
    public BookingRepository() {
        this.bookings = new HashMap<>();
        this.bookingsByReference = new HashMap<>();
        this.currentId = 1L;
    }
    
    public Booking save(Booking booking) {
        if (booking.getBookingId() == null) {
            booking.setBookingId(currentId++);
        }
        bookings.put(booking.getBookingId(), booking);
        bookingsByReference.put(booking.getBookingReference(), booking);
        return booking;
    }
    
    public Optional<Booking> findById(Long id) {
        return Optional.ofNullable(bookings.get(id));
    }
    
    public Optional<Booking> findByReference(String reference) {
        return Optional.ofNullable(bookingsByReference.get(reference));
    }
    
    public List<Booking> findAll() {
        return new ArrayList<>(bookings.values());
    }
    
    public List<Booking> findByUserId(Long userId) {
        return bookings.values().stream()
                .filter(b -> b.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
    
    public List<Booking> findByFlightId(Long flightId) {
        return bookings.values().stream()
                .filter(b -> b.getFlightId().equals(flightId))
                .collect(Collectors.toList());
    }
    
    public List<Booking> findByStatus(BookingStatus status) {
        return bookings.values().stream()
                .filter(b -> b.getStatus() == status)
                .collect(Collectors.toList());
    }
    
    public void delete(Long id) {
        Booking booking = bookings.remove(id);
        if (booking != null) {
            bookingsByReference.remove(booking.getBookingReference());
        }
    }
    
    public int count() {
        return bookings.size();
    }
}
