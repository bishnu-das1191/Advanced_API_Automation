package com.database;

import com.api.utils.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager_OLD {

    private static final String DB_URL = ConfigManager.getProperty("DB_URL");
    private static final String DB_USERNAME = ConfigManager.getProperty("DB_USER_NAME");
    private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");

    // Any update that happens to this connection variable, all the Threads will be aware of it.
    // when we have requirement of multiple threads accessing and updating the same variable then we should use volatile keyword
    private volatile static Connection connection;

    private DatabaseManager_OLD() {
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

    public static void createConnection() throws SQLException {

        // Double-checking locking Design Pattern :-
        // This will improve the performance as well as thread safety

        // explain double-checked locking here :
        // First check without locking, if connection is already created then no need to enter synchronized block
        // if connection is null then only enter synchronized block
        // Inside synchronized block, check again if connection is null, if yes then create connection
        // This will ensure that only one thread creates the connection
        // Other threads will not enter synchronized block if connection is already created
        // This will reduce the overhead of synchronization


        if(connection == null ) { // First check where all the parallel threads will come here
            synchronized (DatabaseManager_OLD.class) {
                if(connection == null) {
                    connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                    System.out.println("Connection established successfully: " + !connection.isClosed());
                }
            }
        }

        // what was the problem in earlier code without double checked locking in detail?
        // Ans : In the earlier code without double-checked locking, every time a thread called the createConnection() method,
        // it had to wait for the synchronized block to be free, even if the connection was already established.
        // This created a bottleneck, especially when multiple threads were trying to access the method simultaneously.
        // With double-checked locking, the first check outside the synchronized block allows threads to quickly determine
        // if the connection is already established. If it is, they can proceed without waiting.

    }
}
