package org.example;

import org.example.model.books.Author;
import org.example.model.books.Book;
import org.example.model.books.Category;
import org.example.service.BookService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookEditorForm extends JFrame {
    private JTextField isbnField;
    private JTextField titleField;
    private JTextField yearField;
    private JTextField copiesAvailableField;
    private JCheckBox editableCheckBox;
    private JComboBox<Author> authorsComboBox;
    private JComboBox<Category> categoriesComboBox;
    private boolean changesMade = false;

    public BookEditorForm(Book book, List<Author> allAuthors, List<Category> allCategories) {
        setTitle("Book Editor");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel formPanel = new JPanel(new GridLayout(7, 2));

        JLabel isbnLabel = new JLabel("ISBN:");
        isbnField = new JTextField(book.getIsbn());
        isbnField.setEditable(false);
        formPanel.add(isbnLabel);
        formPanel.add(isbnField);
        isbnField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changesMade = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changesMade = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changesMade = true;
            }
        });
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

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(book.getTitle());
        titleField.setEditable(false);
        formPanel.add(titleLabel);
        formPanel.add(titleField);
        titleField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changesMade = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changesMade = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changesMade = true;
            }
        });
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

        JLabel yearLabel = new JLabel("Year:");
        yearField = new JTextField(book.getYear() != null ? book.getYear().toString() : "");
        yearField.setEditable(false);
        formPanel.add(yearLabel);
        formPanel.add(yearField);
        yearField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changesMade = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changesMade = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changesMade = true;
            }
        });
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

        JLabel copiesAvailableLabel = new JLabel("Copies Available:");
        copiesAvailableField = new JTextField(book.getCopiesAvailable() != null ? book.getCopiesAvailable().toString() : "");
        copiesAvailableField.setEditable(false);
        formPanel.add(copiesAvailableLabel);
        formPanel.add(copiesAvailableField);
        copiesAvailableField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changesMade = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changesMade = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changesMade = true;
            }
        });
        copiesAvailableField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) && copiesAvailableField.getText().length() < 7)) {
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

        JLabel authorsLabel = new JLabel("Authors:");
        authorsComboBox = new JComboBox<>(allAuthors.toArray(new Author[0]));
        authorsComboBox.setSelectedItem(book.getAuthors().iterator().next());
        authorsComboBox.setRenderer(new AuthorComboBoxRenderer());
        authorsComboBox.setEnabled(false);
        formPanel.add(authorsLabel);
        formPanel.add(authorsComboBox);
        authorsComboBox.addActionListener(e -> {
            changesMade = true;
        });

        JLabel categoriesLabel = new JLabel("Categories:");
        categoriesComboBox = new JComboBox<>(allCategories.toArray(new Category[0]));
        categoriesComboBox.setSelectedItem(book.getCategories().iterator().next());
        categoriesComboBox.setRenderer(new CategoryComboBoxRenderer());
        categoriesComboBox.setEnabled(false);
        formPanel.add(categoriesLabel);
        formPanel.add(categoriesComboBox);
        categoriesComboBox.addActionListener(e -> {
            changesMade = true;
        });

        JLabel editableLabel = new JLabel("Editable:");
        editableCheckBox = new JCheckBox();
        editableCheckBox.setSelected(false);
        editableCheckBox.addActionListener(e -> {
            boolean editable = editableCheckBox.isSelected();
            isbnField.setEditable(editable);
            titleField.setEditable(editable);
            yearField.setEditable(editable);
            copiesAvailableField.setEditable(editable);
            authorsComboBox.setEnabled(editable);
            categoriesComboBox.setEnabled(editable);
        });
        formPanel.add(editableLabel);
        formPanel.add(editableCheckBox);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            if (changesMade) {
                int result = JOptionPane.showConfirmDialog(this, "Save changes to the book?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    if (!(isbnField.getText().isEmpty() || titleField.getText().isEmpty() || yearField.getText().isEmpty() || copiesAvailableField.getText().isEmpty())) {
                        book.setIsbn(isbnField.getText());
                        book.setTitle(titleField.getText());
                        book.setYear(Long.parseLong(yearField.getText()));
                        book.setCopiesAvailable(Long.parseLong(copiesAvailableField.getText()));

                        Author selectedAuthor = (Author) authorsComboBox.getSelectedItem();
                        Category selectedCategory = (Category) categoriesComboBox.getSelectedItem();

                        Set<Author> selectedAuthors = new HashSet<>();
                        selectedAuthors.add(selectedAuthor);
                        book.setAuthors(selectedAuthors);

                        Set<Category> selectedCategories = new HashSet<>();
                        selectedCategories.add(selectedCategory);
                        book.setCategories(selectedCategories);
                        BookService bookService = new BookService();
                        bookService.updateBook(book);
                        changesMade = false;

                        Runnable clearTabbedPanes = Main::clearTabbedPanes;
                        Runnable addTabbedPanes = Main::addTabbedPanes;
                        clearTabbedPanes.run();
                        addTabbedPanes.run();

                        JOptionPane.showMessageDialog(this, "Data has been successfully saved", "Info", JOptionPane.INFORMATION_MESSAGE);
                        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                    } else {
                        JOptionPane.showMessageDialog(this, "You didn't fill in the blanks", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No changes have been made.", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        add(formPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
    }

    static class AuthorComboBoxRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Author) {
                value = ((Author) value).getName();
            }
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            return this;
        }
    }

    static class CategoryComboBoxRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Category) {
                value = ((Category) value).getName();
            }
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            return this;
        }
    }
}