package com.arthuryasak.dao;

import com.arthuryasak.exceptions.DAOException;
import com.arthuryasak.models.Bet;
import com.arthuryasak.models.Lot;
import com.arthuryasak.models.User;
import com.arthuryasak.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Supplier;

@Repository
public class BetDAOImpl implements BetDAO {

    @Override
    public Bet getById(Integer betId) {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Bet bet = session.get(Bet.class, betId);
            return bet;
        }
    }

    @Override
    public List<Bet> getByUser(User user) {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<Bet> query = session.createQuery("from Bet b where b.userOwner.userId = ?1", Bet.class);
            query.setParameter(1, user.getUserId());
            return query.list();
        }
    }

    @Override
    public Bet getByLotId(int lotId) {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<Bet> query = session.createQuery("from Bet b where b.lot.lotId = ?1", Bet.class);
            query.setParameter(1, lotId);
            List list = query.list();
            if (list.size() == 1) {
                return query.list().get(0);
            } else {
                return null;
            }
        }
    }

    @Override
    public void add(Bet bet) {
        try(Session session = HibernateSessionFactoryUtil.getSession()) {
            withTransaction(session.beginTransaction(), () -> session.persist(bet));
        }
    }

    @Override
    public Bet update(Bet bet) {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            return (Bet)withTransaction(session.beginTransaction(), () -> session.merge(bet));
        }
    }

    @Override
    public void deleteByLotId(int lotId) {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            withTransaction(session.beginTransaction(), () -> {
                Query<Bet> query = session.createQuery("delete from Bet b where b.lot.lotId = ?1");
                query.setParameter(1, lotId);
                query.executeUpdate();
            });
        }
    }

    public void deleteByUserId(int userId) {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {

            withTransaction(session.beginTransaction(), () -> {
                Query<Bet> query = session.createNativeQuery
                        ("delete from bet b where b.userOwner.userId() = ?1");
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
