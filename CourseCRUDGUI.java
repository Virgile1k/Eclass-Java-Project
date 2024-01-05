import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CourseCRUDGUI extends JFrame {

    private JTextField courseIdField, courseTitleField, startDateField, endDateField, enrolledStudentField, materialsField;
    private JTextArea resultArea;
    

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Brando_Db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public CourseCRUDGUI() {
        super("Course CRUD Application");

        // Initialize the database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        courseIdField = new JTextField(10);
        courseTitleField = new JTextField(20);
        startDateField = new JTextField(10);
        endDateField = new JTextField(10);
        enrolledStudentField = new JTextField(15);
        materialsField = new JTextField(20);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        JButton createButton = new JButton("Create");
        JButton readButton = new JButton("Read");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createCourse();
            }
        });

        readButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                readCourse();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCourse();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteCourse();
            }
        });

        panel.add(new JLabel("Course ID:"));
        panel.add(courseIdField);
        panel.add(new JLabel("Course Title:"));
        panel.add(courseTitleField);
        panel.add(new JLabel("Start Date:"));
        panel.add(startDateField);
        panel.add(new JLabel("End Date:"));
        panel.add(endDateField);
        panel.add(new JLabel("Enrolled Student:"));
        panel.add(enrolledStudentField);
        panel.add(new JLabel("Course Materials:"));
        panel.add(materialsField);

        panel.add(createButton);
        panel.add(readButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        panel.add(new JLabel("Result:"));
        panel.add(new JScrollPane(resultArea));

        add(panel);
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    private void createCourse() {
        try (Connection connection = getConnection()) {
            int courseId = Integer.parseInt(courseIdField.getText());
            String courseTitle = courseTitleField.getText();
            String startDate = startDateField.getText();
            String endDate = endDateField.getText();
            String enrolledStudent = enrolledStudentField.getText();
            String materials = materialsField.getText();

            String query = "INSERT INTO course (course_id, course_title, start_date, end_date, enrolled_student, course_materials) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, courseId);
                preparedStatement.setString(2, courseTitle);
                preparedStatement.setString(3, startDate);
                preparedStatement.setString(4, endDate);
                preparedStatement.setString(5, enrolledStudent);
                preparedStatement.setString(6, materials);
                preparedStatement.executeUpdate();
                resultArea.setText("Course created successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void displayAllCourses() {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM course";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Display courses in a table format
                StringBuilder table = new StringBuilder("Course ID\tCourse Title\tStart Date\tEnd Date\tEnrolled Student\tCourse Materials\n");
                while (resultSet.next()) {
                    table.append(resultSet.getInt("course_id")).append("\t")
                         .append(resultSet.getString("course_title")).append("\t")
                         .append(resultSet.getString("start_date")).append("\t")
                         .append(resultSet.getString("end_date")).append("\t")
                         .append(resultSet.getString("enrolled_student")).append("\t")
                         .append(resultSet.getString("course_materials")).append("\n");
                }
                resultArea.setText(table.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void readCourse() {
        try (Connection connection = getConnection()) {
            int courseId = Integer.parseInt(courseIdField.getText());
            String query = "SELECT * FROM course WHERE course_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, courseId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        resultArea.setText("Course ID: " + resultSet.getInt("course_id") + "\n"
                                + "Course Title: " + resultSet.getString("course_title") + "\n"
                                + "Start Date: " + resultSet.getString("start_date") + "\n"
                                + "End Date: " + resultSet.getString("end_date") + "\n"
                                + "Enrolled Student: " + resultSet.getString("enrolled_student") + "\n"
                                + "Course Materials: " + resultSet.getString("course_materials"));
                    } else {
                        resultArea.setText("Course not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCourse() {
        try (Connection connection = getConnection()) {
            int courseId = Integer.parseInt(courseIdField.getText());
            String newCourseTitle = courseTitleField.getText();
            String newStartDate = startDateField.getText();
            String newEndDate = endDateField.getText();
            String newEnrolledStudent = enrolledStudentField.getText();
            String newMaterials = materialsField.getText();

            String query = "UPDATE course SET course_title=?, start_date=?, end_date=?, enrolled_student=?, course_materials=? WHERE course_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, newCourseTitle);
                preparedStatement.setString(2, newStartDate);
                preparedStatement.setString(3, newEndDate);
                preparedStatement.setString(4, newEnrolledStudent);
                preparedStatement.setString(5, newMaterials);
                preparedStatement.setInt(6, courseId);
                preparedStatement.executeUpdate();
                resultArea.setText("Course updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCourse() {
        try (Connection connection = getConnection()) {
            int courseId = Integer.parseInt(courseIdField.getText());
            String query = "DELETE FROM course WHERE course_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, courseId);
                preparedStatement.executeUpdate();
                resultArea.setText("Course deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CourseCRUDGUI();
            }
        });
    }
}
