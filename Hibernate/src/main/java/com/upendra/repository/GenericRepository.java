package com.upendra.repository;

import java.util.List;

public interface GenericRepository<T> {
    void save(T instance);

    T findByPrimaryKey(Object id, Class<T> klass);

    T findByNaturalId(Object naturalId, Class<T> klass);

    <K> List<K> executeSelectionHQL(String hql, Class<K> klass, boolean readOnly, Object... params);

    int executeMutationHQL(String hql, Object... params);

    <K> List<K> executeNamedSelectionHQL(String queryName, Class<K> klass, boolean readOnly, Object... params);

    int executeNamedMutationHQL(String queryName, Object... params);

    <K> List<K> executeSelectionNativeQuery(String sql, Class<K> klass, Object... params);

    int executeMutationNativeQuery(String sql, Object... params);
}
