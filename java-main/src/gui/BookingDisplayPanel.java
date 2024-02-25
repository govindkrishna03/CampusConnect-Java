package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingDisplayPanel extends JPanel {
    private JTable table;

    public BookingDisplayPanel() {
        setLayout(new BorderLayout());

        // Create table model with column names
        String[] columns = {"ID", "Class Room", "Batch", "Date", "Time Slots", "Teacher"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Load data from database
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/maintanance";
        String username = "root";
        String password = "2003";

        try {
            // Establish connection to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare SQL statement
            String sql = "SELECT id, class_room, batch, date, time_slots, teacher FROM bookings";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Execute the SQL query
            ResultSet resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                int id = resultSet.getInt("id");
                String classRoom = resultSet.getString("class_room");
                String batch = resultSet.getString("batch");
                String date = resultSet.getString("date");
                String timeSlots = resultSet.getString("time_slots");
                String teacher = resultSet.getString("teacher");

                // Add data to the table model
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{id, classRoom, batch, date, timeSlots, teacher});
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch data from the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Booking Display");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);

            BookingDisplayPanel displayPanel = new BookingDisplayPanel();
            frame.add(displayPanel);

            frame.setVisible(true);
        });
    }
}
