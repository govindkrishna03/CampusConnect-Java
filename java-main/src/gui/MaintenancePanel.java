package gui;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MaintenancePanel extends JPanel {

    public MaintenancePanel() {
        setLayout(new BorderLayout(0, 10));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel titleLabel = new JLabel("Report Repair");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(5, 10, 5, 15));
        add(inputPanel, BorderLayout.CENTER);

        JTextField buildingField = new JTextField();
        addPlaceholder(buildingField, "Select Building");
        inputPanel.add(buildingField);

        JTextField classField = new JTextField();
        addPlaceholder(classField, "Select Class");
        inputPanel.add(classField);

        JTextArea problemArea = new JTextArea();
        problemArea.setLineWrap(true);
        problemArea.setWrapStyleWord(true);
        addPlaceholder(problemArea, "Describe the Problem");
        JScrollPane scrollPane = new JScrollPane(problemArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        inputPanel.add(scrollPane);

        JButton reportButton = new JButton("Report");
        reportButton.setFont(new Font("Arial", Font.PLAIN, 16));
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String building = buildingField.getText();
                String classVal = classField.getText();
                String problem = problemArea.getText();

                // Send the report data to the MySQL server
                sendReportToServer(building, classVal, problem);
            }
        });
        inputPanel.add(reportButton);

        JButton viewRequestsButton = new JButton("View Maintenance Requests");
        viewRequestsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        viewRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the MaintenancePanel
                SwingUtilities.invokeLater(() -> {
                    JFrame repairFrame = new JFrame("Repair Panel");
                    repairFrame.getContentPane().add(new DisplayRequests());
                    repairFrame.pack();
                    repairFrame.setLocationRelativeTo(null);
                    repairFrame.setVisible(true);
                });
            }
        });
        inputPanel.add(viewRequestsButton);
    }

    private void sendReportToServer(String building, String classVal, String problem) {
        try {
            // Establish a connection to your MySQL database
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/maintenance", // Replace with your database URL
                    "root", // Replace with your database username
                    "password" // Replace with your database password
            );

            // Create a PreparedStatement to insert the report data into a table
            String sql = "INSERT INTO reports (building, class, problem) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, building);
            statement.setString(2, classVal);
            statement.setString(3, problem);

            // Execute the PreparedStatement
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Report submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit report!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Close the connection and statement
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to send report to server: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addPlaceholder(JTextComponent textComponent, String placeholder) {
        textComponent.setText(placeholder);
        textComponent.setForeground(Color.GRAY);
        textComponent.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textComponent.getText().equals(placeholder)) {
                    textComponent.setText("");
                    textComponent.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textComponent.getText().isEmpty()) {
                    textComponent.setText(placeholder);
                    textComponent.setForeground(Color.GRAY);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Repair Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new MaintenancePanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
