package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FacAccess extends JFrame {

    public FacAccess() {
        setTitle("Swing Button Layout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton maintenanceButton = createButton("Maintenance", "onMaintenanceButtonClick");
        panel.add(maintenanceButton, gbc);

        JButton bookClassButton = createButton("Book Class/Lab", "onBookClassButtonClick");
        panel.add(bookClassButton, gbc);

        JButton uploadFilesButton = createButton("Upload Files", "onUploadFilesButtonClick");
        panel.add(uploadFilesButton, gbc);

        add(panel);
    }

    private JButton createButton(String text, String actionCommand) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Maintenance".equalsIgnoreCase(text)) {
                    // Show the MaintenancePanel
                    SwingUtilities.invokeLater(() -> {
                        JFrame repairFrame = new JFrame("Repair Panel");
                        repairFrame.getContentPane().add(new MaintenancePanel());
                        repairFrame.pack();
                        repairFrame.setLocationRelativeTo(null);
                        repairFrame.setVisible(true);
                    });
                } else if ("Upload Files".equalsIgnoreCase(text)) {
                    // Show the NotesUploader
                    SwingUtilities.invokeLater(() -> {
                        JFrame uploaderFrame = new JFrame("Upload Files");
                        uploaderFrame.getContentPane().add(new NotesUploader());
                        uploaderFrame.pack();
                        uploaderFrame.setLocationRelativeTo(null);
                        uploaderFrame.setVisible(true);
                    });
                } else if ("Book Class/Lab".equalsIgnoreCase(text)) {
                    // Show the ClassBookingPanel
                    SwingUtilities.invokeLater(() -> {
                        JFrame frame = new JFrame(text);
                        frame.getContentPane().add(new ClassBookingPanel());
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    });
                }
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Perform user authentication
                if (authenticateUser()) {
                    // If authentication succeeds, show the FacAccess frame
                    new FacAccess().setVisible(true);
                } else {
                    // If authentication fails, display an error message and exit
                    JOptionPane.showMessageDialog(null, "Access denied. You are not authorized to access this page.", "Access Denied", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
        });
    }

    private static boolean authenticateUser() {

        boolean isAuthenticated = true;
        return isAuthenticated;
    }
}
