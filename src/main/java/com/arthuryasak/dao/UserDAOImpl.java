package com.arthuryasak.dao;

import com.arthuryasak.exceptions.DAOException;
import com.arthuryasak.models.AuthorizationData;
import com.arthuryasak.models.User;
import com.arthuryasak.utils.HibernateSessionFactoryUtil;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Supplier;


@Repository
public class UserDAOImpl implements UserDAO {

    private static Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    public UserDAOImpl() {

    }

    @Override
    public void add(User user) throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            withTransaction(session.beginTransaction(), () -> session.persist(user));

        }
    }

    @Override
    public List<User> getAll(int userId) throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<User> query = session.createQuery("from User u where NOT u.userId = ?1", User.class);
            query.setParameter(1, userId);
            logger.info("\nALL users: \n" + query.list() + '\n');
            return query.list();
        }
    }

    @Override
    public User getById(Integer userId) throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            User user = session.get(User.class, userId);
            return user;
        }
    }

    @Override
    public User getByUsername(String username) throws DAOException {  // not tested
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            List<User> allUsersList = query.list();
            for (User user: allUsersList) {
                AuthorizationData authorizationData = user.getAuthorizationData();
                if (authorizationData.getLogin().equals(username)) {
                    return user;
                }
            }
            return null;
        }
    }

    @Override
    public User update(User user) throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            return (User)withTransaction(session.beginTransaction(), () -> session.merge(user));
        }
    }

    @Override
    public void deleteAll() throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            withTransaction(session.beginTransaction(), () -> {
                Query<User> query = session.createNativeQuery("DELETE FROM Users", User.class);
                query.executeUpdate();
            });
        }
    }

    @Override
    public void deleteById(Integer userId) throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            withTransaction(session.beginTransaction(), () -> {
                User userForRemove = getById(userId);
                logger.info("\nUSER FOR DELETE: " + userForRemove);
                session.remove(userForRemove);
            });
            logger.info("User with id: " + userId + " was DELETED \n");
        }
    }

    public User getFirstUser() throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            ScrollableResults scroll = query.scroll();
            scroll.first();
            User firstUser = ((User[])scroll.get())[0];
            logger.info("FIRST user is: " + firstUser + '\n');
            return firstUser;
        }
    }

    public User getLastUser() throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            ScrollableResults scroll = query.scroll();
            scroll.last();
            User lastUser = ((User[])scroll.get())[0];
            logger.info("LAST user is: " + lastUser + '\n');
            return lastUser;
        }
    }

    private <T> T withTransaction(Transaction transaction, Supplier<T> supplier) throws DAOException {
        try {
            T result = supplier.get();
            transaction.commit();
            return result;
        } catch(Exception e) {
            transaction.rollback();
            throw new DAOException("Error at transaction with User", e);
        }
    }

    private void withTransaction(Transaction transaction, Runnable runnable) throws DAOException {
        try {
            runnable.run();
            transaction.commit();
        } catch(Exception e) {
            transaction.rollback();
            throw new DAOException("Error at transaction with User", e);
        }
    }
}
