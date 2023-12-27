import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton addUserButton;

    public AddUserUI() {
        setTitle("Add User");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Username and Password fields
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Add User button
        addUserButton = new JButton("Add User");

        // Layout
        setLayout(new GridLayout(3, 1, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addUserButton);

        add(inputPanel);
        add(buttonPanel);

        // Add action listener to the Add User button
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
    }

    private void addUser() {
        // Get entered username and password
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        String enteredPassword = new String(password);

        // For demonstration purposes, display the entered user information
        JOptionPane.showMessageDialog(this,
                "User Added!\nUsername: " + username,
                "Success", JOptionPane.INFORMATION_MESSAGE);

        // Clear fields after adding user
        usernameField.setText("");
        passwordField.setText("");
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
