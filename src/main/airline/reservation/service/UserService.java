package airline.reservation.service;

import airline.reservation.entity.User;
import airline.reservation.entity.User.UserRole;
import airline.reservation.repository.UserRepository;
import java.util.List;
import java.util.Optional;

public class UserService {
    private UserRepository userRepository;
    private User currentUser;
    
    public UserService() {
        this.userRepository = new UserRepository();
    }
    
    public User registerUser(String username, String password, String email, 
                            String fullName, String phoneNumber) {
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        
        // Check if username already exists
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        // Check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }
        
        // Create new user
        User user = new User(null, username, password, email, fullName, phoneNumber, UserRole.CUSTOMER);
        return userRepository.save(user);
    }
    
    public boolean login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == UserRole.ADMIN;
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public List<User> getCustomers() {
        return userRepository.findByRole(UserRole.CUSTOMER);
    }
    
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }
    
    public int getTotalUsers() {
        return userRepository.count();
    }
}
