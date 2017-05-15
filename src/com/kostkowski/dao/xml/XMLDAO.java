package com.kostkowski.dao.xml;

import com.kostkowski.model.Salesman;
import com.kostkowski.model.Shelf;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Grzegorz Kostkowski on 2017-03-11.
 */
public abstract class XMLDAO {
    protected XStream xstream = new XStream(new DomDriver());
    protected String xml;
    protected static final String filepath = "shop.xml";
    private Collection<Shelf> shelves;
    private Collection<Salesman> workers;

    public boolean toFile(Pair<Collection<Shelf>, Collection<Salesman>> collection) {
        setAliases();
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(filepath, false));
            xml = xstream.toXML(new DataWrapper(collection));
            if (xml == null || xml.length() == 0)
                return false;

            fw.write(xml);
            fw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Pair<Collection<Shelf>, Collection<Salesman>> fromFile() {
        setAliases();
        Collection<Shelf> shelves = new ArrayList<>();
        Collection<Salesman> workers = new ArrayList<>();
        try {
            File file = new File(filepath);
            if (!file.exists())
                file.createNewFile();
            else {
                xml = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
                return ((DataWrapper) xstream.fromXML(xml)).getData();
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    /*public boolean toFile(Collection<Shelf> collection) {
        setAliases();
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(filepath, false));
            xml = xstream.toXML(new DataWrapper(collection));
            if (xml == null || xml.length() == 0)
                return false;

            fw.write(xml);
            fw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Collection<Shelf> fromFile() {
        setAliases();
        Collection<Shelf> collection = new ArrayList<>();
        try {
            File file = new File(filepath);
            if (!file.exists())
                file.createNewFile();
            else {
                xml = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
                collection = ((DataWrapper) xstream.fromXML(xml)).getData();
            }
            return collection;
        } catch (IOException e) {
            return null;
        }
    }*/

    protected void setAliases() {
        xstream.alias("shelf", ShelfWrapper.class);
        xstream.alias("shelves", DataWrapper.class);
    }


    public Pair<Collection<Shelf>, Collection<Salesman>> fetchAll() {
//        shelves = fromFile(); //TODO
        Pair<Collection<Shelf>, Collection<Salesman>> readed = fromFile();
        workers = readed.getValue();
        shelves = readed.getKey();
        return readed;
        //return new Pair(shelves, workers);
    }

    public boolean updateXML(Collection<Shelf> shelves, Collection<Salesman> workers) {
        return toFile(new Pair<Collection<Shelf>, Collection<Salesman>>(shelves, workers)); //TODO dodaj obsluge workers w wpisywaniu do xmla
    }

}
