package de.digitaldevs.database.utils;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * This class is a utility class for handling sql queries much more easier.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class SQLBuilder {

    private final Connection connection;
    private final List<Object> parameters;
    private final String query;

    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(50);

    public SQLBuilder(String query, Connection connection) {
        this.parameters = new ArrayList<>();
        this.connection = connection;
        this.query = query;
    }

    public SQLBuilder addParameter(Object parameter) {
        this.parameters.add(parameter);
        return this;
    }

    public SQLBuilder addParameters(Object... parameters) {
        this.parameters.addAll(Arrays.asList(parameters));
        return this;
    }

    public void updateSync() {
        this.executeSync(false);
    }

    public ResultSet querySync() {
        return this.executeSync(true);
    }

    public void updateAsync() {
        this.executeAsync(null, false);
    }

    public void updateAsync(final Runnable callback) {
        this.executeAsync(resultSet -> callback.run(), false);
    }

    public void queryAsync(Consumer<ResultSet> callback) {
        this.executeAsync(callback, true);
    }

    private ResultSet executeSync(boolean isQuery) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(this.query);
            for (int i = 0; i < this.parameters.size(); i++) statement.setObject(i + 1, this.parameters.get(i));

            ResultSet result;

            if (!isQuery) {
                statement.executeUpdate();
                return null;
            }

            result = statement.executeQuery();
            return result;

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private void executeAsync(Consumer<ResultSet> callback, boolean isQuery) {
        THREAD_POOL.execute(() -> {
            ResultSet result = this.executeSync(isQuery);
            if (callback != null) callback.accept(result);
        });
    }

    private void close(CachedRowSet cachedRowSet, ResultSet result) throws SQLException {
        if (cachedRowSet != null) cachedRowSet.close();
        if (result != null) result.close();
    }

}
