package com.arthuryasak.utils;

import com.arthuryasak.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    public static Session getSession() {
        sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        return session;
    }
    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(User.class)
                        .addAnnotatedClass(Lot.class)
                        .addAnnotatedClass(LotProperty.class)
                        .addAnnotatedClass(Bet.class)
                        .addAnnotatedClass(UserData.class)
                        .addAnnotatedClass(AuthorizationData.class)
                        .addAnnotatedClass(Commission.class)
                        .addAnnotatedClass(Role.class)
                        .buildSessionFactory();
            } catch (Exception e) {
                System.out.println("EXCEPTION! ");
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }
}
