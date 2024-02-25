package gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class DisplayRequests extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public DisplayRequests() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Set background color

        // Create table with custom table model
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };
        table = new JTable(model);

        // Set table fonts and colors
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setSelectionBackground(new Color(102, 204, 255)); // Light blue selection color

        // Add columns to the table
        model.addColumn("ID");
        model.addColumn("Building");
        model.addColumn("Class");
        model.addColumn("Problem");
        model.addColumn("Status");

        // Set row height
        table.setRowHeight(30);

        // Add table to scroll pane with padding
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        add(scrollPane, BorderLayout.CENTER);

        // Create panel for buttons with padding
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding

        // Button to update status
        JButton updateButton = new JButton("Update Status");
        updateButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set button font
        updateButton.setBackground(new Color(51, 153, 255)); // Light blue background color
        updateButton.setForeground(Color.WHITE); // White text color
        updateButton.setFocusPainted(false); // Remove focus border
        updateButton.setPreferredSize(new Dimension(150, 40)); // Set preferred button size
        updateButton.addActionListener(e -> {
            // Get selected row index
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(DisplayRequests.this, "Please select a request to update status", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Show dialog with radio buttons for selecting status
            String[] options = {"Completed", "Not Completed"};
            int choice = JOptionPane.showOptionDialog(DisplayRequests.this,
                    "Select Status:", "Update Status",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);

            // Update the status based on the user's choice
            if (choice == 0) {
                model.setValueAt("Completed", selectedRow, 4);
            } else if (choice == 1) {
                model.setValueAt("Not Completed", selectedRow, 4);
            }
        });

        // Button to delete selected request
        JButton deleteButton = new JButton("Delete Request");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set button font
        deleteButton.setBackground(Color.RED); // Red background color
        deleteButton.setForeground(Color.WHITE); // White text color
        deleteButton.setFocusPainted(false); // Remove focus border
        deleteButton.setPreferredSize(new Dimension(150, 40)); // Set preferred button size
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(DisplayRequests.this, "Please select a request to delete", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            model.removeRow(selectedRow);
        });

        // Add buttons to button panel
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Retrieve data from the database and populate the table
        retrieveDataFromDatabase();
    }

    // Method to add a new request to the table
    public void addRequest(int id, String building, String classVal, String problem, String status) {
        Object[] rowData = {id, building, classVal, problem, status};
        model.addRow(rowData);
    }

    // Method to retrieve data from the database and populate the table
    private void retrieveDataFromDatabase() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maintanance", "root", "2003");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM reports");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String building = resultSet.getString("building");
                String classVal = resultSet.getString("class");
                String problem = resultSet.getString("problem");
                String status = resultSet.getString("status");
                addRequest(id, building, classVal, problem, status);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Maintenance Requests");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            DisplayRequests displayRequests = new DisplayRequests();
            frame.add(displayRequests);

            frame.setVisible(true);
        });
    }
}
