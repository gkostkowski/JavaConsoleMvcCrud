package com.kostkowski.model;

import javax.naming.SizeLimitExceededException;
import javax.swing.table.TableStringConverter;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Grzegorz Kostkowski on 2017-03-04.
 */
public abstract class IShop<P, S, I, W> {
    Collection <Shelf> shelves;
    Collection <Salesman> workers;
    protected static final String EMPTY_COLLECTION = "Brak polek.";

    abstract public void putOnShelf(P prod, Integer shelfId) throws SizeLimitExceededException, SQLException; //create product
    abstract public P takeFromShelf(I prodId, Integer shelfId) throws SQLException; //delete product
    abstract public P checkProduct(Integer prodId, Integer shelfId); //retireve product
    abstract public void changeProduct(I prodId, Integer shelfId, P newProd) throws SizeLimitExceededException; //update product
    abstract public boolean placeShelf(S shelf) throws SQLException; // create shelf
    abstract public S takeOutShelf(I shelfId); // delete shelf
    abstract public S checkShelf(I shelfId); // retrieve shelf
    abstract public boolean rearrangeShelf(I shelfId, S newShelf) throws SQLException; // update shelf
    abstract public String checkShelfs(); // retrieve all shelfs
    abstract public String checkProducts(Integer shelfId);  // retrieve products from shelf
    abstract public String checkProducts();  // retrieve all products

    public abstract void finishSession();

    public abstract boolean createWorker(W newWorker);

    public abstract String deleteWorker(I workerId);

    public abstract String checkWorkers();

    public abstract W checkWorker(I workerId);

    protected <T> String retrieveAll(Collection<T> collection) {
        StringBuilder res = new StringBuilder();
        for (Iterator<T> it = collection.iterator(); it.hasNext(); ) {
            res.append(it.next().toString()).append("\n");
        }
        return res.toString().isEmpty() ? EMPTY_COLLECTION : res.toString();
    }
    protected <T extends Identifiable<Integer>> T retrieve(int id, Collection<T> collection) {
        T res = null;
        for (T elem : collection) {
            if (elem.getId() == id)
                res = elem;
        }
        return res;
    }
}
