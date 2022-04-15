package de.digitaldevs.database.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

/**
 * This class is a manager for handling mysql database connections. <br><br>
 * Note: This class is outdated and should <b>NOT</b> be used anymore. <br> It has security flaws related to SQL injections.
 * Please use another instance like {@link MySQLHandler} for handling database connections. <br><br>
 *
 * @author MerryChrismas
 * @author <a href="https://digitaldevs.de">https://digitaldevs.de</a>DigitalDevs.de
 * @version 1.0
 */
@Deprecated
public class MySQLManager extends MySQLConnector {

    /**
     * Creates a new instance for handling operations with a MySQL-Database.
     *
     * @param host     the host of the database
     * @param port     the port of the database
     * @param database the name of the database
     * @param user     the user which should and can access the database
     * @param password the users' password
     * @param useSSL   should the connection be protected with ssl?
     */
    public MySQLManager(String host, String port, String database, String user, String password, boolean useSSL) {
        super(host, port, database, user, password, useSSL);
    }

    /**
     * Updates the database asynchronous.
     *
     * @param sql the query witch should be executed.
     */
    public void updateAsync(String sql) {
        new Thread(() -> updateSync(sql)).start();
    }

    /**
     * Updates the database synchronously.
     *
     * @param sql the query witch should be executed.
     */
    public void updateSync(String sql) {
        try {
            getConnection().prepareStatement(sql).executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Gets data from the database asynchronously.
     *
     * @param sql      the query witch should be executed
     * @param consumer new instance for handling operations due this query
     * @return the result of the query
     */
    public ResultSet getResultAsync(String sql, Consumer<ResultSet> consumer) {
        new Thread(() -> consumer.accept(getResultSync(sql)));
        return null;
    }

    /**
     * Gets data from the database synchronously.
     *
     * @param sql the query witch should be executed
     * @return the result of the query
     */
    public ResultSet getResultSync(String sql) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
