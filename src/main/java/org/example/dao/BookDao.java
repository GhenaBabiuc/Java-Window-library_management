package org.example.dao;

import org.apache.commons.lang3.StringUtils;
import org.example.filters.BookFilter;
import org.example.model.books.Author;
import org.example.model.books.Book;
import org.example.model.books.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.*;
import java.util.ArrayList;
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

    public List<Book> searchBooks(BookFilter bookFilter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Book> query = builder.createQuery(Book.class);
            Root<Book> root = query.from(Book.class);

            root.fetch("authors", JoinType.LEFT);
            root.fetch("categories", JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(bookFilter.getIsbn())) {
                predicates.add(builder.like(builder.lower(root.get("isbn")), "%" + bookFilter.getIsbn().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(bookFilter.getTitle())) {
                predicates.add(builder.like(builder.lower(root.get("title")), "%" + bookFilter.getTitle().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(bookFilter.getYear())) {
                predicates.add(builder.equal(root.get("year"), bookFilter.getYear()));
            }

            if (StringUtils.isNotBlank(bookFilter.getAuthor())) {
                Join<Book, Author> authorJoin = root.join("authors");
                predicates.add(builder.like(builder.lower(authorJoin.get("name")), "%" + bookFilter.getAuthor().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(bookFilter.getCategory())) {
                Join<Book, Category> categoryJoin = root.join("categories");
                predicates.add(builder.like(builder.lower(categoryJoin.get("name")), "%" + bookFilter.getCategory().toLowerCase() + "%"));
            }

            query.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(query).getResultList();
        }
    }

    public void deleteBook(Book book) {
        try (Session session = sessionFactory.openSession()) {
        }
    }
}
