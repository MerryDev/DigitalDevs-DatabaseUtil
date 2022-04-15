package de.digitaldevs.database.mysql;

import de.digitaldevs.database.utils.SQLBuilder;

/**
 * @author MerryChrismas
 * @author <a href="https://digitaldevs.de">https://digitaldevs.de</a>DigitalDevs
 * @version 1.0.0
 */
public class MySQLHandler extends MySQLConnector {

    public MySQLHandler(String host, String port, String database, String user, String password, boolean useSSL) {
        super(host, port, database, user, password, useSSL);
    }

    public SQLBuilder createBuilder(String query) {
        if (!this.isConnected()) {
            System.out.println("The library has no connection to a database!");
            return null;
        }
        return new SQLBuilder(query, this.getConnection());
    }

}
