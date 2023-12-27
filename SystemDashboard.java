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
    }

    private void initComponents() {
        // Buttons for navigating to specific management UIs
        studentManagementButton = new JButton("Student Management");
        attendanceTrackingButton = new JButton("Attendance Tracking");

        // Layout
        setLayout(new GridLayout(2, 1, 10, 10));

        add(studentManagementButton);
        add(attendanceTrackingButton);

        // Add action listeners
        studentManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openStudentManagementUI();
            }
        });

        attendanceTrackingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAttendanceTrackingUI();
            }
        });
    }

    private void openStudentManagementUI() {
        // Launch the Student Management UI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentManagementUI().setVisible(true);
            }
        });
    }

    private void openAttendanceTrackingUI() {
        // Launch the Attendance Tracking UI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AttendanceTrackingUI().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SystemDashboard().setVisible(true);
            }
        });
    }
}
