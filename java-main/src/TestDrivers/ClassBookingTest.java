package TestDrivers;
import javax.swing.*;

import gui.ClassBookingPanel;

public class ClassBookingTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Class Booking Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setLocationRelativeTo(null);

                ClassBookingPanel bookingPanel = new ClassBookingPanel();
                frame.add(bookingPanel);

                frame.setVisible(true);
            }
        });
    }
}
