package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DisplayNotes extends JFrame {

    private JList<String> notesList;

    public DisplayNotes(ArrayList<String> notes) {
        setTitle("Uploaded Notes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Uploaded Notes:");
        panel.add(titleLabel, BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String note : notes) { // Iterate over strings directly
            listModel.addElement(note);
        }
        notesList = new JList<>(listModel);
        notesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        notesList.setVisibleRowCount(10);
        JScrollPane scrollPane = new JScrollPane(notesList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton openButton = new JButton("Open");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = notesList.getSelectedIndex();
                if (selectedIndex != -1) {
                    try {
                        // Open file action here
                        JOptionPane.showMessageDialog(DisplayNotes.this, "Opening file: " + notes.get(selectedIndex), "Open File", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(DisplayNotes.this, "Error opening file.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(DisplayNotes.this, "Please select a file to open.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(openButton);

        JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = notesList.getSelectedIndex();
                if (selectedIndex != -1) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Save As");
                    int userSelection = fileChooser.showSaveDialog(DisplayNotes.this);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        // Download file action here
                        JOptionPane.showMessageDialog(DisplayNotes.this, "Downloading file: " + notes.get(selectedIndex), "Download File", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(DisplayNotes.this, "Please select a file to download.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(downloadButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    public static void main(String[] args) {
        // Retrieve data from the database
        ArrayList<String> notes = new ArrayList<>();
        try {
            // Establish connection to the database
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/maintanance", 
                    "root", 
                    "2003" 
            );

            // Prepare SQL statement
            String sql = "SELECT * FROM files";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Execute query
            ResultSet resultSet = statement.executeQuery();

            // Process result set
            while (resultSet.next()) {
                String fileName = resultSet.getString("fileName");
                notes.add(fileName);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to retrieve notes from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Create and display the DisplayNotes frame
        SwingUtilities.invokeLater(() -> new DisplayNotes(notes).setVisible(true));
    }
}
