package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClassBookingPanel extends JPanel {
    public ClassBookingPanel() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Book Class/Lab");
        topPanel.add(titleLabel);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel classIdLabel = new JLabel("Class Room:");
        centerPanel.add(classIdLabel, gbc);

        gbc.gridx++;
        JTextField classIdField = new JTextField(10);
        centerPanel.add(classIdField, gbc);
        
        JLabel batchLabel = new JLabel("Batch:");
        gbc.gridx++;
        centerPanel.add(batchLabel, gbc);

        gbc.gridx++;
        JTextField batchField = new JTextField(10);
        centerPanel.add(batchField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel dateLabel = new JLabel("Date:");
        centerPanel.add(dateLabel, gbc);

        gbc.gridx++;
        JTextField dateField = new JTextField(10);
        centerPanel.add(dateField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel timeSlotLabel = new JLabel("Time Slot:");
        centerPanel.add(timeSlotLabel, gbc);

        gbc.gridx++;
        String[] timeSlots = {"8:00 AM - 10:00 AM", "10:00 AM - 12:00 PM", "1:00 PM - 3:00 PM", "3:00 PM - 5:00 PM"};
        JCheckBox[] timeSlotCheckBoxes = new JCheckBox[timeSlots.length];
        JPanel timeSlotPanel = new JPanel(new GridLayout(0, 1)); 
        for (int i = 0; i < timeSlots.length; i++) {
            timeSlotCheckBoxes[i] = new JCheckBox(timeSlots[i]);
            timeSlotPanel.add(timeSlotCheckBoxes[i]);
        }
        centerPanel.add(timeSlotPanel, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel teacherLabel = new JLabel("Teacher:");
        centerPanel.add(teacherLabel, gbc);

        gbc.gridx++;
        JTextField teacherField = new JTextField(10);
        centerPanel.add(teacherField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton bookButton = new JButton("Book");
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String classRoom = classIdField.getText();
                String batch = batchField.getText();
                String dateString = dateField.getText();
                StringBuilder selectedTimeSlots = new StringBuilder();
                for (JCheckBox checkBox : timeSlotCheckBoxes) {
                    if (checkBox.isSelected()) {
                        if (selectedTimeSlots.length() > 0) {
                            selectedTimeSlots.append(", ");
                        }
                        selectedTimeSlots.append(checkBox.getText());
                    }
                }
                String teacher = teacherField.getText();

                // Perform booking logic here
                // For example, display a message dialog
                String message = "Booking Details:\nClass Room: " + classRoom + "\nBatch: " + batch + "\nDate: " + dateString + "\nTime Slot(s): " + selectedTimeSlots.toString() + "\nTeacher: " + teacher;
                JOptionPane.showMessageDialog(ClassBookingPanel.this, message, "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);
            
                // Add booking details to database
                try {
                    // Establish connection to the database
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maintanance", "root", "2003");
            
                    // Prepare SQL statement
                    String sql = "INSERT INTO bookings (class_room, batch, date, time_slots, teacher) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, classRoom);
                    statement.setString(2, batch);
                    
                    // Convert string date to Date object and then to SQL date format
                    DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
                    Date parsedDate = dateFormat.parse(dateString);
                    java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                    
                    statement.setDate(3, sqlDate);
                    statement.setString(4, selectedTimeSlots.toString());
                    statement.setString(5, teacher);
            
                    // Execute the SQL statement
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("A new booking was inserted successfully.");
                    }
            
                    // Close resources
                    statement.close();
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ClassBookingPanel.this, "Failed to add booking to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(bookButton);
        
        JButton viewBookingsButton = new JButton("View Bookings");
        viewBookingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("View Bookings");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setLocationRelativeTo(null);

                BookingDisplayPanel bookingDisplayPanel = new BookingDisplayPanel();
                frame.add(bookingDisplayPanel);

                frame.setVisible(true);
            }
        });
        buttonPanel.add(viewBookingsButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Class Booking");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setLocationRelativeTo(null);

                ClassBookingPanel bookingPanel = new ClassBookingPanel();
                frame.add(bookingPanel);

                frame.setVisible(true);
            }
        });
    }
}