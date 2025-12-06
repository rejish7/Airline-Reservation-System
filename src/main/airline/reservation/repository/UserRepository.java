package airline.reservation.repository;

import airline.reservation.entity.User;
import airline.reservation.entity.User.UserRole;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private Map<Long, User> users;
    private Map<String, User> usersByUsername;
    private Long currentId;
    
    public UserRepository() {
        this.users = new HashMap<>();
        this.usersByUsername = new HashMap<>();
        this.currentId = 1L;
        initializeDefaultUsers();
    }
    
    private void initializeDefaultUsers() {
        // Create default admin user
        User admin = new User(currentId++, "admin", "admin123", "admin@airline.com", 
                             "System Administrator", "1234567890", UserRole.ADMIN);
        save(admin);
    }
    
    public User save(User user) {
        if (user.getUserId() == null) {
            user.setUserId(currentId++);
        }
        users.put(user.getUserId(), user);
        usersByUsername.put(user.getUsername(), user);
        return user;
    }
    
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }
    
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(usersByUsername.get(username));
    }
    
    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
    
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
    
    public List<User> findByRole(UserRole role) {
        List<User> result = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getRole() == role) {
                result.add(user);
            }
        }
        return result;
    }
    
    public boolean existsByUsername(String username) {
        return usersByUsername.containsKey(username);
    }
    
    public boolean existsByEmail(String email) {
        return users.values().stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }
    
    public void delete(Long id) {
        User user = users.remove(id);
        if (user != null) {
            usersByUsername.remove(user.getUsername());
        }
    }
    
    public int count() {
        return users.size();
    }
}
