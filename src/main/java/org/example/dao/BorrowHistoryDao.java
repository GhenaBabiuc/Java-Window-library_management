package org.example.dao;

import org.example.model.books.Book;
import org.example.model.books.BorrowHistory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BorrowHistoryDao {
    private final SessionFactory sessionFactory;

    public BorrowHistoryDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<BorrowHistory> getAllBorrowHistory() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT bh FROM BorrowHistory bh ", BorrowHistory.class).getResultList();
        }
    }

    public BorrowHistory getBorrowHistoryById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT bh FROM BorrowHistory bh " +
                    "WHERE bh.id=:id ", BorrowHistory.class).setParameter("id", id).getSingleResult();
        }
    }

    public void updateBorrowHistory(BorrowHistory borrowHistory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.merge(borrowHistory);

            transaction.commit();
        }
    }
}
