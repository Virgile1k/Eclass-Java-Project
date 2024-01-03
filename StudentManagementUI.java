import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentManagementUI extends JFrame {
    private JTextField firstNameField, lastNameField, regNumberField, emailField;
    private JButton addButton, updateButton, deleteButton;
    private DefaultListModel<String> studentListModel;
    private JList<String> studentList;

    public StudentManagementUI() {
        setTitle("Student Management UI");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadData();
    }

    private void initComponents() {
        // Input fields
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        regNumberField = new JTextField(20);
        emailField = new JTextField(20);
        // Buttons
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        // List for displaying students
        studentListModel = new DefaultListModel<>();
        studentList = new JList<>(studentListModel);

        // Layout
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.add(new JLabel("First Name:"));
        inputPanel.add(firstNameField);
        inputPanel.add(new JLabel("Last Name:"));
        inputPanel.add(lastNameField);
        inputPanel.add(new JLabel("Reg Number:"));
        inputPanel.add(regNumberField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(studentList), BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        studentList.addListSelectionListener(e -> displaySelectedStudent());
    }

    private void loadData() {

        String url = "jdbc:mysql://localhost:3306/Brando_Db";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM student";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                studentListModel.clear();

                while (resultSet.next()) {
                    int studentId = resultSet.getInt("student_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String regNumber = resultSet.getString("registration_number");
                    String email = resultSet.getString("email_address");

                    String displayText = String.format("%s %s (%s)", firstName, lastName, regNumber);
                    studentListModel.addElement(displayText);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addStudent() {

        // Retrieve values from input fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String regNumber = regNumberField.getText();
        String email = emailField.getText();

        // Validate input (add your validation logic here)

        // Insert into the database
        String url = "jdbc:mysql://localhost:3306/Brando_Db";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO student (first_name, last_name, registration_number, email_address) VALUES (?, ?, ?, ?)";


            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, regNumber);
                preparedStatement.setString(4, email);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Student added successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadData(); // Refresh the student list
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add student.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding student to the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        // Placeholder for updating a student in the database
        // Retrieve values from input fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String regNumber = regNumberField.getText();
        String email = emailField.getText();

        // Validate input (add your validation logic here)

        // Update in the database
        String url = "jdbc:mysql://localhost:3306/Brando_Db";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE student SET first_name = ?, last_name = ?, email_address = ? WHERE registration_number = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, regNumber);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Student updated successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadData(); // Refresh the student list
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update student.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating student in the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        // Placeholder for deleting a student from the database
        int selectedIndex = studentList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedStudentInfo = studentListModel.getElementAt(selectedIndex);
        String[] parts = selectedStudentInfo.split("\\s+"); // Split by whitespace
        String regNumber = parts[parts.length - 1].replace("(", "").replace(")", ""); // Get the registration number

        // Delete from the database
        String url = "jdbc:mysql://localhost:3306/Brando_Db";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "DELETE FROM student WHERE registration_number = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, regNumber);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Student deleted successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadData(); // Refresh the student list
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete student.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting student from the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displaySelectedStudent() {
        // Display selected student's information in input fields for updating
        int selectedIndex = studentList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedStudentInfo = studentListModel.getElementAt(selectedIndex);
            String[] parts = selectedStudentInfo.split("\\s+"); // Split by whitespace
            String regNumber = parts[parts.length - 1].replace("(", "").replace(")", ""); // Get the registration number

        }
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        regNumberField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentManagementUI().setVisible(true);
            }
        });
    }
}
