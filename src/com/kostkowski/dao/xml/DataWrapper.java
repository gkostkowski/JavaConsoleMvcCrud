package com.kostkowski.dao.xml;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import com.kostkowski.model.Salesman;
import com.kostkowski.model.Shelf;
import javafx.util.Pair;

/**
 * Created by Grzegorz Kostkowski on 2017-03-11.
 */
public class DataWrapper implements Serializable{
    private Collection<Shelf> shelvesCollection;
    private Collection<Salesman> workersCollection;


    public DataWrapper() {
        shelvesCollection = new LinkedList<Shelf>();
    }
    public DataWrapper(Collection<Shelf> shelves, Collection<Salesman> workers) {
        setData(shelves, workers);
    }
    public DataWrapper(Pair<Collection<Shelf>, Collection<Salesman>> pair) {
        setData(pair);
    }

    public Pair<Collection<Shelf>, Collection<Salesman>> getData() {
        return new Pair(shelvesCollection, workersCollection);
    }

    public void setData(Collection<Shelf> shelves, Collection<Salesman> workers) {
        this.shelvesCollection = shelves;
        this.workersCollection = workers;
    }
    public void setData(Pair<Collection<Shelf>, Collection<Salesman>> pair) {
        this.shelvesCollection = pair.getKey();
        this.workersCollection = pair.getValue();
    }


}
