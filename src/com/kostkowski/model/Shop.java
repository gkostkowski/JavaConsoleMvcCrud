package com.kostkowski.model;

import com.kostkowski.dao.DAOFactory;
import com.kostkowski.dao.IProductDAO;
import com.kostkowski.dao.IShelfDAO;
import javafx.util.Pair;
import org.jetbrains.annotations.Nullable;

import javax.naming.SizeLimitExceededException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Grzegorz Kostkowski on 2017-03-04.
 */
public class Shop extends IShop<Product, Shelf, Integer, Salesman> implements IModel {

    private IShelfDAO<Integer, Product, Shelf> shelfDataSource = null;
    private IProductDAO<Integer, Product, Shelf> prodDataSource = null;
    /*Zakladam ze w kazdej chwili dzialania programu stan kolekcji jest zgodny ze stanem kontenerow*/


    private static final String EMPTY_SHELF = "Brak produktow na wskazanej polce lub brak polki o wskazanym numerze: ";
    private static final String COLLECTION_TITLE = "\nWszystkie dostepne produkty:\n";
    private static final String PRODUCTS_TITLE = "Produkty z polki nr ";

    public Shop() {
        shelves = new LinkedList<Shelf>();
        workers = new LinkedList<Salesman>();
    }

    @Override
    public void putOnShelf(Product prod, Integer shelfId) throws SizeLimitExceededException, SQLException {
        Shelf updated = checkShelf(shelfId);
        updated.create(prod);
        rearrangeShelf(shelfId, updated);
        shelfDataSource.save(shelves, workers);

    }

    @Override
    @Nullable
    public Product takeFromShelf(Integer prodId, Integer shelfId) {
        Shelf updated = checkShelf(shelfId);
        if (updated != null) {
            Product toRemove = updated.get(prodId);
            updated.remove(prodId);
            rearrangeShelf(shelfId, updated);
            shelfDataSource.save(shelves, workers);
            if (prodDataSource.delete(prodId, shelfId))
                return toRemove;
        }
        return null;
    }

    @Override
    @Nullable
    public Product checkProduct(Integer prodId, Integer shelfId) {
        Shelf shelf = checkShelf(shelfId);
        return shelf != null ? shelf.get(prodId) : null;
    }

    @Override
    public void changeProduct(Integer prodId, Integer shelfId, Product newProd) throws SizeLimitExceededException {
        checkShelf(shelfId).update(prodId, newProd);
        shelfDataSource.save(shelves, workers);
    }

    @Override
    public boolean placeShelf(Shelf shelf) {
        shelves.add(shelf);
        shelfDataSource.save(shelves, workers);
        return shelfDataSource.add(shelf);
    }

    @Override
    public Shelf takeOutShelf(Integer shelfId) {
        Shelf removed;
        shelves.remove(removed = checkShelf(shelfId));
        shelfDataSource.save(shelves, workers);
        return shelfDataSource.delete(shelfId) ? removed : null;
    }

    @Override
    @Nullable
    public Shelf checkShelf(Integer shelfId) {
        Shelf res = null;
        for (Shelf s : shelves) {
            if (s.getShelfId() == shelfId)
                res = s;
        }
        return res;
    }

    @Override
    public boolean rearrangeShelf(Integer shelfId, Shelf newShelf) {
        if (shelfId != newShelf.getShelfId())
            throw new IllegalStateException("Shelf identifier is invalid!");
        Shelf toRemove = checkShelf(shelfId);
        if (!toRemove.freeSlotExists() && newShelf.getMaxCapacity() < toRemove.getMaxCapacity())
            throw new IllegalStateException("Impossible to minimalize size of full shelf!");
        Shelf removed = takeOutShelf(shelfId);
        if (removed.getProducts() != null && removed.getProducts().size() > 0) {
            newShelf.setProducts(removed.getProducts());
            newShelf.setCapacity(removed.getCapacity());
        }
        return removed != null &&
                placeShelf(newShelf);
    }

    @Override
    public String checkShelfs() {
        StringBuilder res = new StringBuilder();
        for (Iterator<Shelf> it = shelves.iterator(); it.hasNext(); ) {
            res.append(it.next().toString()).append("\n");
        }
        return res.toString().isEmpty() ? EMPTY_COLLECTION : res.toString();
    }

    @Override
    @Nullable
    public String checkProducts(Integer shelfId) {
        StringBuilder res = new StringBuilder();
        Shelf shelf = checkShelf(shelfId);
        Collection<Product> prods;
        if (shelf != null) {
            prods = shelf.getProducts();
            if (prods != null) {
                for (Iterator<Product> it = prods.iterator(); it.hasNext(); ) {
                    res.append(it.next().toString()).append("\n");
                }
            }
        }
        return res.toString().isEmpty() ? EMPTY_SHELF + shelfId : PRODUCTS_TITLE + shelfId + ":\n" + res.toString();
    }

    @Override
    public String checkProducts() {
        StringBuilder res = new StringBuilder();
        for (Iterator<Shelf> it = shelves.iterator(); it.hasNext(); ) {
            try {
                res.append(checkProducts(it.next().getShelfId()).toString()).append("\n");
            } catch (NullPointerException e) {
                continue;
            }
        }
        return res.toString().isEmpty() ? EMPTY_COLLECTION : COLLECTION_TITLE + res.toString();
    }

    @Override
    public void finishSession() {
        shelfDataSource.finish();
    }

    @Override
    public boolean createWorker(Salesman newWorker) {
        boolean res = workers.add(newWorker);
        shelfDataSource.save(shelves, workers);
        return res;
    }

    @Nullable
    @Override
    public Salesman checkWorker(Integer workerId) {
        return retrieve(workerId, workers);
    }


    @Override
    public String deleteWorker(Integer workerId) {
        Salesman toRemove = checkWorker(workerId);
        boolean res = workers.remove(toRemove);
        shelfDataSource.save(shelves, workers);
        return res ? toRemove.toString() : null;
    }


    private Collection<Product> getAllProducts() {
        List<Product> all = new LinkedList<>();
        for (Shelf s : shelves) {
            all.addAll(s.getProducts());
        }
        return all;
    }

    private boolean isUnique(int newId) {
        boolean res = true;
        for (Shelf s : shelves) {
            if (s.getShelfId() == newId)
                res = false;
            else if (s.hasProducts()) {
                for (Product p : s.getProducts())
                    if (p.getId().equals(newId))
                        res = false;
            }
        }
        return res;
    }

    public boolean putOnShelf(List<String> inputs) {
        int newId = Integer.parseInt(inputs.get(0));
        if (isUnique(newId)) {
            try {
                putOnShelf(new Product(inputs.subList(0, inputs.size() - 1)), Integer.parseInt(inputs.get(inputs.size() - 1)));
                return true;
            } catch (SizeLimitExceededException e) {
                return false;
            } catch (Exception e) {
                return false;
            }
        } else
            return false;
    }

    private boolean isNumeric(String nbr) {
        int newId;
        try {
            newId = Integer.parseInt(nbr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int asNumeric(String nbr) {
        return Integer.parseInt(nbr);
    }

    public boolean placeShelf(List<String> inputs) {
        if (isNumeric(inputs.get(0)))
            if (isUnique(asNumeric(inputs.get(0)))) {
                try {
                    if (inputs.size() == 4)
                    placeShelf(new Shelf(inputs));
                    else if (inputs.size() > 4)
                        placeShelf(new Stall(inputs, checkWorker(Integer.parseInt(inputs.get(4)))));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        return false;
    }

    public Shelf checkShelf(List<String> inputs) {
        String shelfId = null;
        if (!inputs.isEmpty()) {
            shelfId = inputs.remove(0);
            if (isNumeric(shelfId)) {
                return checkShelf(asNumeric(shelfId));
            }
        }
        return null;
    }

    public Shelf takeOutShelf(List<String> inputs) {
        String shelfId;
        if (!inputs.isEmpty()) {
            shelfId = inputs.remove(0);
            if (isNumeric(shelfId)) {
                return takeOutShelf(asNumeric(shelfId));
            }
        }
        return null;
    }

    public boolean rearrangeShelf(List<String> inputs) {
        if (isNumeric(inputs.get(0)))
            try {
                return rearrangeShelf(asNumeric(inputs.get(0)), new Shelf(inputs));
            } catch (Exception e) {
                return false;
            }
        return false;
    }

    public Product checkProduct(List<String> inputs) {
        String shelfId, prodId;
        if (inputs.size() >= 2) {
            prodId = inputs.remove(0);
            shelfId = inputs.remove(0);
            if (isNumeric(prodId) && isNumeric(shelfId)) {
                return checkProduct(asNumeric(prodId), asNumeric(shelfId));
            }
        }
        return null;
    }

    public boolean changeProduct(List<String> inputs) {
        if (isNumeric(inputs.get(0)) && isNumeric(inputs.get(1)))
            try {
                changeProduct(asNumeric(inputs.get(0)), asNumeric(inputs.remove(1)), new Product(inputs));
                return true;
            } catch (Exception e) {
                return false;
            }
        return false;
    }

    public Product takeFromShelf(List<String> inputs) {
        String prodId, shelfId;
        if (inputs.size() >= 2) {
            prodId = inputs.remove(0);
            shelfId = inputs.remove(0);
            if (isNumeric(prodId) && isNumeric(shelfId)) {
                return takeFromShelf(asNumeric(prodId), asNumeric(shelfId));
            }
        }
        return null;
    }

    private boolean isDataContainerSpecified() {
        return shelfDataSource != null;
    }

    private boolean syncWithDataSource() {
        Pair<Collection<Shelf>, Collection<Salesman>> readed = shelfDataSource.fetchAll();
        if (readed != null) {
            shelves = readed.getKey();
            workers = readed.getValue();
            if (workers == null)
                workers = new LinkedList<>();
            if (shelves == null)
                shelves = new LinkedList<>();
        }
        return readed != null;
    }

    public boolean setDataContainer(String src) {
        DAOFactory.DataContainers container = DAOFactory.DataContainers.getContainer(src);
        if (container != null) {
            try {
                shelfDataSource = DAOFactory.getShelfDAO(container);
                prodDataSource = DAOFactory.getProductDAO(container);
            } catch (Exception e) {
                return false;
            }
            return syncWithDataSource();
        }
        return false;
    }


    public boolean createWorker(List<String> inputs) {
        if (isNumeric(inputs.get(0)))
            if (isUnique(asNumeric(inputs.get(0)))) {
                try {
                    return createWorker(new Salesman(inputs));
                } catch (Exception e) {
                    return false;
                }
            }
        return false;
    }


    public String deleteWorker(List<String> inputs) {
        if (isNumeric(inputs.get(0)))
            if (isUnique(asNumeric(inputs.get(0)))) {
                try {
                    return deleteWorker(Integer.parseInt(inputs.get(0)));
                } catch (Exception e) {
                    return null;
                }
            }
        return null;
    }

    @Override
    public String checkWorkers() {
        return retrieveAll(workers);
    }

    public String checkWorker(List<String> inputs) {
        try {
            if (!inputs.isEmpty())
                return checkWorker(Integer.parseInt(inputs.get(0))).toString();
        } catch (NumberFormatException e) {
            return null;
        }
        return null;
    }


    public boolean assignWorker(List<String> inputs) {
        if (inputs.size() >= 2)
            try {
                return assignWorker(Integer.parseInt(inputs.get(0)), Integer.parseInt(inputs.get(1)));
            } catch (NumberFormatException e) {
                return false;
            }
        return false;
    }

    private boolean assignWorker(int stallId, int salesmanId) {
        Stall stall = null;
        Salesman worker = checkWorker(salesmanId);
        try {
            stall = (Stall) checkShelf(stallId);
            stall.setAssignedWorker(worker);
            worker.setAssignedPlace(stall);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean retractWorkerBySalesmanId(List<String> inputs) {
        if (!inputs.isEmpty())
            try {

                return retractWorkerBySalesmanId(Integer.parseInt(inputs.get(0)));
            } catch (NumberFormatException e) {
                return false;
            }
        return false;
    }
    public boolean retractWorkerByStallId(List<String> inputs) {
        if (!inputs.isEmpty())
            try {

                return retractWorkerByStallId(Integer.parseInt(inputs.get(0)));
            } catch (NumberFormatException e) {
                return false;
            }
        return false;
    }

    private boolean retractWorkerByStallId(int stallId) {
        Stall stall = (Stall)checkShelf(stallId);
        try {
            stall.getAssignedWorker().setAssignedPlace(null);
            stall.setAssignedWorker(null);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean retractWorkerBySalesmanId(int salesmanId) {
        Salesman salesman = checkWorker(salesmanId);
        try {
            salesman.getAssignedPlace().setAssignedWorker(null);
            salesman.setAssignedPlace(null);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

  }
