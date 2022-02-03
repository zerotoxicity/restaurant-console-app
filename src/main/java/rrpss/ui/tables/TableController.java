package rrpss.ui.tables;

import rrpss.entities.Reservation;
import rrpss.entities.Table;
import rrpss.ui.RouteMap;
import rrpss.ui.app.Back;
import rrpss.util.Crypto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Control class for table management
 *
 * @author SSP4 Group 1
 * @version 1.0
 * @since 2021-10-26
 */
public class TableController {
    private static TableController INSTANCE;
    private final long gracePeriod = 5;


    /**
     * Class constructor
     */
    private TableController() {
    }

    public static TableController getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TableController();
        // Remove expired reservations
        INSTANCE.removeExpiredReservations();
        return INSTANCE;
    }

    public ArrayList<Table> getTables() {
        return Table.getTables();
    }

    public long getGracePeriod() {
        return gracePeriod;
    }

    public Table getTable(String id) {
        ArrayList<Table> tables = getTables();
        for (Table table : tables) {
            if (table.getID().equalsIgnoreCase(id))
                return table;
        }
        return null;
    }


    /**
     * Get table reservations by specifying date
     *
     * @param table
     * @return returns reservations of table by date
     */
    public ArrayList<Reservation> getReservationsByDate(Table table, LocalDate date) {
        ArrayList<Reservation> reservationsToday = new ArrayList();
        for (Reservation reservation : table.getCurrentReservations()) {
            if (!reservation.getFrom().toLocalDate().isEqual(date)) continue;
            reservationsToday.add(reservation);
        }
        return reservationsToday;
    }

    /**
     * Gets available tables from a specified time frame
     *
     * @param seats
     * @param from
     * @param to
     * @return returns available tables specified from the time frame
     */
    public ArrayList<Table> getAvailableTables(int seats, LocalDateTime from, LocalDateTime to) {
        ArrayList<Table> tables = new ArrayList();
        for (Table table : getTables()) {
            // Check if table can fill capacity
            if (table.getSeats() < seats) continue;
            if (!table.isReserved(from, to))
                tables.add(table);
        }
        return tables;
    }

    /**
     * Gets tables by pax
     *
     * @param seats
     * @return returns tables
     */
    public ArrayList<Table> getAvailableTables(int seats) {
        ArrayList<Table> tables = new ArrayList();
        for (Table table : getTables()) {
            // Check if table can fill capacity
            if (table.getSeats() < seats) continue;
            tables.add(table);
        }
        return tables;
    }

    /**
     * Removes expired reservations from tables
     */
    public void removeExpiredReservations() {
        ArrayList<Table> tables = getTables();
        for (Table table : tables) {
            table.removeExpiredReservation(gracePeriod);
        }
    }

    /**
     * Retrieves tables which are occupied by reservations
     *
     * @return
     */
    public ArrayList<Table> getOccupiedTables() {
        ArrayList<Table> tables = this.getTables();
        ArrayList<Table> occupiedTables = new ArrayList<>();
        for (Table table : tables)
            if (table.getCurrentReservation() != null)
                occupiedTables.add(table);
        return occupiedTables;
    }

    /**
     * Create tables using a predefined template
     *
     * @param tables template of tables
     */
    public void bulkCreateTable(ArrayList<Table> tables) {
        Table.set(tables);
    }

    /**
     * Add table to the database
     *
     * @param seats  number of seats
     * @param number table number
     */
    public void createTable(int seats, int number) {
        new Table(Crypto.genUUID(), number, seats).save();
    }


}