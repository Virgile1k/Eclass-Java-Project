import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField phoneField;
    private JButton addUserButton;

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Brando_Db";
    private static final String DB_USER = "root"; // Replace with your root username
    private static final String DB_PASSWORD = ""; // Replace with your root password

    public AddUserUI() {
        setTitle("Add User");
        setSize(500, 300); // Increased the size of the form
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Username, Password, Email, and Phone fields
        usernameField = new JTextField(30); // Increased the size of the input fields
        passwordField = new JPasswordField(30);
        emailField = new JTextField(30);
        phoneField = new JTextField(30);

        // Add User button
        addUserButton = new JButton("Add User");

        // Layout
        setLayout(new GridLayout(5, 1, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addUserButton);

        add(inputPanel);
        add(buttonPanel);

        // Add action listener to the Add User button
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUserToDatabase();
            }
        });
    }

    private void addUserToDatabase() {
        // Get entered username, password, email, and phone
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        String enteredPassword = new String(password);
        String email = emailField.getText();
        String phone = phoneField.getText();

        // Database connection and insertion
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO users (username, password, email, phone) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, enteredPassword);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, phone);
                preparedStatement.executeUpdate();

                // Display success message
                JOptionPane.showMessageDialog(this,
                        "User Added!\nUsername: " + username + "\nEmail: " + email + "\nPhone: " + phone,
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear fields after adding user
                usernameField.setText("");
                passwordField.setText("");
                emailField.setText("");
                phoneField.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database-related exceptions
            JOptionPane.showMessageDialog(this,
                    "Error adding user. Please try again.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddUserUI().setVisible(true);
            }
        });
    }
}
