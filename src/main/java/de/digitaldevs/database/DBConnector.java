package de.digitaldevs.database;

import java.sql.Connection;

/**
 * This class is an abstract base for all database connectors.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0
 */
public interface DBConnector {

    /**
     * Abstract method for connection to a database
     */
    void openConnection();

    /**
     * Abstract method for closing a database connection
     */
    void closeConnection();

    /**
     * Abstract method for checking if a connection to a database is currently existent.
     *
     * @return {@code true} if a valid connection to the database exists, or {@code false} if no or a invalid connection to the database exists
     */
    boolean isConnected();

    Connection getConnection();

}
