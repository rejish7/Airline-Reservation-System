package airline.reservation.entity;

public class Passenger {
    private Long passengerId;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String idProof;
    private String idNumber;
    
    public Passenger() {}
    
    public Passenger(Long passengerId, String firstName, String lastName, 
                     int age, String gender, String idProof, String idNumber) {
        this.passengerId = passengerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.idProof = idProof;
        this.idNumber = idNumber;
    }
    
    // Getters and Setters
    public Long getPassengerId() {
        return passengerId;
    }
    
    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getIdProof() {
        return idProof;
    }
    
    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }
    
    public String getIdNumber() {
        return idNumber;
    }
    
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public String toString() {
        return String.format("%s %s (Age: %d, %s) - %s: %s", 
                firstName, lastName, age, gender, idProof, idNumber);
    }
}
