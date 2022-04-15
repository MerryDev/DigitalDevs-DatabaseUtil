package de.digitaldevs.database.mysql;

import de.digitaldevs.database.DBConnector;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RequiredArgsConstructor
public class MySQLConnector implements DBConnector {

    private final String host, port, database, user, password;
    private final boolean useSSL;
    private Connection connection;

    /**
     * Creates a new connection to the database.
     */
    @Override
    public void openConnection() {
        try {
            if (useSSL) {
                this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8", this.user, this.password);
            } else {
                this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false", this.user, this.password);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Closes the connection.
     */
    @Override
    public void closeConnection() {
        try {
            if (this.isConnected()) {
                this.connection.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Checks if a valid connection to a database exists.
     *
     * @return true if a connections exists; false if not
     */
    @Override
    public boolean isConnected() {
        return this.connection != null;
    }

    /**
     * Get the current connection to the database.
     *
     * @return the connection
     */
    @Override
    public Connection getConnection() {
        return this.connection;
    }
}
