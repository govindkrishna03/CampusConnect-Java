package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class NotesUploader extends JPanel {
    private JFrame frame;
    private DefaultListModel<String> fileListModel;
    private JList<String> fileList;

    public NotesUploader() {
        frame = new JFrame("File Uploader");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        JScrollPane scrollPane = new JScrollPane(fileList);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton uploadButton = new JButton("Upload");
        JButton viewButton = new JButton("View File");

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File[] selectedFiles = fileChooser.getSelectedFiles();
                    for (File file : selectedFiles) {
                        String fileName = file.getName();
                        fileListModel.addElement(fileName);
                        insertFileIntoDatabase(fileName, file);
                    }
                }
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to view the selected file
            }
        });

        buttonPanel.add(uploadButton);
        buttonPanel.add(viewButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void insertFileIntoDatabase(String fileName, File file) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maintanance", "root", "2003");
            String sql = "INSERT INTO files (fileName, filedata) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, fileName);
            FileInputStream fis = new FileInputStream(file);
            statement.setBinaryStream(2, fis, (int) file.length());

            // Execute SQL statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("File inserted successfully into database.");
            }

            // Close resources
            statement.close();
            fis.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to upload file: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NotesUploader();
            }
        });
    }
}
