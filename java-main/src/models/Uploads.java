package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Uploads {
    
    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/maintanance";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "2003";
    
    // Method to upload file and store it in the database
    public static void uploadFile(File file) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO uploads (fileName, filedata) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, file.getName());
                pstmt.setBlob(2, new FileInputStream(file));
                pstmt.executeUpdate();
            }
            System.out.println("File uploaded successfully.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Error uploading file.");
        }
    }
    
    public static void main(String[] args) {
        // Example usage:
        File file = new File("example.txt"); // Change to the path of your file
        uploadFile(file);
    }
}
