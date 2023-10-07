--liquibase formatted sql

--changeset gbabiuc:fill-authors-table splitStatements: false
INSERT INTO books.authors (author_name)
VALUES ('George Orwell'),
       ('J.K. Rowling'),
       ('Jane Austen'),
       ('Mark Twain');

--changeset gbabiuc:fill-books-table splitStatements: false
INSERT INTO books.books (isbn, title, author, year, publisher, genre, copies_available)
VALUES ('9780451524935', '1984', 'George Orwell', 1949, 'Penguin Books', 'Dystopian', 10),
       ('9780439554930', 'Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', 1997, 'Scholastic', 'Fantasy', 15),
       ('9780141439518', 'Pride and Prejudice', 'Jane Austen', 1813, 'Penguin Classics', 'Classic', 7),
       ('9781615340042', 'The Adventures of Huckleberry Finn', 'Mark Twain', 1884, 'Vintage Classics', 'Adventure', 5);

--changeset gbabiuc:fill-books_authors-table splitStatements: false
INSERT INTO books.books_authors (book_id, author_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4);

--changeset gbabiuc:fill-users-table splitStatements: false
INSERT INTO users.users (first_name, last_name, email, phone, address)
VALUES ('John', 'Doe', 'johndoe@example.com', '+1234567890', '123 Main St, City'),
       ('Alice', 'Smith', 'alicesmith@example.com', '+9876543210', '456 Elm St, Town'),
       ('Bob', 'Johnson', 'bobjohnson@example.com', '+1112223333', '789 Oak St, Village');

--changeset gbabiuc:fill-borrow_history-table splitStatements: false
INSERT INTO users.borrow_history (user_id, book_id, borrow_date, return_date)
VALUES (1, 1, '2023-10-01', '2023-10-15'),
       (2, 2, '2023-09-20', '2023-10-05'),
       (3, 3, '2023-09-15', '2023-09-30'),
       (1, 4, '2023-09-10', NULL);
