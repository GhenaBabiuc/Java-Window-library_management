package org.example.dao;

import org.example.model.users.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDao {
    private final SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> userRoot = criteria.from(User.class);

            userRoot.fetch("contacts", JoinType.LEFT);

            criteria.select(userRoot);

            return session.createQuery(criteria).getResultList();
        }
    }

    public User getUserById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT u FROM User u " +
                    "JOIN FETCH u.contacts " +
                    "WHERE u.id=:id", User.class).setParameter("id", id).getSingleResult();
        }
    }
}
