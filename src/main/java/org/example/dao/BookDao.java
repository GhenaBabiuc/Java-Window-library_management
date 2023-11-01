package org.example.dao;

import org.example.model.books.Author;
import org.example.model.books.Book;
import org.example.model.books.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BookDao {
    private final SessionFactory sessionFactory;

    public BookDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Book> getAllBooks() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT b FROM Book b " +
                    "JOIN FETCH b.authors " +
                    "JOIN FETCH b.categories ", Book.class).getResultList();
        }
    }

    public Book getBookById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT b FROM Book b " +
                    "JOIN FETCH b.authors " +
                    "JOIN FETCH b.categories " +
                    "WHERE b.id=:id", Book.class).setParameter("id", id).getSingleResult();
        }
    }

    public List<Category> getAllCategories() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT c FROM Category c ", Category.class).getResultList();
        }
    }

    public List<Author> getAllAuthors() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT a FROM Author a", Author.class).getResultList();
        }
    }

    public void updateBook(Book book) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.merge(book);

            transaction.commit();
        }
    }

    public void deleteBook(Book book) {
        try (Session session = sessionFactory.openSession()) {
        }
    }
}
