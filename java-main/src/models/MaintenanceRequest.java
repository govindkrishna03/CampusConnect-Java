package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceRequest {
    private String building;
    private String classroom;
    private String problem;

    public MaintenanceRequest(String building, String classroom, String problem) {
        this.building = building;
        this.classroom = classroom;
        this.problem = problem;
    }

    // Getters and setters

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    // Method to submit maintenance request
    public void submitRequest() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maintenance", "root", "password")) {
            String sql = "INSERT INTO maintenance_requests (building, classroom, problem) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, building);
            statement.setString(2, classroom);
            statement.setString(3, problem);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Maintenance Request submitted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all maintenance requests
    public static List<MaintenanceRequest> getAllRequests() {
        List<MaintenanceRequest> requests = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maintanance", "root", "2003")) {
            String sql = "SELECT * FROM maintenance_requests";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String building = resultSet.getString("building");
                String classroom = resultSet.getString("classroom");
                String problem = resultSet.getString("problem");
                MaintenanceRequest request = new MaintenanceRequest(building, classroom, problem);
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
}
