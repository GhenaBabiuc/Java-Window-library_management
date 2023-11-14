package org.example.dao;

import org.apache.commons.lang3.StringUtils;
import org.example.filters.UserFilter;
import org.example.model.users.Contact;
import org.example.model.users.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> searchUsers(UserFilter userFilter) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> userRoot = query.from(User.class);

            userRoot.fetch("contacts", JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(userFilter.getIdn())) {
                predicates.add(builder.like(builder.lower(userRoot.get("idn")), "%" + userFilter.getIdn().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(userFilter.getFirstName())) {
                predicates.add(builder.like(builder.lower(userRoot.get("firstName")), "%" + userFilter.getFirstName().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(userFilter.getLastName())) {
                predicates.add(builder.like(builder.lower(userRoot.get("lastName")), "%" + userFilter.getLastName().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(userFilter.getContacts())) {
                Join<User, Contact> contactJoin = userRoot.join("contacts");
                predicates.add(builder.like(builder.lower(contactJoin.get("value")), "%" + userFilter.getContacts().toLowerCase() + "%"));
            }

            query.where(predicates.toArray(new Predicate[0]));

            return session.createQuery(query).getResultList();
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
