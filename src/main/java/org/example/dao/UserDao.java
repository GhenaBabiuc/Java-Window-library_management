package org.example.dao;

import org.example.model.users.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDao {
    private final SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT u FROM User u " +
                    "JOIN FETCH u.contacts ", User.class).list();
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
