import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseManagementUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Course Management");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            // Create components
            JPanel controlPanel = new JPanel();
            JButton addCourseButton = new JButton("Add Course");
            JButton viewCoursesButton = new JButton("View Courses");

            // Add components to control panel
            controlPanel.add(addCourseButton);
            controlPanel.add(viewCoursesButton);

            // Add control panel to the main panel
            mainPanel.add(controlPanel, BorderLayout.NORTH);

            JTextArea courseTextArea = new JTextArea();
            courseTextArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(courseTextArea);

            // Add text area with scroll pane to the main panel
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            // Add action listeners for buttons
            addCourseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle add course button action
                    String courseName = JOptionPane.showInputDialog(frame, "Enter Course Name:");
                    if (courseName != null && !courseName.trim().isEmpty()) {
                        courseTextArea.append("Course Name: " + courseName + "\n");
                    }
                }
            });

            viewCoursesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    JOptionPane.showMessageDialog(frame, "Viewing Courses...");
                }
            });

            // Set up the frame
            frame.add(mainPanel);
            frame.setLocationRelativeTo(null); // Center the frame
            frame.setVisible(true);
        });
    }
}
