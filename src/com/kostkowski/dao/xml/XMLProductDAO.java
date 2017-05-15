package com.kostkowski.dao.xml;

import com.kostkowski.dao.IProductDAO;
import com.kostkowski.model.Product;
import com.kostkowski.model.Salesman;
import com.kostkowski.model.Shelf;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by Grzegorz Kostkowski on 2017-03-11.
 */
public class XMLProductDAO extends XMLDAO
        implements IProductDAO<Integer, Product, Shelf> {

    @Override
    public Product get(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean update(Integer id, Product newVal) {
        return false;
    }

    @Override
    public boolean finish() {
        return false;
    }

    @Override
    public void save(Collection<Shelf> shelves, Collection<Salesman> workers) {

        updateXML(shelves, workers);
    }

    @Override
    public void add(Product val, Integer shelfId) throws SQLException {

    }

    @Override
    public boolean delete(Integer prodId, Integer shelfId) {
        return false;
    }

    @Override
    public boolean deleteAll(Integer shelfId) {
        return false;
    }

    @Override
    public Collection<Product> fetchAll(Integer id) {
        return null;//super.fetchAll();
    }


    @Override
    public boolean updateXML(Collection<Shelf> shelves, Collection<Salesman> workers) {
        return super.updateXML(shelves, workers);
    }


}
