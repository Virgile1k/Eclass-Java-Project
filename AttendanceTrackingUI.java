import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendanceTrackingUI extends JFrame {
    private JComboBox<String> studentComboBox;
    private JTextField dateField;
    private JButton addAttendanceButton;
    private DefaultListModel<String> attendanceListModel;
    private JList<String> attendanceList;

    public AttendanceTrackingUI() {
        setTitle("Attendance Tracking UI");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadData(); // Load initial data from the database
    }

    private void initComponents() {
        // Student combo box
        studentComboBox = new JComboBox<>();
        // Date input field
        dateField = new JTextField(15);
        // Add Attendance button
        addAttendanceButton = new JButton("Add Attendance");

        // List for displaying attendance records
        attendanceListModel = new DefaultListModel<>();
        attendanceList = new JList<>(attendanceListModel);

        // Layout
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.add(new JLabel("Student:"));
        inputPanel.add(studentComboBox);
        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        inputPanel.add(dateField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addAttendanceButton);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(attendanceList), BorderLayout.SOUTH);

        // Add action listeners
        addAttendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAttendance();
            }
        });
    }

    private void loadData() {
        // Load data from the database and populate the student combo box
        // This is a placeholder; replace it with your database connection code
        // You might want to use a separate class or method for database operations
        // For simplicity, I'm using a local database URL
        String url = "jdbc:mysql://localhost:3306/e_class_management_system";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM student";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                studentComboBox.removeAllItems();

                while (resultSet.next()) {
                    int studentId = resultSet.getInt("student_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String regNumber = resultSet.getString("registration_number");

                    String displayName = String.format("%s %s (%s)", firstName, lastName, regNumber);
                    studentComboBox.addItem(displayName);
                }
            }

            // Load initial attendance records
            loadAttendanceData();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAttendanceData() {
        // Load attendance data from the database and populate the attendance list
        // This is a placeholder; replace it with your database connection code
        // You might want to use a separate class or method for database operations
        // For simplicity, I'm using a local database URL
        String url = "jdbc:mysql://localhost:3306/e_class_management_system";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM attendance";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                attendanceListModel.clear();

                while (resultSet.next()) {
                    int attendanceId = resultSet.getInt("attendance-id");
                    int studentId = resultSet.getInt("student_id");
                    int courseId = resultSet.getInt("course_id");
                    String date = resultSet.getString("date");

                    String displayText = String.format("ID: %d | Student ID: %d | Course ID: %d | Date: %s",
                            attendanceId, studentId, courseId, date);
                    attendanceListModel.addElement(displayText);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading attendance data from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addAttendance() {
        // Implement adding attendance record to the database
        // This is a placeholder; replace it with your database connection code
        // You might want to use a separate class or method for database operations

        // Retrieve selected student's information from the combo box
        String selectedStudentInfo = (String) studentComboBox.getSelectedItem();
        if (selectedStudentInfo == null) {
            JOptionPane.showMessageDialog(this, "Please select a student.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] parts = selectedStudentInfo.split("\\s+"); // Split by whitespace
        String regNumber = parts[parts.length - 1].replace("(", "").replace(")", ""); // Get the registration number

        // Retrieve date from input field
        String date = dateField.getText();

        // Validate input (add your validation logic here)

        // Insert into the database
        String url = "jdbc:mysql://localhost:3306/e_class_management_system";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // First, retrieve the student ID based on the registration number
            int studentId = getStudentIdByRegNumber(connection, regNumber);

            if (studentId != -1) {
                // Insert the attendance record
                String insertQuery = "INSERT INTO attendance (student_id, course_id, date) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setInt(1, studentId);
                    // Assuming a default course ID for simplicity; you may replace it with a proper course selection logic
                    preparedStatement.setInt(2, 1);
                    preparedStatement.setString(3, date);

                    int affectedRows = preparedStatement.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Attendance added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadAttendanceData(); // Refresh the attendance list
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add attendance.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Student not found in the database.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding attendance to the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getStudentIdByRegNumber(Connection connection, String regNumber) throws SQLException {
        // Retrieve the student ID based on the registration number
        String query = "SELECT student_id FROM student WHERE registration_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, regNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("student_id");
                }
            }
        }
        return -1;
    }

    private void clearFields() {
        dateField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AttendanceTrackingUI().setVisible(true);
            }
        });
    }
}
