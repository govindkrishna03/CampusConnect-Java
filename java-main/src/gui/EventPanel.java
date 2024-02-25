package gui;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventPanel extends JPanel {
    public EventPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Connect to the database and retrieve events
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/maintanance",
                    "root",
                    "2003"
            );

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM events");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String eventName = resultSet.getString("eventName");
                String date = resultSet.getString("date");
                String location = resultSet.getString("location");
                addEvent(eventName, date, location);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to retrieve events from the database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to add a new event box to the panel
    public void addEvent(String eventName, String date, String location) {
        JPanel eventBox = new JPanel(new BorderLayout());
        eventBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Add border
        eventBox.setBackground(new Color(41, 128, 185)); // Set background color
        eventBox.setOpaque(true); // Ensure background color is visible
        eventBox.setPreferredSize(new Dimension(300, 100)); // Set preferred size
        eventBox.setMaximumSize(new Dimension(300, 100)); // Set maximum size

        // Event name label
        JLabel nameLabel = new JLabel(eventName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setForeground(Color.WHITE); // Set text color
        eventBox.add(nameLabel, BorderLayout.NORTH);

        // Event details panel
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 5, 5)); // Single column layout for details
        detailsPanel.setBackground(new Color(41, 128, 185)); // Set background color

        // Date label
        JLabel dateLabel = new JLabel("Date: " + formatDate(date));
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setForeground(Color.WHITE); // Set text color
        detailsPanel.add(dateLabel);

        // Location label
        JLabel locationLabel = new JLabel("Location: " + location);
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        locationLabel.setForeground(Color.WHITE); // Set text color
        detailsPanel.add(locationLabel);

        eventBox.add(detailsPanel, BorderLayout.CENTER);

        // Add event box to the panel
        add(Box.createVerticalStrut(20)); // Add vertical space between boxes
        add(eventBox);
    }

    // Method to format date string
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inputFormat.parse(dateStr);
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
            return outputFormat.format(date);
        } catch (Exception e) {
            return dateStr; // Return original string if parsing fails
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("College Events");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null); // Center the frame on the screen

            EventPanel eventPanel = new EventPanel();
            frame.add(eventPanel);

            frame.setVisible(true);
        });
    }
}
