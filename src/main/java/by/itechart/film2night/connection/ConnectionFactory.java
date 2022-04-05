package by.itechart.film2night.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_DRIVER = "db.driver";

    static ProxyConnection createConnection(Properties dbProperties) {
        ProxyConnection proxyConnection = null;
        try {
            String dbUrl = dbProperties.getProperty(DB_URL);
            String dbUser = dbProperties.getProperty(DB_USER);
            String dbPassword = dbProperties.getProperty(DB_PASSWORD);
            Class.forName(dbProperties.getProperty(DB_DRIVER));
            proxyConnection = new ProxyConnection(DriverManager.getConnection(dbUrl, dbUser, dbPassword));
        } catch (SQLException e) {
            LOGGER.error("Unable to connect to DB!", e);

        } catch (ClassNotFoundException e) {
            LOGGER.error("MySQL JDBC driver not found!", e);

        }
        return proxyConnection;
    }
}

