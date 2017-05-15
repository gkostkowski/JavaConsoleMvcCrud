package com.kostkowski.model;

import javax.naming.SizeLimitExceededException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Grzegorz Kostkowski on 2017-03-04.
 */
public class Shelf implements ICRUD<Integer, Product> {
    private int shelfId;
    private int capacity = 0,
            maxCapacity;
    private String category;

    private Collection<Product> products;

    public Shelf(){}
    public Shelf(List<String> inputs) {
        this(Integer.parseInt(inputs.remove(0)), Integer.parseInt(inputs.remove(0)), inputs.remove(0));
    }
    public Shelf(int shelfId, int capacity, String cat) {
        products = new LinkedList<Product>();
        setShelfId(shelfId);
        setMaxCapacity(capacity);
        setCategory(cat);
    }
    public Shelf(int shelfId, int capacity, int maxCapacity, String cat) {
        products = new LinkedList<Product>();
        setShelfId(shelfId);
        setCapacity(capacity);
        setMaxCapacity(maxCapacity);
        setCategory(cat);
    }
    public Shelf(int shelfId, int capacity, int maxCapacity, String cat, Collection<Product> prods) {
        products = new LinkedList<Product>();
        setShelfId(shelfId);
        setCapacity(capacity);
        setMaxCapacity(maxCapacity);
        setCategory(cat);
        this.products = prods;
    }

    public int getShelfId() {
        return shelfId;
    }
    public void setShelfId(int shelfId) {
        if (shelfId > 0)
            this.shelfId = shelfId;
        else throw new IllegalStateException("Identyfikator musi byc liczba wieksza od 0!");
    }

    public int getCapacity() {
        return capacity;
    }
    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setCapacity(int capacity) {
        if (capacity >= 0)
            this.capacity = capacity;
        else throw new IllegalStateException("Aktualna zajetosc musi byc wieksza lub rowna 0!");
    }
    public void setMaxCapacity(int capacity) {
        if (capacity > 0)
            this.maxCapacity = capacity;
        else throw new IllegalStateException("Pojemnosc musi byc wieksza od 0!");
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        if (!category.isEmpty())
            this.category = category;
        else throw new IllegalStateException("Wprowadzono pusty lancuch znakowy!");
    }

    public boolean hasProducts() {
        return products != null;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }


    @Override
    public void create(Product newElem) throws SizeLimitExceededException {
        if (freeSlotExists()) {
            products.add(newElem);
            capacity++;
        }
        else throw new SizeLimitExceededException("Polka zapelniona, brak miejsca na polce!");
    }

    public boolean freeSlotExists() {
        return capacity < maxCapacity;
    }

    @Override
    public void delete(Integer elem) {
        remove(elem);
    }

    @Override
    public Product remove(Integer elem) {
        Product removed;
        products.remove(removed=get(elem));
        capacity = capacity>0 ? capacity - 1 : 0;
        return removed;
    }

    @Override
    public void update(Integer id, Product elem) throws SizeLimitExceededException {
        remove(id);
        create(elem);
    }

    @Override
    public Product get(Integer elem) {
        Product res = null;
        for (Product p:products) {
            if (p.getId().equals(elem))
                res = p;
        }
        return res;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "polka nr "+shelfId+":\tpojemnosc: "+capacity+"/"+maxCapacity+"\tkategoria: "+category;
    }
}
