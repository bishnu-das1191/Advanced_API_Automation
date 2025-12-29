package com.database;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String DB_URL = ConfigManager.getProperty("DB_URL");
    private static final String DB_USERNAME = ConfigManager.getProperty("DB_USER_NAME");
    private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");

    private static final int MAXIMUM_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
    private static final int MINIMUM_IDLE_CONNECTIONS = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE_CONNECTIONS"));
    private static final int CONNECTION_TIMEOUT_IN_SECS = Integer.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SECS"));
    private static final int IDLE_TIMEOUT_IN_SECS = Integer.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT_IN_SECS"));
    private static final int MAX_LIFETIME_IN_MINS = Integer.parseInt(ConfigManager.getProperty("MAX_LIFETIME_IN_MINS"));
    private static final String HIKARICP_POOL_NAME = ConfigManager.getProperty("HIKARICP_POOL_NAME");


    private static HikariConfig hikariConfig;
    private volatile static HikariDataSource hikariDataSource;
    private static Connection connection;

    private DatabaseManager() {
        // private constructor to prevent instantiation
    }

    // in order to make Thread safe we can use synchronized keyword,
    // this will ensure that only one thread can access this method at a time
    // e.g when more than 1 api calls are made simultaneously or parallel then only one thread can create connection at a time

    /*public synchronized static void createConnection() throws SQLException {
        // Code to create a database connection
        if(connection == null) {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connection established successfully: " + !connection.isClosed());
        }
    }*/

    private static void initializePool() {

        // Double-checking locking Design Pattern :-
        // This will improve the performance as well as thread safety

        // explain double-checked locking here :
        // First check without locking, if connection is already created then no need to enter synchronized block
        // if connection is null then only enter synchronized block
        // Inside synchronized block, check again if connection is null, if yes then create connection
        // This will ensure that only one thread creates the connection
        // Other threads will not enter synchronized block if connection is already created
        // This will reduce the overhead of synchronization


        if(hikariDataSource == null ) { // First check where all the parallel threads will come here
            synchronized (DatabaseManager.class) {
                if(hikariDataSource == null) {

                    HikariConfig hikariConfig = new HikariConfig();
                    hikariConfig.setJdbcUrl(ConfigManager.getProperty("DB_URL"));
                    hikariConfig.setUsername(ConfigManager.getProperty("DB_USER_NAME"));
                    hikariConfig.setPassword(ConfigManager.getProperty("DB_PASSWORD"));
                    // one of the major benefit of HikariCP is we can set the maximum pool size
                    hikariConfig.setMaximumPoolSize(MAXIMUM_POOL_SIZE); // set max 10 connections in the pool, meaning at max 10 connections can be created in the pool
                    hikariConfig.setMinimumIdle(MINIMUM_IDLE_CONNECTIONS); // set min 2 idle connections in the pool, meaning even if there are no requests, 2 connections will be kept alive
                    hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT_IN_SECS * 1000); //set connection timeout to 10 seconds, meaning if a connection is not available in the pool within 10 seconds then it will throw an exception
                    hikariConfig.setIdleTimeout(IDLE_TIMEOUT_IN_SECS * 1000); // set idle timeout to 10 seconds, meaning if a connection is idle for 10 seconds it will be removed from the pool
                    hikariConfig.setMaxLifetime(MAX_LIFETIME_IN_MINS * 60 * 1000); // set max lifetime to 30 minutes, meaning after 30 minutes the connection will be removed from the pool
                    hikariConfig.setPoolName(HIKARICP_POOL_NAME);

                    hikariDataSource = new HikariDataSource(hikariConfig);

                }
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        if (hikariDataSource == null) {
            initializePool();
        } else if (hikariDataSource.isClosed()) {
            throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
        }

        connection = hikariDataSource.getConnection();

        return connection;
    }
}
