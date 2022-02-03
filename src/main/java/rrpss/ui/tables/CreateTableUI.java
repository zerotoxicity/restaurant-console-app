package rrpss.ui.tables;

import rrpss.entities.Table;
import rrpss.ui.UI;
import rrpss.util.Crypto;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Boundary class for creating tables
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class CreateTableUI extends UI {
    /**
     * Form for manually adding tables
     */
    public void form() {
        print("Enter number of seats: ");
        int seats = promptInt(2, 10, (Integer i) -> {
            if (i % 2 != 0) return "Only even numbers are allowed";
            return "";
        });
        TableController.getInstance().createTable(seats, TableController.getInstance().getTables().size() + 1);
    }

    /**
     * Create table based on predefined template
     */
    public void bulkCreate() {
        ArrayList<Table> tables = new ArrayList(
                Arrays.asList(
                        new Table(Crypto.genUUID(), 1, 2),
                        new Table(Crypto.genUUID(), 2, 4),
                        new Table(Crypto.genUUID(), 3, 4)
//                        new Table(Crypto.genUUID(), 4,8),
//                        new Table(Crypto.genUUID(), 5,10),
//                        new Table(Crypto.genUUID(), 6,2),
//                        new Table(Crypto.genUUID(), 7, 4),
//                        new Table(Crypto.genUUID(), 8, 6),
//                        new Table(Crypto.genUUID(), 9,8)
//                        new Table(Crypto.genUUID(), 10),
//                        new Table(Crypto.genUUID(), 2),
//                        new Table(Crypto.genUUID(), 4),
//                        new Table(Crypto.genUUID(), 6),
//                        new Table(Crypto.genUUID(), 8),
//                        new Table(Crypto.genUUID(), 10),
//                        new Table(Crypto.genUUID(), 2),
//                        new Table(Crypto.genUUID(), 4),
//                        new Table(Crypto.genUUID(), 6),
//                        new Table(Crypto.genUUID(), 8),
//                        new Table(Crypto.genUUID(), 10)
                )
        );
        TableController.getInstance().bulkCreateTable(tables);
    }

    /**
     * Options for adding table
     */
    public void options() {
        println("[0] Bulk Create");
        println("[1] Add Table");
        int option = promptInt(true, 0, 1);
        switch (option) {
            case 0:
                bulkCreate();
                break;
            default:
                form();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        subtitle("Create Tables");
        options();
    }
}
