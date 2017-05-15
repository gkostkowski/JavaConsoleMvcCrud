package com.kostkowski.dao.xml;

import com.kostkowski.model.Shelf;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by Grzegorz Kostkowski on 2017-03-11.
 */
public class SimpleShelvesWrapper implements Serializable{
    private Collection<ShelfWrapper> shelvesCollection;



    public SimpleShelvesWrapper() {
        shelvesCollection = new LinkedList<ShelfWrapper>();
    }
    public SimpleShelvesWrapper(Collection<Shelf> collection) {
        setShelves(collection);
    }

    public Collection<ShelfWrapper> getShelves() {
        return shelvesCollection;
    }

    public void setShelves(Collection<Shelf> shelves) {
        //this.shelvesCollection = shelves;
        if (this.shelvesCollection == null)
            shelvesCollection = new LinkedList<ShelfWrapper>();
        for (Shelf s: shelves)
            shelvesCollection.add(new ShelfWrapper(s));
    }


}
