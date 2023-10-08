--liquibase formatted sql

--changeset gbabiuc:fill-books-table splitStatements: false
INSERT INTO books.books (isbn, title, year, copies_available)
VALUES ('9780451419439', 'To Kill a Mockingbird', 1960, 5),
       ('9780142424179', 'The Catcher in the Rye', 1951, 3),
       ('9780061120084', '1984', 1949, 7);

--changeset gbabiuc:fill-authors-table splitStatements: false
INSERT INTO books.authors (name)
VALUES ('Harper Lee'),
       ('J.D. Salinger'),
       ('George Orwell');

--changeset gbabiuc:fill-book_to_author-table splitStatements: false
INSERT INTO books.book_to_author (book_id, author_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);

--changeset gbabiuc:fill-categories-table splitStatements: false
INSERT INTO books.categories (name)
VALUES ('Fiction'),
       ('Dystopian'),
       ('Classic');

--changeset gbabiuc:fill-book_to_category-table splitStatements: false
INSERT INTO books.book_to_category (book_id, category_id)
VALUES (1, 1),
       (2, 1),
       (3, 2);

--changeset gbabiuc:fill-users-table splitStatements: false
INSERT INTO users.users (idn, first_name, last_name)
VALUES ('000000000001', 'John', 'Doe'),
       ('000000000002', 'Jane', 'Smith'),
       ('000000000003', 'Bob', 'Johnson');

--changeset gbabiuc:fill-contacts-table splitStatements: false
INSERT INTO users.contacts (type, value)
VALUES ('Email', 'john@example.com'),
       ('Phone', '555-123-4567'),
       ('Email', 'jane@example.com');

--changeset gbabiuc:fill-user_to_contact-table splitStatements: false
INSERT INTO users.user_to_contact (user_id, contact_id)
VALUES (1, 1),
       (1, 2),
       (2, 3);

--changeset gbabiuc:fill-borrow_history-table splitStatements: false
INSERT INTO books.borrow_history (user_id, book_id, borrow_date, return_date)
VALUES (1, 1, '2023-01-15', '2023-02-15'),
       (2, 2, '2023-02-10', '2023-03-10'),
       (3, 3, '2023-03-20', NULL);
