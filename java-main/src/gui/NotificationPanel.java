package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationPanel extends JPanel {

    public NotificationPanel() {
        initComponents();
        fetchNotificationsFromDB(); // Fetch notifications from the database
    }

    private void initComponents() {
        // Set layout
        setLayout(new BorderLayout());

        // Create title label
        JLabel titleLabel = new JLabel("Notifications/News");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create text area for notifications
        JTextArea notificationArea = new JTextArea();
        notificationArea.setEditable(false);
        notificationArea.setFont(new Font("Arial", Font.PLAIN, 14));
        notificationArea.setLineWrap(true);
        notificationArea.setWrapStyleWord(true);

        // Add scroll pane for notifications text area
        JScrollPane scrollPane = new JScrollPane(notificationArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Add components to panel
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Set panel properties
        setPreferredSize(new Dimension(600, 400));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void fetchNotificationsFromDB() {
        try {
            // Establish connection to the database
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/maintanance", 
                    "root", 
                    "2003" 
            );

            // Prepare SQL statement
            String sql = "SELECT * FROM events;";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Execute query
            ResultSet resultSet = statement.executeQuery();

            // Process result set
            StringBuilder notifications = new StringBuilder();
            while (resultSet.next()) {
                notifications.append(resultSet.getString("notification")).append("\n");
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();

            // Update notification area with fetched data
            JTextArea notificationArea = (JTextArea) ((JScrollPane) getComponent(1)).getViewport().getView();
            notificationArea.setText(notifications.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch notifications from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Notification Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new NotificationPanel());
            frame.pack();
            frame.setLocationRelativeTo(null); // Center the frame
            frame.setVisible(true);
        });
    }
}
