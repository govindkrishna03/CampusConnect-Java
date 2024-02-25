package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeComboBox;

    public LoginPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel logoLabel = new JLabel(new ImageIcon("/path/to/your/logo.png")); 
        add(logoLabel, gbc);

        gbc.gridy++;
        JLabel titleLabel = new JLabel("CampusConnect");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel, gbc);

        gbc.gridy++;
        usernameField = new JTextField(15);
        add(usernameField, gbc);

        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel, gbc);

        gbc.gridy++;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        gbc.gridy++;
        JLabel userTypeLabel = new JLabel("User Type:");
        add(userTypeLabel, gbc);

        gbc.gridy++;
        String[] userTypes = {"Student", "Faculty"};
        userTypeComboBox = new JComboBox<>(userTypes);
        add(userTypeComboBox, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 40)); 
        loginButton.setBackground(new Color(0, 102, 102)); 
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14)); 
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                String userType = (String) userTypeComboBox.getSelectedItem();

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maintanance",
                        "root", "2003");

                    PreparedStatement st = connection.prepareStatement("SELECT username, password FROM " + userType.toLowerCase() + " WHERE username=? AND password=?");

                    st.setString(1, username);
                    st.setString(2, password);
                    ResultSet rs = st.executeQuery();
                    if (rs.next()) {
                        dispose();
                        // Show the main application frame
                        if (userType.equals("Faculty")) {
                            SwingUtilities.invokeLater(() -> {
                                FacAccess facAccess = new FacAccess();
                                facAccess.setVisible(true);
                            });
                        } else {
                            SwingUtilities.invokeLater(() -> {
                                StudentAccess studentAccess = new StudentAccess();
                                studentAccess.setVisible(true);
                            });
                        }
                        JOptionPane.showMessageDialog(loginButton, "You have successfully logged in");
                    } else {
                        JOptionPane.showMessageDialog(loginButton, "Wrong Username & Password");
                    }
                } catch (SQLException | ClassNotFoundException sqlException) {
                    sqlException.printStackTrace();
                }
            }

            private void dispose() {
                // Close resources if necessary
            }
        });
        add(loginButton, gbc);


        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JLabel footerLabel = new JLabel("Copyright ©  CampusConnect. All rights reserved.");
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(footerLabel, gbc);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new LoginPanel());
        frame.pack();
        frame.setVisible(true);
    }
}
