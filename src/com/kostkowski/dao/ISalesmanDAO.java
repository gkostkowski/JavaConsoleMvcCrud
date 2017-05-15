package com.kostkowski.dao;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by Grzesiek on 2017-03-19.
 */
public interface ISalesmanDAO<K, V> {
    boolean add(V val);
    boolean delete(K workerId);
}
