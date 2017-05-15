package com.kostkowski.model;

import java.util.List;

/**
 * Created by Grzesiek on 2017-03-18.
 */
public class Stall extends Shelf {
    private Salesman assignedWorker;

    public Stall(int shelfId, int capacity, int maxCapacity, String cat, Salesman assignedWorker) {
        super(shelfId, capacity, maxCapacity, cat);
        this.assignedWorker = assignedWorker;
    }
    public Stall(int shelfId, int maxCapacity, String cat, Salesman assignedWorker) {
        super(shelfId, maxCapacity, cat);
        this.assignedWorker = assignedWorker;
    }

    public Stall(List<String> inputs, Salesman assignedWorker) {
        this(Integer.parseInt(inputs.get(0)), Integer.parseInt(inputs.get(0)), inputs.get(0), assignedWorker);
    }

    public Salesman getAssignedWorker() {
        return assignedWorker;
    }

    public void setAssignedWorker(Salesman assignedWorker) {
        this.assignedWorker = assignedWorker;
    }

    @Override
    public String toString() {
        return super.toString()+"\t"+(assignedWorker != null ? "nr_pracownika: "+assignedWorker.getSalesmanId() : "");
    }
}
