package org.example;

import org.example.model.books.Author;
import org.example.model.books.Book;
import org.example.model.books.BorrowHistory;
import org.example.model.books.Category;
import org.example.model.users.Contact;
import org.example.model.users.User;
import org.example.service.BookService;
import org.example.service.BorrowHistoryService;
import org.example.service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Main {
    private static BookService bookService;
    private static UserService userService;
    private static BorrowHistoryService borrowHistoryService;
    private static JFrame frame;
    private static JTabbedPane tabbedPane;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Library Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        bookService = new BookService();
        userService = new UserService();
        borrowHistoryService = new BorrowHistoryService();

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Books", createBooksPanel());
        tabbedPane.addTab("Users", createUsersPanel());
        tabbedPane.addTab("History", createHistoryPanel());

        frame.add(tabbedPane);

        frame.setVisible(true);
    }

    private static JPanel createBooksPanel() {
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new BorderLayout());

        DefaultTableModel booksTableModel = new DefaultTableModel(new Object[]{"ID", "Isbn", "Title", "Year", "CopiesAvailable", "Authors", "Categories"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable booksTable = new JTable(booksTableModel);
        JScrollPane booksScrollPane = new JScrollPane(booksTable);
        booksPanel.add(booksScrollPane, BorderLayout.CENTER);

        booksTable.getColumnModel().getColumn(0).setMinWidth(0);
        booksTable.getColumnModel().getColumn(0).setMaxWidth(0);
        booksTable.getColumnModel().getColumn(0).setWidth(0);

        booksTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = booksTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        Long bookId = (Long) booksTableModel.getValueAt(selectedRow, 0);
                        openBookTab(bookId);
                    }
                }
            }
        });

        List<Book> books = bookService.getAllBooks();
        for (Book book : books) {
            StringBuilder authors = new StringBuilder();
            StringBuilder categories = new StringBuilder();

            for (Author author : book.getAuthors()) {
                authors.append(author.getName()).append(", ");
            }
            for (Category category : book.getCategories()) {
                categories.append(category.getName()).append(", ");
            }

            Object[] rowData = {book.getId(), book.getIsbn(), book.getTitle(), book.getYear(), book.getCopiesAvailable(), authors.toString(), categories.toString()};
            booksTableModel.addRow(rowData);
        }

        return booksPanel;
    }

    private static JPanel createUsersPanel() {
        JPanel usersPanel = new JPanel();
        usersPanel.setLayout(new BorderLayout());

        DefaultTableModel usersTableModel = new DefaultTableModel(new Object[]{"ID", "Idn", "First Name", "Last Name", "Contacts"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable usersTable = new JTable(usersTableModel);
        JScrollPane usersScrollPane = new JScrollPane(usersTable);
        usersPanel.add(usersScrollPane, BorderLayout.CENTER);

        usersTable.getColumnModel().getColumn(0).setMinWidth(0);
        usersTable.getColumnModel().getColumn(0).setMaxWidth(0);
        usersTable.getColumnModel().getColumn(0).setWidth(0);

        usersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = usersTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        Long userId = (Long) usersTableModel.getValueAt(selectedRow, 0);
                        openUserTab(userId);
                    }
                }
            }
        });

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            StringBuilder contacts = new StringBuilder();

            for (Contact contact : user.getContacts()) {
                contacts.append(contact.getType()).append(":").append(contact.getValue()).append(", ");
            }
            Object[] rowData = {user.getId(), user.getIdn(), user.getFirstName(), user.getLastName(), contacts};
            usersTableModel.addRow(rowData);
        }

        return usersPanel;
    }

    private static JPanel createHistoryPanel() {
        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BorderLayout());

        DefaultTableModel historyTableModel = new DefaultTableModel(new Object[]{"ID", "User Idn", "User Name", "Book Isbn", "Book Title", "Borrow Date", "Return Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable historyTable = new JTable(historyTableModel);
        JScrollPane historyScrollPane = new JScrollPane(historyTable);
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);

        historyTable.getColumnModel().getColumn(0).setMinWidth(0);
        historyTable.getColumnModel().getColumn(0).setMaxWidth(0);
        historyTable.getColumnModel().getColumn(0).setWidth(0);

        historyTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = historyTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        Long borrowHistoryId = (Long) historyTableModel.getValueAt(selectedRow, 0);
                        openBorrowHistoryTab(borrowHistoryId);
                    }
                }
            }
        });

        List<BorrowHistory> borrowHistories = borrowHistoryService.getAllBorrowHistory();
        for (BorrowHistory borrowHistory : borrowHistories) {
            Object[] rowData = {borrowHistory.getId(), borrowHistory.getUser().getIdn(), borrowHistory.getUser().getFirstName() + " " + borrowHistory.getUser().getLastName(), borrowHistory.getBook().getIsbn(), borrowHistory.getBook().getTitle(), borrowHistory.getBorrowDate(), borrowHistory.getReturnDate()};
            historyTableModel.addRow(rowData);
        }

        return historyPanel;
    }

    private static void openBookTab(Long bookId) {
        Book book = bookService.getBookById(bookId);

        JDialog bookDialog = new JDialog(frame, "Edit Book", true);

        JTextField titleField = new JTextField(book.getTitle());
        JTextField isbnField = new JTextField(book.getIsbn());

        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");

        saveButton.addActionListener(e -> {
            book.setTitle(titleField.getText());
            book.setIsbn(isbnField.getText());
            bookService.updateBook(book);
            bookDialog.dispose();
        });

        deleteButton.addActionListener(e -> {
            bookService.deleteBook(book);
            bookDialog.dispose();
        });

        JPanel bookPanel = new JPanel(new GridLayout(3, 2));
        bookPanel.add(new JLabel("Title:"));
        bookPanel.add(titleField);
        bookPanel.add(new JLabel("ISBN:"));
        bookPanel.add(isbnField);

        bookPanel.add(saveButton);
        bookPanel.add(deleteButton);

        bookDialog.add(bookPanel);
        bookDialog.pack();
        bookDialog.setLocationRelativeTo(frame);
        bookDialog.setVisible(true);
    }

    private static void openUserTab(Long userId) {
        User user = userService.getUserByID(userId);
        System.out.println(user.getFirstName());
    }

    private static void openBorrowHistoryTab(Long borrowHistoryId) {
        BorrowHistory borrowHistory = borrowHistoryService.getBorrowHistoryById(borrowHistoryId);
        System.out.println(borrowHistory.getBorrowDate());
    }
}