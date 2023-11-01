package org.example;

import org.example.model.books.Author;
import org.example.model.books.Book;
import org.example.model.books.Category;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class BookEditorForm extends JFrame {
    private Book book;
    private JTextField isbnField;
    private JTextField titleField;
    private JTextField yearField;
    private JTextField copiesAvailableField;
    private JCheckBox editableCheckBox;
    private JComboBox<Author> authorsComboBox;
    private JComboBox<Category> categoriesComboBox;

    public BookEditorForm(Book book, List<Author> allAuthors, List<Category> allCategories) {
        this.book = book;
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

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(book.getTitle());
        titleField.setEditable(false);
        formPanel.add(titleLabel);
        formPanel.add(titleField);

        JLabel yearLabel = new JLabel("Year:");
        yearField = new JTextField(book.getYear() != null ? book.getYear().toString() : "");
        yearField.setEditable(false);
        formPanel.add(yearLabel);
        formPanel.add(yearField);

        JLabel copiesAvailableLabel = new JLabel("Copies Available:");
        copiesAvailableField = new JTextField(book.getCopiesAvailable() != null ? book.getCopiesAvailable().toString() : "");
        copiesAvailableField.setEditable(false);
        formPanel.add(copiesAvailableLabel);
        formPanel.add(copiesAvailableField);

        JLabel authorsLabel = new JLabel("Authors:");
        authorsComboBox = new JComboBox<>(allAuthors.toArray(new Author[0]));
        authorsComboBox.setSelectedItem(book.getAuthors());
        formPanel.add(authorsLabel);
        formPanel.add(authorsComboBox);

        JLabel categoriesLabel = new JLabel("Categories:");
        categoriesComboBox = new JComboBox<>(allCategories.toArray(new Category[0]));
        categoriesComboBox.setSelectedItem(book.getCategories());
        formPanel.add(categoriesLabel);
        formPanel.add(categoriesComboBox);

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
            book.setIsbn(isbnField.getText());
            book.setTitle(titleField.getText());
            if (!yearField.getText().isEmpty()) {
                book.setYear(Long.parseLong(yearField.getText()));
            } else {
                book.setYear(null);
            }
            if (!copiesAvailableField.getText().isEmpty()) {
                book.setCopiesAvailable(Long.parseLong(copiesAvailableField.getText()));
            } else {
                book.setCopiesAvailable(null);
            }

            Author selectedAuthor = (Author) authorsComboBox.getSelectedItem();
            Category selectedCategory = (Category) categoriesComboBox.getSelectedItem();

            // Add the selected author and category to the book's authors and categories
            Set<Author> selectedAuthors = book.getAuthors();
            selectedAuthors.add(selectedAuthor);
            book.setAuthors(selectedAuthors);

            Set<Category> selectedCategories = book.getCategories();
            selectedCategories.add(selectedCategory);
            book.setCategories(selectedCategories);

            dispose();
        });

        add(formPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
    }
}