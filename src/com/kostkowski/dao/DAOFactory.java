package com.kostkowski.dao;

import com.kostkowski.dao.db.DBProductDAO;
import com.kostkowski.dao.db.DBShelfDAO;
import com.kostkowski.dao.xml.XMLProductDAO;
import com.kostkowski.dao.xml.XMLShelfDAO;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;

/**
 * Created by Grzegorz Kostkowski on 2017-03-10.
 */
public class DAOFactory {
    public enum DataContainers {
        DB("bd"), XML("xml");

        private String val;

        DataContainers(String v) {
            this.val = v;
        }

        @Nullable
        public static DAOFactory.DataContainers getContainer(String v) {
            for(DataContainers a: DataContainers.values())
                if (a.val.equals(v))
                    return a;
            return null;
        }
    }

    @Nullable
    public static IShelfDAO getShelfDAO(DataContainers sourceType) throws SQLException {
        if (sourceType.equals(DataContainers.DB))
            return new DBShelfDAO();
        else if (sourceType.equals(DataContainers.XML))
            return new XMLShelfDAO();
        return null;
    }

    @Nullable
    public static IProductDAO getProductDAO(DataContainers sourceType) throws SQLException {
        if (sourceType.equals(DataContainers.DB))
            return new DBProductDAO();
        else if (sourceType.equals(DataContainers.XML))
            return new XMLProductDAO();
        return null;
    }
}
