package org.example.service;

import org.example.dao.UserDao;
import org.example.filters.UserFilter;
import org.example.model.users.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UserService {
    private final UserDao userDao;

    public UserService() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        userDao = new UserDao(sessionFactory);
    }

    public List<User> searchUsers(UserFilter userFilter) {
        return userDao.searchUsers(userFilter);
    }

    public User getUserByID(Long id) {
        return userDao.getUserById(id);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }
}
