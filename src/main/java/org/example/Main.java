package org.example;

import org.example.model.books.Author;
import org.example.model.books.Book;
import org.example.model.books.Category;
import org.example.service.BookService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Library Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Books", createBooksPanel());
        tabbedPane.addTab("Users", createUsersPanel());
        tabbedPane.addTab("History", createHistoryPanel());

        frame.add(tabbedPane);

        frame.setVisible(true);
    }

    private static JPanel createBooksPanel() {
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new BorderLayout());

        DefaultTableModel booksTableModel = new DefaultTableModel(new Object[]{"Isbn", "Title", "Year", "CopiesAvailable", "Authors", "Categories"}, 0);
        JTable booksTable = new JTable(booksTableModel);
        JScrollPane booksScrollPane = new JScrollPane(booksTable);
        booksPanel.add(booksScrollPane, BorderLayout.CENTER);

        List<Book> books = new BookService().getAllBooks();
        for (Book book : books) {
            StringBuilder authors = new StringBuilder();
            StringBuilder categories = new StringBuilder();

            for (Author author : book.getAuthors()) {
                authors.append(author.getName()).append(", ");
            }
            for (Category category : book.getCategories()) {
                categories.append(category.getName()).append(", ");
            }

            Object[] rowData = {book.getIsbn(), book.getTitle(), book.getYear(), book.getCopiesAvailable(), authors.toString(), categories.toString()};
            booksTableModel.addRow(rowData);
        }

        return booksPanel;
    }

    private static JPanel createUsersPanel() {
        JPanel usersPanel = new JPanel();
        usersPanel.setLayout(new BorderLayout());

        DefaultTableModel usersTableModel = new DefaultTableModel(new Object[]{"Idn", "First Name", "Last Name", "Contact Type", "Contact Value"}, 0);
        JTable usersTable = new JTable(usersTableModel);
        JScrollPane usersScrollPane = new JScrollPane(usersTable);
        usersPanel.add(usersScrollPane, BorderLayout.CENTER);

        return usersPanel;
    }

    private static JPanel createHistoryPanel() {
        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BorderLayout());

        DefaultTableModel historyTableModel = new DefaultTableModel(new Object[]{"User Idn", "User Name", "Book Isbn", "Book Title", "Borrow Date", "Return Date"}, 0);
        JTable historyTable = new JTable(historyTableModel);
        JScrollPane historyScrollPane = new JScrollPane(historyTable);
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);

        return historyPanel;
    }
}