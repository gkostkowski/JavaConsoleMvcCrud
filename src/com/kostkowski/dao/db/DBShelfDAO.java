package com.kostkowski.dao.db;

import com.kostkowski.dao.IProductDAO;
import com.kostkowski.dao.IShelfDAO;
import com.kostkowski.model.Product;
import com.kostkowski.model.Salesman;
import com.kostkowski.model.Shelf;
import javafx.util.Pair;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Grzegorz Kostkowski on 2017-03-10.
 */
public class DBShelfDAO extends DBDAO
        implements IShelfDAO<Integer, Product, Shelf> {

    IProductDAO<Integer, Product, Shelf> prodDao = new DBProductDAO();

    DBShelfDAO(String user, String password) throws SQLException {
        super(user, password);
    }

    public DBShelfDAO() throws SQLException {
        super();
    }

    @Override
    public boolean add(Shelf shelf) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Shelves (shelf_id, capacity, maxCapacity, category) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, shelf.getShelfId());
            preparedStatement.setInt(2, shelf.getCapacity());
            preparedStatement.setInt(3, shelf.getMaxCapacity());
            preparedStatement.setString(4, shelf.getCategory());
            preparedStatement.executeUpdate();
            for (Product p: shelf.getProducts())
                prodDao.add(p, shelf.getShelfId());
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Shelf get(Integer id) {
        ResultSet rs = null;
        Shelf res;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM SHELVES WHERE shelf_id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.first();
            do {
                res = new Shelf(rs.getInt("shelf_id"), rs.getInt("capacity"),
                        rs.getInt("maxCapacity"), rs.getString("category"));
            } while (rs.next());
            rs.close();
            ps.close();
            return res;
        } catch (SQLException e) {
            rs = null;
            return null;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            if (prodDao.deleteAll(id)) {
                PreparedStatement psDelShelf = connection.prepareStatement(
                        "DELETE FROM Shelves WHERE shelf_id = ?");
                psDelShelf.setInt(1, id);
                psDelShelf.executeUpdate();
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Integer id, Shelf shelf) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE SHELVES SET maxCapacity = ?, category = ? WHERE shelf_id = ?");
            preparedStatement.setInt(1, shelf.getMaxCapacity());
            preparedStatement.setString(2, shelf.getCategory());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Pair<Collection<Shelf>, Collection<Salesman>> fetchAll() {
        ResultSet rs = null;
        Collection<Shelf> res = new ArrayList<Shelf>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM SHELVES");
            rs = ps.executeQuery();
            if (rs.first())
                do {
                    int currShelfId = rs.getInt("shelf_id");
                    res.add(new Shelf(currShelfId, rs.getInt("capacity"),
                            rs.getInt("maxCapacity"), rs.getString("category"),
                            prodDao.fetchAll(currShelfId)));
                } while (rs.next());
            rs.close();
            ps.close();
            return new Pair(res, null); //todo
        } catch (Exception e) {
            rs = null;
            return null;
        }
    }

    @Override
    public boolean setAll(Collection<Shelf> elems) {
        return false;
    }

    @Override
    public boolean finish() {
        return super.finish();
    }

    @Override
    public void save(Collection<Shelf> currCollection, Collection<Salesman> workers) {

    }
}
