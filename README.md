# Airline Reservation System

A comprehensive console-based airline reservation system built using pure Java and Object-Oriented Programming (OOP) principles.

## Project Overview

This is a Java-based airline reservation system that allows users to search for flights, make bookings, manage reservations, and provides administrative functions for flight management. The system is built without any framework dependencies, using only core Java features.

## Features

### Customer Features
- **User Registration & Login**: Secure user account creation and authentication
- **Flight Search**: Search for flights by source, destination, and date
- **Flight Booking**: Book flights with multiple passengers
- **Booking Management**: View and cancel bookings
- **User Profile**: View personal profile information

### Admin Features
- **Flight Management**: Add, update, and delete flights
- **Booking Overview**: View all bookings in the system
- **User Management**: View all registered users
- **System Statistics**: View total users, flights, bookings, and revenue
- **Flight Status Management**: Update flight status (Scheduled, Delayed, Cancelled, etc.)

## Project Structure

```
src/main/airline/reservation/
├── entity/                     # Entity/Model classes
│   ├── User.java              # User entity with roles (Admin/Customer)
│   ├── Flight.java            # Flight entity with details
│   ├── Booking.java           # Booking entity
│   └── Passenger.java         # Passenger entity
├── repository/                 # Data storage layer (in-memory)
│   ├── UserRepository.java    # User data management
│   ├── FlightRepository.java  # Flight data management
│   └── BookingRepository.java # Booking data management
├── service/                    # Business logic layer
│   ├── UserService.java       # User-related operations
│   ├── FlightService.java     # Flight-related operations
│   └── BookingService.java    # Booking-related operations
├── util/                       # Utility classes
│   ├── DateUtil.java          # Date formatting and parsing
│   ├── ValidationUtil.java    # Input validation
│   └── ConsoleUtil.java       # Console I/O utilities
└── AirlineReservationSystemApplication.java  # Main application

```

## Object-Oriented Design

### Key OOP Concepts Used:

1. **Encapsulation**: All entity classes have private fields with public getters/setters
2. **Inheritance**: User roles using enums, shared behaviors
3. **Polymorphism**: Service interfaces and implementations
4. **Abstraction**: Separation of concerns (Entity, Repository, Service layers)
5. **Composition**: Booking contains Flight and Passenger objects

### Design Patterns:
- **Repository Pattern**: Data access abstraction
- **Service Layer Pattern**: Business logic separation
- **Singleton Pattern**: Single scanner instance in ConsoleUtil

## How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command prompt or terminal

### Compilation
Navigate to the project directory and compile:
```cmd
javac airline/reservation/*.java airline/reservation/entity/*.java airline/reservation/repository/*.java airline/reservation/service/*.java airline/reservation/util/*.java
```

### Execution
```cmd
java airline.reservation.AirlineReservationSystemApplication
```

## Default Credentials

### Admin Account
- Username: `admin`
- Password: `admin123`

### Sample Flights
The system comes pre-loaded with 4 sample flights for testing.

## Usage Guide

### For Customers:
1. Register a new account
2. Login with your credentials
3. Search for flights by entering source, destination, and date
4. Book a flight by providing passenger details
5. View your bookings
6. Cancel bookings if needed

### For Admins:
1. Login with admin credentials
2. Manage flights (Add/Update/Delete)
3. View all bookings and users
4. Check system statistics

## Input Formats

- **Date**: `dd-MM-yyyy` (e.g., 25-12-2023)
- **Date-Time**: `dd-MM-yyyy HH:mm` (e.g., 25-12-2023 14:30)
- **Flight Number**: Format like AI101, 6E303 (2 letters + 3-4 digits)
- **Phone**: 10-digit number

## Features Details

### Booking System
- Automatic seat management
- Unique booking reference generation
- Payment method recording
- Multi-passenger support

### Flight Management
- Flight status tracking (Scheduled, Delayed, Cancelled, Departed, Arrived)
- Automatic seat availability updates
- Flight search by multiple criteria

### Data Persistence
- In-memory storage using HashMap
- All data is stored during runtime
- Data resets on application restart

## Future Enhancements
- Database integration for persistent storage
- Email notifications for bookings
- Payment gateway integration
- PDF ticket generation
- Flight pricing based on demand
- Seat selection feature
- Loyalty program

## Technologies Used
- **Language**: Java
- **Paradigm**: Object-Oriented Programming
- **Storage**: In-memory (HashMap)
- **I/O**: Console-based

## Author
BCA Third Semester Project

## License
Educational Project - Free to use and modify
---

**Note**: This is a console-based application built for educational purposes to demonstrate OOP concepts in Java without using any external frameworks.
