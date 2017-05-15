package com.kostkowski.dao.db;

import com.kostkowski.dao.IProductDAO;
import com.kostkowski.model.Product;
import com.kostkowski.model.Salesman;
import com.kostkowski.model.Shelf;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Grzegorz Kostkowski on 2017-03-11.
 */
public class DBProductDAO extends DBDAO
        implements IProductDAO<Integer, Product, Shelf> {

    public DBProductDAO(String user, String password) throws SQLException {
        super(user, password);
    }

    public DBProductDAO() throws SQLException {
        super();
    }

    @Override
    public Product get(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer prodId) {
        return false;
    }

    @Override
    public boolean deleteAll(Integer shelfId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM Products WHERE shelf_id = ?");
            preparedStatement.setInt(1, shelfId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(Integer prodId, Integer shelfId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM Products WHERE shelf_id = ? AND prod_id = ?");
            preparedStatement.setInt(1, shelfId);
            preparedStatement.setInt(2, prodId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Integer id, Product newVal) {
        return false;
    }

    @Override
    public void add(Product prod, Integer shelfId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Products (prod_id, shelf_id, price, name) VALUES (?, ?, ?, ?)");
        preparedStatement.setInt(1, prod.getId());
        preparedStatement.setInt(2, shelfId);
        preparedStatement.setDouble(3, prod.getPrice());
        preparedStatement.setString(4, prod.getName());
        int res = preparedStatement.executeUpdate();

    }

    @Override
    public Collection<Product> fetchAll(Integer id) {
        ResultSet rs = null;
        Collection<Product> res = new ArrayList<Product>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM PRODUCTS WHERE shelf_id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.first())
                do {
                    res.add(new Product(rs.getInt("prod_id"), rs.getDouble("price"),
                            rs.getString("name")));
                } while (rs.next());
            rs.close();
            ps.close();
            return res;
        } catch (Exception e) {
            rs = null;
            return null;
        }
    }

    @Override
    public void save(Collection<Shelf> shelves, Collection<Salesman> workers) {
    }

    @Override
    public boolean finish() {
        return super.finish();
    }


}
