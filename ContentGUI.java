import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.SQLException;
import java.util.Vector;

public class ContentGUI extends JFrame {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Brando_Db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private JTable table;

    public ContentGUI() {
        setTitle("Content Table Viewer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // Buttons for CRUD operations
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Connect to the database and load data into the table
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            String sql = "SELECT * FROM content";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Populate the table model with data from the result set
            table.setModel(buildTableModel(resultSet));

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContent();
                refreshTable();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContent();
                refreshTable();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContent();
                refreshTable();
            }
        });
    }

    // Helper method to convert ResultSet to TableModel
    private static DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();

        // Column names
        int columnCount = metaData.getColumnCount();
        Vector<String> columnNames = new Vector<>();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // Data
        Vector<Vector<Object>> data = new Vector<>();
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(resultSet.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }

    // Method to refresh the table data
    private void refreshTable() {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            String sql = "SELECT * FROM content";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Populate the table model with data from the result set
            table.setModel(buildTableModel(resultSet));

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Implement methods for CRUD operations
    // Example methods:

    private void addContent() {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            String sql = "INSERT INTO content (column1, column2, column3) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "NewValue1");
            preparedStatement.setString(2, "NewValue2");
            preparedStatement.setString(3, "NewValue3");

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteContent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                String sql = "DELETE FROM content WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, (int) table.getValueAt(selectedRow, 0));

                preparedStatement.executeUpdate();

                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateContent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                String sql = "UPDATE content SET column1=?, column2=?, column3=? WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "UpdatedValue1");
                preparedStatement.setString(2, "UpdatedValue2");
                preparedStatement.setString(3, "UpdatedValue3");
                preparedStatement.setInt(4, (int) table.getValueAt(selectedRow, 0));

                preparedStatement.executeUpdate();

                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContentGUI contentGUI = new ContentGUI();
            contentGUI.setVisible(true);
        });
    }
}
