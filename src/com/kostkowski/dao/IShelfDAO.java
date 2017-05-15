package com.kostkowski.dao;



import com.kostkowski.model.Salesman;
import com.kostkowski.model.Shelf;
import javafx.util.Pair;

import java.util.Collection;

/**
 * Created by Grzegorz Kostkowski on 2017-03-10.
 */
public interface IShelfDAO <K, P, S> extends IGenericDAO<K, S> {
    //Collection<P> fetchAll();
    Pair<Collection<Shelf>, Collection<Salesman>> fetchAll();
    boolean setAll(Collection<S> elems);
    boolean add(S val);
    void save(Collection<S> currCollection, Collection<Salesman> workers);

}
