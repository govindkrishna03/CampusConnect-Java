import models.MaintenanceRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // List to store maintenance requests
        List<MaintenanceRequest> maintenanceRequests = new ArrayList<>();

        try {
            // Establish connection to the database
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/maintanance", 
                    "root", 
                    "2003" 
            );

            // Prepare SQL statement
            String sql = "SELECT * FROM reports";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Execute query
            ResultSet resultSet = statement.executeQuery();

            // Process result set
            while (resultSet.next()) {
                String building = resultSet.getString("building");
                String classroom = resultSet.getString("class");
                String problem = resultSet.getString("problem");

                // Create MaintenanceRequest object
                MaintenanceRequest request = new MaintenanceRequest(building, classroom, problem);
                maintenanceRequests.add(request);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Print maintenance requests
        for (MaintenanceRequest request : maintenanceRequests) {
            System.out.println("Maintenance Request:");
            System.out.println("Building: " + request.getBuilding());
            System.out.println("Classroom: " + request.getClassroom());
            System.out.println("Problem: " + request.getProblem());
            System.out.println();
        }
    }
}
