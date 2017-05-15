package com.kostkowski.dao.xml;

import com.kostkowski.dao.IShelfDAO;
import com.kostkowski.model.Product;
import com.kostkowski.model.Salesman;
import com.kostkowski.model.Shelf;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;

/**
 * Created by Grzegorz Kostkowski on 2017-03-10.
 */
public class XMLShelfDAO extends XMLDAO
        implements IShelfDAO<Integer, Product, Shelf> {

    private Collection<Shelf> shelves;

    //shelfDataSource.updateXML(shelves);

    @Override
    public boolean setAll(Collection<Shelf> elems) {
        throw new NotImplementedException();
        //return false;
    }

    @Override
    public boolean add(Shelf val) {
        return true;
    }

    @Override
    public void save(Collection<Shelf> shelves, Collection<Salesman> workers) {
        updateXML(shelves, workers);
    }

    @Override
    public Shelf get(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return true;
    }

    @Override
    public boolean update(Integer id, Shelf newVal) {
        return true;
    }

    @Override
    public boolean finish() {
        return true;
    }



}
