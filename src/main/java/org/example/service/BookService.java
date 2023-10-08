package org.example.service;

import org.example.dao.BookDao;
import org.example.model.books.Book;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class BookService {
    private final BookDao bookDao;

    public BookService() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        bookDao = new BookDao(sessionFactory);
    }

    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }
}
