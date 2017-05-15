package com.kostkowski.dao;

import com.kostkowski.model.Salesman;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by Grzegorz Kostkowski on 2017-03-11.
 */
public interface IProductDAO<K, P, S> extends IGenericDAO<K, P> {
    void add(P val, K shelfId) throws SQLException;
    boolean delete(K prodId, K shelfId);
    boolean deleteAll(K shelfId);
    Collection<P> fetchAll(K shelfId);
    public void save(Collection<S> shelves, Collection<Salesman> workers);
}
