package com.upendra.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.SelectionQuery;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.hibernate.internal.TransactionManagement.manageTransaction;

public abstract class HibernateGenericRepository<T> implements GenericRepository<T> {

    protected final SessionFactory sessionFactory;

    protected HibernateGenericRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Override this to specify the policy to get session for the unit of work.
     * By default, it relies on hibernate current session context policy. If it needs to change the policy, override and provide the implementation.
     *
     * @return Session Instance to perform current operation.
     */
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected void inTransaction(Consumer<Session> action) {
        try (Session session = getSession()) {
            manageTransaction(session, session.beginTransaction(), action);
        }
    }

    protected <R> R inTransaction(Function<Session, R> action) {
        try (Session session = getSession()) {
            return manageTransaction(session, session.beginTransaction(), action);
        }
    }

    @Override
    public void save(T instance) {
        inTransaction((Session session) -> session.persist(instance));
    }

    @Override
    public T findByPrimaryKey(Object id, Class<T> klass) {
        return inTransaction((Session session) -> session.get(klass, id));
    }

    @Override
    public T findByNaturalId(Object naturalId, Class<T> klass) {
        return inTransaction((Session session) -> session.bySimpleNaturalId(klass).load(naturalId));
    }

    @Override
    public <K> List<K> executeSelectionHQL(String hql, Class<K> klass, boolean readOnly, Object... params) {
        return inTransaction(session -> {
            SelectionQuery<K> query = session.createSelectionQuery(hql, klass);
            query.setReadOnly(readOnly);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            return query.getResultList();
        });
    }

    @Override
    public int executeMutationHQL(String hql, Object... params) {
        return inTransaction(session -> {
            MutationQuery query = session.createMutationQuery(hql);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            return query.executeUpdate();
        });
    }

    @Override
    public <K> List<K> executeNamedSelectionHQL(String queryName, Class<K> klass, boolean readOnly, Object... params) {
        return inTransaction(session -> {
            SelectionQuery<K> query = session.createNamedSelectionQuery(queryName, klass);
            query.setReadOnly(readOnly);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            return query.getResultList();
        });
    }

    @Override
    public int executeNamedMutationHQL(String queryName, Object... params) {
        return inTransaction(session -> {
            MutationQuery query = session.createNamedMutationQuery(queryName);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            return query.executeUpdate();
        });
    }

    @Override
    public <K> List<K> executeSelectionNativeQuery(String sql, Class<K> klass, Object... params) {
        return inTransaction(session -> {
            NativeQuery<K> nativeQuery = session.createNativeQuery(sql, klass);
            for (int i = 0; i < params.length; i++) {
                nativeQuery.setParameter(i + 1, params[i]);
            }
            return nativeQuery.getResultList();
        });
    }

    @Override
    public int executeMutationNativeQuery(String sql, Object... params) {
        return inTransaction(session -> {
            NativeQuery<Integer> nativeQuery = session.createNativeQuery(sql, Integer.class);
            for (int i = 0; i < params.length; i++) {
                nativeQuery.setParameter(i + 1, params[i]);
            }
            return nativeQuery.executeUpdate();
        });
    }
}
