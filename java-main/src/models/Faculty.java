package models;
import java.util.ArrayList;
import java.util.List;


public class Faculty {
    private String name;
    private int id;
    private String department;
    private List<String> classesTaught;

    // Additional properties for authentication
    private String username;
    private String password;

    public Faculty(String name, int id, String department, String username, String password) {
        this.name = name;
        this.id = id;
        this.department = department;
        this.classesTaught = new ArrayList<>();
        this.username = username;
        this.password = password;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Method for authentication
    public boolean authenticate(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    // Method to request maintenance
    public void requestMaintenance() {
        // Implement request maintenance functionality
    }

    // Method to book class
    public void bookClass(String date, String startTime, String endTime, int interval, String building, String roomNumber) {
        // Implement booking class functionality
    }

    // Method to upload notes
    public void uploadNotes() {
        // Implement upload notes functionality
    }




    // Method to view events
    public void viewEvents() {
        // Implement view events functionality
    }

    // Method to search for students
    public void searchStudents(String query) {
        // Implement search functionality for students
    }

    // Method to add a class taught by the faculty
    public void addClassTaught(String className) {
        classesTaught.add(className);
    }

    // Method to remove a class taught by the faculty
    public void removeClassTaught(String className) {
        classesTaught.remove(className);
    }
}