import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssessmentManagementUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Assessment Management");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            // Create components
            JPanel controlPanel = new JPanel();
            JButton addAssessmentButton = new JButton("Add Assessment");
            JButton updateAssessmentButton = new JButton("Update Assessment");
            JButton deleteAssessmentButton = new JButton("Delete Assessment");

            // Add components to control panel
            controlPanel.add(addAssessmentButton);
            controlPanel.add(updateAssessmentButton);
            controlPanel.add(deleteAssessmentButton);

            // Add control panel to the main panel
            mainPanel.add(controlPanel, BorderLayout.NORTH);

            JTextArea assessmentTextArea = new JTextArea();
            assessmentTextArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(assessmentTextArea);

            // Add text area with scroll pane to the main panel
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            // Add action listeners for buttons
            addAssessmentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle add assessment button action
                    String assessmentName = JOptionPane.showInputDialog(frame, "Enter Assessment Name:");
                    if (assessmentName != null && !assessmentName.trim().isEmpty()) {
                        assessmentTextArea.append("Assessment Name: " + assessmentName + "\n");
                    }
                }
            });

            updateAssessmentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle update assessment button action
                    // You can implement the logic to update assessments
                    JOptionPane.showMessageDialog(frame, "Updating Assessment...");
                }
            });

            deleteAssessmentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle delete assessment button action
                    // You can implement the logic to delete assessments
                    JOptionPane.showMessageDialog(frame, "Deleting Assessment...");
                }
            });

            // Set up the frame
            frame.add(mainPanel);
            frame.setLocationRelativeTo(null); // Center the frame
            frame.setVisible(true);
        });
    }
}
