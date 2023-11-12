package org.example;

import com.toedter.calendar.JDateChooser;
import org.example.filters.BookFilter;
import org.example.filters.BorrowHistoryFilter;
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
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {
    private static BookService bookService;
    private static UserService userService;
    private static BorrowHistoryService borrowHistoryService;
    private static JFrame frame;
    private static JTabbedPane tabbedPane;
    private static BookEditorForm bookEditorForm;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Library Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);

        bookService = new BookService();
        userService = new UserService();
        borrowHistoryService = new BorrowHistoryService();

        tabbedPane = new JTabbedPane();

        addTabbedPanes();

        frame.add(tabbedPane);

        frame.setVisible(true);
    }

    public static void clearTabbedPanes() {
        tabbedPane.removeAll();
    }

    public static void addTabbedPanes() {
        tabbedPane.addTab("Books", createBooksPanel(bookService.searchBooks(new BookFilter())));
        tabbedPane.addTab("Users", createUsersPanel(userService.getAllUsers()));
        tabbedPane.addTab("History", createHistoryPanel(borrowHistoryService.searchBorrowHistory(new BorrowHistoryFilter())));
    }

    private static JPanel createBooksPanel(List<Book> books) {
        JPanel booksPanel = new JPanel();
        booksPanel.setLayout(new BorderLayout());

        JTextField isbnField = new JTextField(10);
        isbnField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) && isbnField.getText().length() < 13)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                    e.consume();
                }
            }
        });
        JTextField titleField = new JTextField(10);
        titleField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) && titleField.getText().length() < 255)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                    e.consume();
                }
            }
        });
        JTextField yearField = new JTextField(4);
        yearField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ((Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) && (yearField.getText().length() < 4)) {
                    try {
                        if (c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                            String text = yearField.getText() + c;
                            if (text.length() == 4) {
                                int year = Integer.parseInt(text);
                                int currentYear = LocalDate.now().getYear();
                                if (!(year >= 1 && year <= currentYear)) {
                                    JOptionPane.showMessageDialog(null, "Please enter a valid year (between 1 and " + currentYear + ").", "Invalid Year", JOptionPane.WARNING_MESSAGE);
                                    yearField.setText("");
                                    e.consume();
                                }
                            }
                        }
                    } catch (NumberFormatException exception) {
                        exception.printStackTrace();
                    }
                } else {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                    e.consume();
                }
            }
        });
        JTextField authorField = new JTextField(10);
        authorField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) && authorField.getText().length() < 255)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                    e.consume();
                }
            }
        });
        JTextField categoryField = new JTextField(10);
        categoryField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) && categoryField.getText().length() < 255)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                    e.consume();
                }
            }
        });

        JButton searchButton = new JButton("Search");
        JButton clearButton = new JButton("Clear");

        clearButton.addActionListener(e -> {
            clearTabbedPanes();
            addTabbedPanes();
        });

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("ISBN:"));
        searchPanel.add(isbnField);
        searchPanel.add(new JLabel("Title:"));
        searchPanel.add(titleField);
        searchPanel.add(new JLabel("Year:"));
        searchPanel.add(yearField);
        searchPanel.add(new JLabel("Author:"));
        searchPanel.add(authorField);
        searchPanel.add(new JLabel("Category:"));
        searchPanel.add(categoryField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        JButton addButton = new JButton("Add new book");
        addButton.addActionListener(e -> {
            addBookTab();
        });
        JPanel addPanel = new JPanel();
        addPanel.add(addButton);

        DefaultTableModel booksTableModel = new DefaultTableModel(new Object[]{"ID", "Isbn", "Title", "Year", "CopiesAvailable", "Authors", "Categories"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable booksTable = new JTable(booksTableModel);
        JScrollPane booksScrollPane = new JScrollPane(booksTable);
        booksPanel.add(searchPanel, BorderLayout.NORTH);
        booksPanel.add(addPanel, BorderLayout.SOUTH);
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

        for (Book book : books) {
            StringBuilder authors = new StringBuilder();
            StringBuilder categories = new StringBuilder();

            for (Author author : book.getAuthors()) {
                authors.append(author.getName());
                if (book.getAuthors().size() > 1) {
                    authors.append(", ");
                }
            }
            for (Category category : book.getCategories()) {
                categories.append(category.getName());
                if (book.getCategories().size() > 1) {
                    categories.append(", ");
                }
            }

            Object[] rowData = {book.getId(), book.getIsbn(), book.getTitle(), book.getYear(), book.getCopiesAvailable(), authors.toString(), categories.toString()};
            booksTableModel.addRow(rowData);
        }

        searchButton.addActionListener(e -> {
            String isbn = isbnField.getText();
            String title = titleField.getText();
            String year = yearField.getText();
            String author = authorField.getText();
            String category = categoryField.getText();

            List<Book> searchedBooks = bookService.searchBooks(BookFilter.builder()
                    .isbn(isbn)
                    .title(title)
                    .year(year)
                    .author(author)
                    .category(category)
                    .build());

            DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
            model.setRowCount(0);

            for (Book book : searchedBooks) {
                StringBuilder authors = new StringBuilder();
                StringBuilder categories = new StringBuilder();

                for (Author bookAuthor : book.getAuthors()) {
                    authors.append(bookAuthor.getName());
                    if (book.getAuthors().size() > 1) {
                        authors.append(", ");
                    }
                }

                for (Category bookCategory : book.getCategories()) {
                    categories.append(bookCategory.getName());
                    if (book.getCategories().size() > 1) {
                        categories.append(", ");
                    }
                }

                Object[] rowData = {book.getId(), book.getIsbn(), book.getTitle(), book.getYear(), book.getCopiesAvailable(), authors.toString(), categories.toString()};
                model.addRow(rowData);
            }
        });

        return booksPanel;
    }

    private static JPanel createUsersPanel(List<User> users) {
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

        for (User user : users) {
            StringBuilder contacts = new StringBuilder();

            for (Contact contact : user.getContacts()) {
                contacts.append(contact.getType()).append(":").append(contact.getValue());
                if (user.getContacts().size() > 1) {
                    contacts.append(", ");
                }
            }
            Object[] rowData = {user.getId(), user.getIdn(), user.getFirstName(), user.getLastName(), contacts};
            usersTableModel.addRow(rowData);
        }

        return usersPanel;
    }

    private static JPanel createHistoryPanel(List<BorrowHistory> borrowHistories) {
        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BorderLayout());

        JTextField userIdnField = new JTextField(10);
        userIdnField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) && userIdnField.getText().length() < 12)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                    e.consume();
                }
            }
        });

        JTextField usernameField = new JTextField(13);
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) && usernameField.getText().length() < 255)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                    e.consume();
                }
            }
        });

        JTextField bookIsbnField = new JTextField(13);
        bookIsbnField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) && bookIsbnField.getText().length() < 13)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                    e.consume();
                }
            }
        });

        JTextField bookTitleField = new JTextField(10);
        bookTitleField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) && bookTitleField.getText().length() < 255)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V) {
                    e.consume();
                }
            }
        });

        JButton searchButton = new JButton("Search");
        JButton clearButton = new JButton("Clear");

        clearButton.addActionListener(e -> {
            clearTabbedPanes();
            addTabbedPanes();
            tabbedPane.setSelectedIndex(2);
        });

        JDateChooser borrowDateChooser = new JDateChooser();
        JDateChooser returnDateChooser = new JDateChooser();

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("User Idn:"));
        searchPanel.add(userIdnField);
        searchPanel.add(new JLabel("User Name:"));
        searchPanel.add(usernameField);
        searchPanel.add(new JLabel("Book Isbn:"));
        searchPanel.add(bookIsbnField);
        searchPanel.add(new JLabel("Book Title:"));
        searchPanel.add(bookTitleField);
//        searchPanel.add(new JLabel("Borrow Date:"));
//        searchPanel.add(borrowDateChooser);
//        searchPanel.add(new JLabel("Return Date:"));
//        searchPanel.add(returnDateChooser);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        DefaultTableModel historyTableModel = new DefaultTableModel(new Object[]{"ID", "User Idn", "User Name", "Book Isbn", "Book Title", "Borrow Date", "Return Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable historyTable = new JTable(historyTableModel);
        JScrollPane historyScrollPane = new JScrollPane(historyTable);
        historyPanel.add(searchPanel, BorderLayout.NORTH);
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

        for (BorrowHistory borrowHistory : borrowHistories) {
            Object[] rowData = {borrowHistory.getId(), borrowHistory.getUser().getIdn(), borrowHistory.getUser().getFirstName() + " " + borrowHistory.getUser().getLastName(), borrowHistory.getBook().getIsbn(), borrowHistory.getBook().getTitle(), borrowHistory.getBorrowDate(), borrowHistory.getReturnDate()};
            historyTableModel.addRow(rowData);
        }

        searchButton.addActionListener(e -> {
            String username = usernameField.getText();
            String userIdn = userIdnField.getText();
            String bookTitle = bookTitleField.getText();
            String bookIsbn = bookIsbnField.getText();
            Date borrowDate = borrowDateChooser.getDate();
            Date returnDate = returnDateChooser.getDate();

            List<BorrowHistory> borrowHistoryList = borrowHistoryService.searchBorrowHistory(BorrowHistoryFilter.builder()
                    .username(username)
                    .userIdn(userIdn)
                    .bookTitle(bookTitle)
                    .bookIsbn(bookIsbn)
                    .borrowDate(borrowDate)
                    .returnDate(returnDate)
                    .build());

            DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
            model.setRowCount(0);

            for (BorrowHistory borrowHistory : borrowHistoryList) {
                Object[] rowData = {borrowHistory.getId(), borrowHistory.getUser().getIdn(), borrowHistory.getUser().getFirstName() + " " + borrowHistory.getUser().getLastName(), borrowHistory.getBook().getIsbn(), borrowHistory.getBook().getTitle(), borrowHistory.getBorrowDate(), borrowHistory.getReturnDate()};
                historyTableModel.addRow(rowData);
            }
        });

        return historyPanel;
    }

    private static void addBookTab() {
        if (bookEditorForm == null) {
            Book book = new Book();
            List<Category> categories = bookService.getAllCategories();
            List<Author> authors = bookService.getAllAuthors();
            bookEditorForm = new BookEditorForm(book, authors, categories);
            bookEditorForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    bookEditorForm = null;
                }
            });
            bookEditorForm.setVisible(true);
        } else {
            bookEditorForm.toFront();
        }
    }

    private static void openBookTab(Long bookId) {
        if (bookEditorForm == null) {
            Book book = bookService.getBookById(bookId);
            List<Category> categories = bookService.getAllCategories();
            List<Author> authors = bookService.getAllAuthors();
            bookEditorForm = new BookEditorForm(book, authors, categories);
            bookEditorForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    bookEditorForm = null;
                }
            });
            bookEditorForm.setVisible(true);
        } else {
            bookEditorForm.toFront();
        }
    }

    private static void openUserTab(Long userId) {
        User user = userService.getUserByID(userId);
        System.out.println(user.getFirstName());
    }

    private static void openBorrowHistoryTab(Long borrowHistoryId) {
        BorrowHistory borrowHistory = borrowHistoryService.getBorrowHistoryById(borrowHistoryId);
        if (borrowHistory.getReturnDate() == null) {
            borrowHistory.setReturnDate(Calendar.getInstance().getTime());

            int result = JOptionPane.showConfirmDialog(null, "Return the book?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                new BorrowHistoryService().updateBorrowHistory(borrowHistory);
                clearTabbedPanes();
                addTabbedPanes();
            }
        }
    }
}