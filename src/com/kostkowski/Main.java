package com.kostkowski;

import com.kostkowski.controller.Controller;
import com.kostkowski.model.Shop;
import com.kostkowski.view.View;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
 /*       Shop model = new Shop();
	    View view = new View(model);
        Controller controller = new Controller(model, view);
*/
        System.out.println(String.class.getSimpleName());
        System.out.println(Integer.class.getSimpleName());
        System.out.println(Boolean.class.getSimpleName());
    }
}
