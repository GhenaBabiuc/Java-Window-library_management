package org.example.service;

import org.example.dao.BorrowHistoryDao;
import org.example.model.books.BorrowHistory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class BorrowHistoryService {
    private final BorrowHistoryDao borrowHistoryDao;

    public BorrowHistoryService() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        borrowHistoryDao = new BorrowHistoryDao(sessionFactory);
    }

    public List<BorrowHistory> getAllBorrowHistory() {
        return borrowHistoryDao.getAllBorrowHistory();
    }

    public BorrowHistory getBorrowHistoryById(Long id) {
        return borrowHistoryDao.getBorrowHistoryById(id);
    }

    public void updateBorrowHistory(BorrowHistory borrowHistory) {
        borrowHistoryDao.updateBorrowHistory(borrowHistory);
    }
}
