
package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;   

public class StudentAccess extends JFrame {

    public StudentAccess() {
        setTitle("Swing Button Layout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton notificationbutton = createButton("Notifications", "");
        panel.add(notificationbutton, gbc);

        JButton events = createButton("Events", "viewEvents");
        panel.add(events, gbc);

        JButton ViewNotes = createButton("Notes", "ViewandDownloadNotes");
        panel.add(ViewNotes, gbc);

        add(panel);
    }

    private JButton createButton(String text, String actionCommand) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button click action
                System.out.println("Button clicked: " + text);
                // Corrected to match the button's text
                if ("Notifications".equalsIgnoreCase(text)) {
                    SwingUtilities.invokeLater(() -> {
                        JFrame repairFrame = new JFrame("Repair Panel");
                       
                        repairFrame.getContentPane().add(new NotificationPanel());
                        repairFrame.pack();
                        repairFrame.setLocationRelativeTo(null);
                        repairFrame.setVisible(true);
                    });
                    
                }
                if ("Events".equalsIgnoreCase(text)) {
                    SwingUtilities.invokeLater(() -> {
                        JFrame repairFrame = new JFrame("Events");
                       
                        repairFrame.getContentPane().add(new EventPanel());
                        repairFrame.pack();
                        repairFrame.setLocationRelativeTo(null);
                        repairFrame.setVisible(true);
                    });
                    
                }
                // if ("Notes".equalsIgnoreCase(text)) {
                //     SwingUtilities.invokeLater(() -> {
                //         JFrame repairFrame = new JFrame("Notes");
                       
                //         repairFrame.getContentPane().add(new DisplayNotes(null));
                //         repairFrame.pack();
                //         repairFrame.setLocationRelativeTo(null);
                //         repairFrame.setVisible(true);
                //     });
                    
                // }
                


            }
        });
        return button;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentAccess().setVisible(true);
                
            }
        });
        
    }
}