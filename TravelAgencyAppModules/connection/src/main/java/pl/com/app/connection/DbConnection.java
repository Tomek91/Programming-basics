package pl.com.app.connection;


import pl.com.app.exceptions.MyException;
import pl.com.app.sqlbuilder.creator.SqlTableCreator;
import pl.com.app.sqlbuilder.types.Types;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection implements AutoCloseable {
    private static DbConnection ourInstance = new DbConnection();

    public static DbConnection getInstance() {
        return ourInstance;
    }

    private final static String DRIVER = "org.sqlite.JDBC";
    private final static String DB_URL = "jdbc:sqlite:connection/Trips.db";

    private Connection connection;

    private DbConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (Exception e) {
            throw new MyException("CONNECTION ERROR");
        }
    }

    public static Connection connection() {
        return DbConnection.getInstance().getConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            throw new MyException("CLOSE CONNECTION ERROR");
        }
    }

    private void createTables() throws SQLException {

        Statement statement = connection.createStatement();

        final String sqlTrip = new SqlTableCreator
                .SqlTableCreatorBuilder()
                .tableName("trip")
                .addPrimaryKey("id")
                .addColumn("destination", Types.VARCHAR).maxSize(50).isNotNull()
                .addColumn("price", Types.DOUBLE).isNotNull()
                .addColumn("people_number", Types.INTEGER).isNotNull()
                .addColumn("travel_agency_id", Types.INTEGER).isNotNull()
                .build();

        statement.execute(sqlTrip);

    }
}
