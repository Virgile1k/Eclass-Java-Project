import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssessmentManagementUI {
    private static class Assessment {
        String assessmentTitle;
        String dueDate;
        int maximumScore;
        String gradingRubric;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Assessment Management");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            JPanel controlPanel = new JPanel();
            JButton addAssessmentButton = new JButton("Add Assessment");
            JButton updateAssessmentButton = new JButton("Update Assessment");
            JButton deleteAssessmentButton = new JButton("Delete Assessment");

            controlPanel.add(addAssessmentButton);
            controlPanel.add(updateAssessmentButton);
            controlPanel.add(deleteAssessmentButton);

            mainPanel.add(controlPanel, BorderLayout.NORTH);

            JTextArea assessmentTextArea = new JTextArea();
            assessmentTextArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(assessmentTextArea);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            addAssessmentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Assessment newAssessment = showAddAssessmentDialog(frame);
                    if (newAssessment != null) {
                        // Display or store the assessment details as needed
                        assessmentTextArea.append("Assessment Title: " + newAssessment.assessmentTitle + "\n");
                        assessmentTextArea.append("Due Date: " + newAssessment.dueDate + "\n");
                        assessmentTextArea.append("Maximum Score: " + newAssessment.maximumScore + "\n");
                        assessmentTextArea.append("Grading Rubric: " + newAssessment.gradingRubric + "\n");
                    }
                }
            });

            updateAssessmentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle update assessment button action
                    JOptionPane.showMessageDialog(frame, "Updating Assessment...");
                }
            });

            deleteAssessmentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle delete assessment button action
                    JOptionPane.showMessageDialog(frame, "Deleting Assessment...");
                }
            });

            frame.add(mainPanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static Assessment showAddAssessmentDialog(JFrame parent) {
        Assessment newAssessment = new Assessment();

        JTextField titleField = new JTextField();
        JTextField dueDateField = new JTextField();
        JTextField maxScoreField = new JTextField();
        JTextField rubricField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Assessment Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Due Date:"));
        panel.add(dueDateField);
        panel.add(new JLabel("Maximum Score:"));
        panel.add(maxScoreField);
        panel.add(new JLabel("Grading Rubric:"));
        panel.add(rubricField);

        int result = JOptionPane.showConfirmDialog(parent, panel, "Add Assessment", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            newAssessment.assessmentTitle = titleField.getText();
            newAssessment.dueDate = dueDateField.getText();
            try {
                newAssessment.maximumScore = Integer.parseInt(maxScoreField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent, "Invalid input for Maximum Score. Please enter a number.");
                return null;
            }
            newAssessment.gradingRubric = rubricField.getText();
            return newAssessment;
        }

        return null;
    }
}
