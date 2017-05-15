package com.kostkowski.dao.xml;

import com.kostkowski.model.Shelf;

/**
 * Created by Grzegorz Kostkowski on 2017-03-12.
 */
class ShelfWrapper {
    private int shelfId;
    private int capacity = 0,
            maxCapacity;
    private String category;

    ShelfWrapper() {}
    ShelfWrapper(Shelf shelf){
        this.shelfId = shelf.getShelfId();
        this.capacity = shelf.getCapacity();
        this.maxCapacity = shelf.getMaxCapacity();
        this.category = shelf.getCategory();
    }
}
