import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemDashboard extends JFrame {
    private JButton studentManagementButton;
    private JButton attendanceTrackingButton;

    public SystemDashboard() {
        setTitle("System Dashboard");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();

        // Apply styling
        applyStyles();
    }

    private void initComponents() {
        // Buttons for navigating to specific management UIs
        studentManagementButton = new JButton("Student Management");
        attendanceTrackingButton = new JButton("Attendance Tracking");

        // Layout
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Adjust spacing as needed

        // Set consistent button size
        Dimension buttonSize = new Dimension(180, 50);
        studentManagementButton.setPreferredSize(buttonSize);
        attendanceTrackingButton.setPreferredSize(buttonSize);

        add(studentManagementButton);
        add(attendanceTrackingButton);

        // Add action listeners
        studentManagementButton.addActionListener(e -> openStudentManagementUI());
        attendanceTrackingButton.addActionListener(e -> openAttendanceTrackingUI());
    }

    private void openStudentManagementUI() {
        // Launch the Student Management UI
        SwingUtilities.invokeLater(() -> new StudentManagementUI().setVisible(true));
    }

    private void openAttendanceTrackingUI() {
        // Launch the Attendance Tracking UI
        SwingUtilities.invokeLater(() -> new AttendanceTrackingUI().setVisible(true));
    }

    private void applyStyles() {
        try {
            // Use UIManager to set styles and colors
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Example styles (customize as needed)
            UIManager.put("Button.background", new Color(65, 105, 225)); // Royal Blue
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.focus", new Color(135, 206, 250)); // Light Sky Blue
            UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 15, 5, 15));

            // Optionally, set background color of the JFrame
            getContentPane().setBackground(new Color(240, 248, 255)); // Alice Blue
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SystemDashboard().setVisible(true));
    }
}
