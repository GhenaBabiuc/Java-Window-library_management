package org.example.dao;

import org.apache.commons.lang3.StringUtils;
import org.example.filters.BorrowHistoryFilter;
import org.example.model.books.Book;
import org.example.model.books.BorrowHistory;
import org.example.model.users.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowHistoryDao {
    private final SessionFactory sessionFactory;

    public BorrowHistoryDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<BorrowHistory> searchBorrowHistory(BorrowHistoryFilter borrowHistoryFilter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<BorrowHistory> query = builder.createQuery(BorrowHistory.class);
            Root<BorrowHistory> root = query.from(BorrowHistory.class);

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(borrowHistoryFilter.getUserIdn())) {
                Join<BorrowHistory, User> userJoin = root.join("user");
                predicates.add(builder.like(builder.lower(userJoin.get("idn")), "%" + borrowHistoryFilter.getUserIdn().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(borrowHistoryFilter.getUsername())) {
                Join<BorrowHistory, User> userJoin = root.join("user");
                Predicate firstNamePredicate = builder.like(builder.lower(userJoin.get("firstName")), "%" + borrowHistoryFilter.getUsername().toLowerCase() + "%");
                Predicate lastNamePredicate = builder.like(builder.lower(userJoin.get("lastName")), "%" + borrowHistoryFilter.getUsername().toLowerCase() + "%");
                predicates.add(builder.or(firstNamePredicate, lastNamePredicate));
            }

            if (StringUtils.isNotBlank(borrowHistoryFilter.getBookTitle())) {
                Join<BorrowHistory, Book> bookJoin = root.join("book");
                predicates.add(builder.like(builder.lower(bookJoin.get("title")), "%" + borrowHistoryFilter.getBookTitle().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(borrowHistoryFilter.getBookIsbn())) {
                Join<BorrowHistory, Book> bookJoin = root.join("book");
                predicates.add(builder.like(builder.lower(bookJoin.get("isbn")), "%" + borrowHistoryFilter.getBookIsbn().toLowerCase() + "%"));
            }

            if (borrowHistoryFilter.getBeforeDate() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("borrowDate"), borrowHistoryFilter.getBeforeDate()));
            }

            if (borrowHistoryFilter.getAfterDate() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("borrowDate"), borrowHistoryFilter.getAfterDate()));
            }

            query.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(query).getResultList();
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
