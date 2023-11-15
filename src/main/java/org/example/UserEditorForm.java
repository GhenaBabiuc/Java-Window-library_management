package org.example;

import org.example.filters.UserFilter;
import org.example.model.users.Contact;
import org.example.model.users.User;
import org.example.service.UserService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

public class UserEditorForm extends JFrame {
    private JTextField idnField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField contactValueField;
    private JCheckBox editableCheckBox;
    private JComboBox<String> contactsTypeComboBox;
    private boolean changesMade = false;

    public UserEditorForm(User user) {
        setTitle("User Editor");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel formPanel = new JPanel(new GridLayout(7, 2));

        JLabel idnLabel = new JLabel("Idn:");
        idnField = user.getIdn() == null ? new JTextField() : new JTextField(user.getIdn());
        formPanel.add(idnLabel);
        formPanel.add(idnField);
        idnField.getDocument().addDocumentListener(new DocumentListener() {
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
        idnField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) && idnField.getText().length() < 12)) {
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

        JLabel firstNameLabel = new JLabel("FirstName:");
        firstNameField = user.getFirstName() == null ? new JTextField() : new JTextField(user.getFirstName());
        formPanel.add(firstNameLabel);
        formPanel.add(firstNameField);
        firstNameField.getDocument().addDocumentListener(new DocumentListener() {
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
        firstNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_SPACE || c == KeyEvent.VK_DELETE) && firstNameField.getText().length() < 255)) {
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

        JLabel lastNameLabel = new JLabel("LastName:");
        lastNameField = user.getLastName() == null ? new JTextField() : new JTextField(user.getLastName());
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        lastNameField.getDocument().addDocumentListener(new DocumentListener() {
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
        lastNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_SPACE || c == KeyEvent.VK_DELETE) && lastNameField.getText().length() < 255)) {
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

        JLabel contactsTypeLabel = new JLabel("Contact Type:");
        contactsTypeComboBox = new JComboBox<>();
        contactsTypeComboBox.addItem("Email");
        contactsTypeComboBox.addItem("Phone");
        contactsTypeComboBox.setSelectedItem(user.getContacts() == null ? " " : user.getContacts().iterator().next().getType());
        formPanel.add(contactsTypeLabel);
        formPanel.add(contactsTypeComboBox);
        contactsTypeComboBox.addActionListener(e -> changesMade = true);

        JLabel contactValueLabel = new JLabel("Contact Value:");
        contactValueField = user.getContacts() == null ? new JTextField() : new JTextField(user.getContacts().iterator().next().getValue());
        formPanel.add(contactValueLabel);
        formPanel.add(contactValueField);
        contactValueField.getDocument().addDocumentListener(new DocumentListener() {
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
        contactValueField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '@' || c == '.' || c == '_' || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) && contactValueField.getText().length() < 255)) {
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

        if (user.getId() != null) {
            idnField.setEditable(false);
            firstNameField.setEditable(false);
            lastNameField.setEditable(false);
            contactsTypeComboBox.setEnabled(false);
            contactValueField.setEditable(false);

            JLabel editableLabel = new JLabel("Editable:");
            editableCheckBox = new JCheckBox();
            editableCheckBox.setSelected(false);
            editableCheckBox.addActionListener(e -> {
                boolean editable = editableCheckBox.isSelected();
                idnField.setEditable(editable);
                firstNameField.setEditable(editable);
                lastNameField.setEditable(editable);
                contactsTypeComboBox.setEnabled(editable);
                contactValueField.setEditable(editable);
            });
            formPanel.add(editableLabel);
            formPanel.add(editableCheckBox);
        }

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            if (changesMade) {
                int result = JOptionPane.showConfirmDialog(this, "Save changes to the user?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    if (!(idnField.getText().isEmpty() || firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || contactValueField.getText().isEmpty())) {
                        user.setIdn(idnField.getText());
                        user.setFirstName(firstNameField.getText());
                        user.setLastName(lastNameField.getText());
                        Contact contact;
                        if (user.getContacts() == null) {
                            contact = new Contact();
                        } else {
                            contact = user.getContacts().iterator().next();
                        }
                        contact.setType((String) contactsTypeComboBox.getSelectedItem());
                        if (((String) contactsTypeComboBox.getSelectedItem()).contentEquals("Email")) {
                            if (!contactValueField.getText().contains("@")) {
                                JOptionPane.showMessageDialog(this, "Email is not entered correctly .", "Info", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }
                        } else {
                            if (contactValueField.getText().contains("@")) {
                                JOptionPane.showMessageDialog(this, "Phone is not entered correctly .", "Info", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }
                        }
                        contact.setValue(contactValueField.getText());
                        Set<Contact> selectedContact = new HashSet<>();
                        selectedContact.add(contact);
                        user.setContacts(selectedContact);

                        UserService userService = new UserService();
                        if (user.getId() == null) {
                            if (userService.searchUsers(UserFilter.builder().idn(idnField.getText()).build()).isEmpty()) {
                                userService.updateUser(user);
                            } else {
                                JOptionPane.showMessageDialog(this, "User with that Idn already exists.", "Info", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }
                        } else {
                            userService.updateUser(user);
                        }

                        changesMade = false;

                        Runnable userUpdatePane = Main::userUpdatePane;
                        userUpdatePane.run();

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
        JPanel editPanel = new JPanel();
        editPanel.add(saveButton);

        if (user.getId() != null) {
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(e -> {
                new UserService().deleteUser(user);

                Runnable userUpdatePane = Main::userUpdatePane;
                userUpdatePane.run();

                JOptionPane.showMessageDialog(this, "Data has been successfully deleted", "Info", JOptionPane.INFORMATION_MESSAGE);
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            });

            editPanel.add(deleteButton);
        }

        add(formPanel, BorderLayout.CENTER);
        add(editPanel, BorderLayout.SOUTH);
    }
}