package com.jersey.rest.api.repository.impl;

import com.jersey.rest.api.repository.UserRepository;
import com.jersey.rest.api.utils.HibernateUtils;
import com.jersey.rest.api.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public User createUser(User user) {
        User savedUser = new User();
        Transaction trx = null;
        try {
            Session session = HibernateUtils.getsessionFactory().openSession();
            trx = session.beginTransaction();
            Serializable id = session.save(user);
            trx.commit();
            savedUser = session.get(User.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            if (trx != null) {
                trx.rollback();
            }
            ex.printStackTrace();
        }
        return savedUser;
    }

    @Override
    public List<User> findByLastName(String lastName) {
        List<User> users = null;
        try (Session session = HibernateUtils.getsessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE lastName = :lastName", User.class);
            query.setParameter("lastName", lastName);
            users = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return users;
    }

    @Override
    public User findUserById(String id) {
        User user = new User();
        try (Session session = HibernateUtils.getsessionFactory().openSession()) {
            user = session.get(User.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return user;
    }

    @Override
    public User updateUser(User user) {
        Transaction trx = null;
        try (Session session = HibernateUtils.getsessionFactory().openSession()) {
            trx = session.beginTransaction();
            session.update(user);
            trx.commit();
        } catch (Exception ex) {
            if (trx != null) {
                trx.rollback();
            }
            ex.printStackTrace();
        }

        return user;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        Session session = HibernateUtils.getsessionFactory().openSession();
        Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
        query.setParameter("username", username);
        return query.uniqueResultOptional();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        Session session = HibernateUtils.getsessionFactory().openSession();
        Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
        query.setParameter("email", email);
        return query.uniqueResultOptional();
    }
}
