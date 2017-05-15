package com.kostkowski.dao;

import com.kostkowski.model.Shelf;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by Grzegorz Kostkowski on 2017-03-10.
 */
public interface IGenericDAO<K, V> {

    V get(K id);
    boolean delete(K id);
    boolean update(K id, V newVal);

    boolean finish();
}
