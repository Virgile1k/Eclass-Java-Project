import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StyledLoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public StyledLoginForm() {
        setTitle("Login ");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(27, 90, 139));

        // Create components
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(27, 90, 139));

        // Title
        JLabel titleLabel = new JLabel("Login to System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        // Username and Password Labels
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Username and Password Fields with rounded corners
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Set rounded corners for text fields
 

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(50, 120, 200));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Set layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        // Add components to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipady = 15;
        gbc.ipadx = 40;
        mainPanel.add(loginButton, gbc);

        // Add the main panel to the frame
        add(mainPanel);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        // Trigger login on Enter key press
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });

        // Set the default button for the frame (Enter key triggers login button)
        getRootPane().setDefaultButton(loginButton);
    }
 

    private void performLogin() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        String enteredPassword = new String(password);

        // Database connection parameters
        DatabaseConfig config = new DatabaseConfig();

        // For demonstration purposes, check if both fields are non-empty
        if (!username.isEmpty() && password.length > 0) {
            // Check the credentials against the database
            if (checkCredentials(username, enteredPassword, config)) {
                // Simulate successful login
                JOptionPane.showMessageDialog(this,
                        "Login Successful!\nWelcome, " + username + "!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                // Example: Open the System Dashboard after successful login
                openSystemDashboard();
            } else {
                // Handle invalid login
                JOptionPane.showMessageDialog(this,
                        "Invalid login. Please enter valid credentials.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Handle invalid input
            JOptionPane.showMessageDialog(this,
                    "Please enter both username and password.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean checkCredentials(String username, String password, DatabaseConfig config) {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");


            // Establish the database connection
            try (Connection connection = DriverManager.getConnection(config.getJdbcUrl(), config.getUsername(), config.getPassword())) {

                // Prepare the SQL query
                String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    statement.setString(2, password);

                    // Execute the query
                    ResultSet resultSet = statement.executeQuery();

                    // Check if the user exists in the database
                    return resultSet.next();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void openSystemDashboard() {
        // Launch the System Dashboard after a successful login
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SystemDashboard().setVisible(true);
                // Close the Login and Authentication UI
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StyledLoginForm().setVisible(true);
            }
        });
    }
}

class DatabaseConfig {
    private final String url = "jdbc:mysql://localhost:3307/Brando_Db?useSSL=false";

    private final String username = "vg";
    private final String password = ""; // Set your actual database password

    public String getJdbcUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
