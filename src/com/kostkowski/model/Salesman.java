package com.kostkowski.model;

import java.util.List;

/**
 * Created by Grzesiek on 2017-03-18.
 */
public class Salesman implements Identifiable<Integer> {
    private String firstName;
    private String lastName;
    private int salesmanId;
    private Stall assignedPlace = null;

    public Salesman(String firstName, String lastName, int salesmanId, Stall assignedPlace) {
        setFirstName(firstName);
        setLastName(lastName);
        setSalesmanId(salesmanId);
        setAssignedPlace(assignedPlace);
    }


    @Override
    public String toString() {
        return "Sprzedawca nr " +salesmanId +":"+
                firstName + " " +
                lastName + ", " +
                "przypisane stoisko: " + (assignedPlace == null? "brak": assignedPlace.getShelfId()+"") ;
    }

    public Salesman(int salesmanId, String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
        setSalesmanId(salesmanId);

    }

    public Salesman(List<String> inputs) {
        this(Integer.parseInt(inputs.get(0)), inputs.get(1), inputs.get(2) );
    }

    public Stall getAssignedPlace() {
        return assignedPlace;
    }

    public void setAssignedPlace(Stall assignedPlace) {
        if (assignedPlace == null)
            throw new IllegalStateException("Brak wskazanego stanowiska sprzedawczego.");
        this.assignedPlace = assignedPlace;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName.isEmpty())
            throw new IllegalStateException("Niepoprawne imie!");
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName.isEmpty())
            throw new IllegalStateException("Niepoprawne nazwisko!");
        this.lastName = lastName;
    }

    public int getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(int salesmanId) {
        if (salesmanId <= 0)
            throw new IllegalStateException("Niepoprawny identyfikator!");
        this.salesmanId = salesmanId;
    }

    @Override
    public boolean equals(Object o) {
        return ((Salesman)o).getSalesmanId() == this.getSalesmanId();
    }

    @Override
    public int hashCode() {
        return getSalesmanId();
    }

    @Override
    public Integer getId() {
        return getSalesmanId();
    }


}
