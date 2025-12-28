package com.database;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HikariCPDemo {

    public static void main(String[] args) throws SQLException {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(ConfigManager.getProperty("DB_URL"));
        hikariConfig.setUsername(ConfigManager.getProperty("DB_USER_NAME"));
        hikariConfig.setPassword(ConfigManager.getProperty("DB_PASSWORD"));
        // one of the major benifit of HikariCP is we can set the maximum pool size
        hikariConfig.setMaximumPoolSize(10); // set max 10 connections in the pool, meaning at max 10 connections can be created in the pool
        hikariConfig.setMinimumIdle(2); // set min 2 idle connections in the pool, meaning even if there are no requests, 2 connections will be kept alive
        hikariConfig.setConnectionTimeout(10000); //set connection timeout to 10 seconds, meaning if a connection is not available in the pool within 10 seconds then it will throw an exception
        hikariConfig.setIdleTimeout(10000); // set idle timeout to 10 seconds, meaning if a connection is idle for 10 seconds it will be removed from the pool
        hikariConfig.setMaxLifetime(1800000); // set max lifetime to 30 minutes, meaning after 30 minutes the connection will be removed from the pool
        hikariConfig.setPoolName("Phoenix Test Automation Framework Pool");

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        Connection connection = dataSource.getConnection();
        System.out.println("Connection established successfully: " + !connection.isClosed());

        Statement statement = connection.createStatement();
        // Execute your queries using the statement object to get results
        //ResultSet resultSet =  statement.executeQuery("SELECT first_name ,last_name ,mobile_number from tr_customer ORDER BY customer_id DESC LIMIT 10;");
        ResultSet resultSet =  statement.executeQuery("SELECT first_name ,last_name ,mobile_number from tr_customer;");
        while(resultSet.next()){
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String mobileNumber = resultSet.getString("mobile_number");

            System.out.println(firstName + " | " + lastName +  " | " + mobileNumber);
        }

        dataSource.close();
    }
}
