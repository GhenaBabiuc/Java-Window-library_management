package org.example.dao;

import org.example.model.books.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
}
