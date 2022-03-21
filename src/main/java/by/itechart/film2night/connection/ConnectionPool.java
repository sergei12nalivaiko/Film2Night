package by.itechart.film2night.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static ConnectionPool instance;
    private BlockingQueue<ProxyConnection> availableСonnections;
    private BlockingQueue<ProxyConnection> usedСonnections;
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static AtomicBoolean create = new AtomicBoolean(false);
    private static final String POOL_SIZE = "db.pool_size";
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DB_CONNECTION_PATH = "database.properties";

    private ConnectionPool() {
        LOGGER.info("in connection pool constructor");
        init();
    }

    private void init(){
        //
        LOGGER.info("in connection pool init");
        Properties dbProperties = new Properties();
        try {
            dbProperties.load(ConnectionFactory.class.getClassLoader().getResourceAsStream(DB_CONNECTION_PATH));
            int poolSize = Integer.valueOf(dbProperties.getProperty(POOL_SIZE));
            availableСonnections = new LinkedBlockingDeque<>(poolSize);
            usedСonnections = new LinkedBlockingDeque<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                ProxyConnection connection = ConnectionFactory.createConnection(dbProperties);
                availableСonnections.add(connection);
            }
        } catch (IOException e) {
            LOGGER.error("Unable to load DB properties!", e);
            e.printStackTrace();
        }
        LOGGER.info("Connection pool initialized = " + availableСonnections.size());
    }

    public static ConnectionPool getInstance(){
        LOGGER.info("in connection pool getInstance");
        if(!create.get()) {
            try {
                reentrantLock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    create.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    public Connection takeConnection(){
        LOGGER.info("in connection pool takeConnection");
        ProxyConnection connection = null;
        try {
            connection = availableСonnections.take();
            usedСonnections.put(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection){
        if (connection instanceof ProxyConnection && usedСonnections.remove(connection)) {
            try {
                availableСonnections.put((ProxyConnection) connection);
            } catch (InterruptedException e) {
                LOGGER.error("InterruptedException in method releaseConnection " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }



    public void closePool() throws SQLException, InterruptedException {

        for (int i = 0; i < availableСonnections.size(); i++) {
            availableСonnections.take().realClose();
        }
        deregisterDrivers();
    }

    private static void deregisterDrivers() {
        LOGGER.trace("unregistering sql drivers");
        final Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                LOGGER.error("could not deregister driver", e);
            }
        }
    }

}
