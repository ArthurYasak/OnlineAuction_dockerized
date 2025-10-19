package com.arthuryasak.dao;

import com.arthuryasak.exceptions.DAOException;
import com.arthuryasak.models.Lot;
import com.arthuryasak.models.User;
import com.arthuryasak.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Supplier;

@Repository
public class LotDAOImpl implements LotDAO {

    private static Logger logger = LoggerFactory.getLogger(LotDAOImpl.class);

    @Override
    public List<Lot> getAll() {
        try(Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<Lot> lotQuery = session.createQuery("from Lot l where l.isBought = false");
            return lotQuery.list();
        }
    }

    @Override
    public List<Lot> getAllForUser(User currentUser) {
        try(Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<Lot> lotQuery = session.createQuery("from Lot l where NOT l.userOwner.userId = ?1 and " +
                    "l.isBought = false", Lot.class);
            lotQuery.setParameter(1, currentUser.getUserId());
            return lotQuery.list();
        }
    }

    @Override
    public List<Lot> getSold(User currentUser) {
        try(Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<Lot> lotQuery = session.createQuery("from Lot l where l.userOwner.userId = ?1 and " +
                    "l.isBought = true", Lot.class);
            lotQuery.setParameter(1, currentUser.getUserId());
            return lotQuery.list();
        }
    }

    @Override
    public List<Lot> getBought(User currentUser) {
        try(Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<Lot> lotQuery = session.createQuery("from Lot l where l.lastCustomer = ?1 and " +
                    "l.isBought = true", Lot.class);
            lotQuery.setParameter(1, currentUser);
            return lotQuery.list();
        }
    }

    @Override
    public Lot getById(Integer lotId) {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Lot lot = session.get(Lot.class, lotId);
            return lot;
        }
    }

    @Override
    public List<Lot> getByUserId(int userId) {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<Lot> query = session.createQuery("from Lot l where l.userOwner.userId = ?1 and " +
                    "l.isBought = false", Lot.class);
            query.setParameter(1, userId);
            return query.list();
        }
    }

    public List<Lot> getWhereLastCustomer(int userId) {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<Lot> query = session.createQuery("from Lot l where l.lastCustomer.userId = ?1", Lot.class);

            query.setParameter(1, userId);
            return query.list();
        }
    }

    @Override
    public List<Lot> getByNameOrTypeForUser(String keyWord, User currentUser) throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<Lot> query = session.createQuery("from Lot l where (LOCATE(LOWER(?1), LOWER(l.property.name)) > 0 " +
                    "or LOCATE(LOWER(?1), LOWER(l.property.type)) > 0) and l.isBought = false and " +
                    "NOT l.userOwner.userId = ?2");
            query.setParameter(1, keyWord);
            query.setParameter(2,currentUser.getUserId());
            return query.list();
        }
    }

    @Override
    public void add(Lot lot) {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            withTransaction(session.beginTransaction(), () -> session.persist(lot));
        }
    }

    @Override
    public Lot update(Lot lot) {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            return (Lot)withTransaction(session.beginTransaction(), () -> session.merge(lot));
        }
    }

    @Override
    public void delete(Lot lot) {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            withTransaction(session.beginTransaction(), () -> {
                session.remove(lot);
            });
        }
    }

    public void deleteByUserId(int userId) {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {

            withTransaction(session.beginTransaction(), () -> {
                Query<Lot> query = session.createNativeQuery("delete from lot l where l.userOwner.userId = ?1");
                query.setParameter(1, userId);
            });
        }
    }

    private <T> T withTransaction(Transaction transaction, Supplier<T> supplier) throws DAOException {
        try {
            T result = supplier.get();
            transaction.commit();
            return result;
        } catch(Exception e) {
            transaction.rollback();
            throw new DAOException("Error at transaction with Lot", e);
        }
    }

    private void withTransaction(Transaction transaction, Runnable runnable) throws DAOException {
        try {
            runnable.run();
            transaction.commit();
        } catch(Exception e) {
            transaction.rollback();
            throw new DAOException("Error at transaction with Lot", e);
        }
    }


}
