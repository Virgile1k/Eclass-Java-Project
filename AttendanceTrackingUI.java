
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
        setBackground(new Color(52, 101, 164));
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
        addAttendanceButton.setBackground(new Color(78, 154, 6));
        addAttendanceButton.setBounds(176, 47, 147, 25);

        // List for displaying attendance records
        attendanceListModel = new DefaultListModel<>();
        attendanceList = new JList<>(attendanceListModel);
        attendanceList.setBackground(new Color(52, 101, 164));

        // Layout
        getContentPane().setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBackground(new Color(52, 101, 164));
        JLabel label = new JLabel("Student:");
        label.setForeground(new Color(238, 238, 236));
        inputPanel.add(label);
        inputPanel.add(studentComboBox);
        JLabel label_1 = new JLabel("Date (YYYY-MM-DD):");
        label_1.setForeground(new Color(238, 238, 236));
        inputPanel.add(label_1);
        inputPanel.add(dateField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(52, 101, 164));
        buttonPanel.setLayout(null);
        buttonPanel.add(addAttendanceButton);

        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        getContentPane().add(new JScrollPane(attendanceList), BorderLayout.SOUTH);

        // Add action listeners
        addAttendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAttendance();
            }
        });
    }

    private void loadData() {

        String url = "jdbc:mysql://localhost:3307/Brando_Db";
        String user = "vg";
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
            JOptionPane.showMessageDialog(this, "Error loading data from the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAttendanceData() {

        String url = "jdbc:mysql://localhost:3307/Brando_Db";
        String user = "vg";
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
            JOptionPane.showMessageDialog(this, "Error loading attendance data from the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addAttendance() {
        String selectedStudentInfo = (String) studentComboBox.getSelectedItem();
        if (selectedStudentInfo == null) {
            JOptionPane.showMessageDialog(this, "Please select a student.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] parts = selectedStudentInfo.split("\\s+"); // Split by whitespace
        String regNumber = parts[parts.length - 1].replace("(", "").replace(")", ""); // Get the registration number

        String date = dateField.getText();

        String url = "jdbc:mysql://localhost:3307/Brando_Db";
        String user = "vg";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            int studentId = getStudentIdByRegNumber(connection, regNumber);

            if (studentId != -1) {
                // Insert the attendance record without specifying 'attendance-id'
                String insertQuery = "INSERT INTO attendance (student_id, course_id, date) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setInt(1, studentId);
                    // Assuming a default course ID for simplicity; you may replace it with a proper
                    // course selection logic
                    preparedStatement.setInt(2, 1);
                    preparedStatement.setString(3, date);

                    int affectedRows = preparedStatement.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "Attendance added successfully.", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        loadAttendanceData(); // Refresh the attendance list
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add attendance.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Student not found in the database.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding attendance to the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
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

// vplanet check github