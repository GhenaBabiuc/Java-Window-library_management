package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class Main {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/library_management";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "qwerty";

    private JFrame frame;
    private JTable bookTable;
    private DefaultTableModel tableModel;

    public Main() {
        frame = new JFrame("Список книг");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("№");
        tableModel.addColumn("Название");
        tableModel.addColumn("Автор");
        tableModel.addColumn("Год");
        tableModel.addColumn("Издатель");
        tableModel.addColumn("Жанр");
        tableModel.addColumn("Доступные копии");

        bookTable = new JTable(tableModel);
        bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(bookTable);

        JButton loadButton = new JButton("Загрузить список книг");
        loadButton.addActionListener(e -> loadBookList());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loadButton);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadBookList() {
        tableModel.setRowCount(0);

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books.books");

            int rowNumber = 1;
            while (resultSet.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rowNumber);
                row.add(resultSet.getString("title"));
                row.add(resultSet.getString("author"));
                row.add(resultSet.getInt("year"));
                row.add(resultSet.getString("publisher"));
                row.add(resultSet.getString("genre"));
                row.add(resultSet.getInt("copies_available"));

                tableModel.addRow(row);
                rowNumber++;
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Ошибка при загрузке списка книг.");
        }
    }

    public void display() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.display();
        });
    }
}