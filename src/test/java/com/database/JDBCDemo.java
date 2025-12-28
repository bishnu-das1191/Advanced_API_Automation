package com.database;

import java.sql.*;

public class JDBCDemo {

    public static void main(String[] args) throws SQLException {

        // Step 1: Establish a connection to the application database

        // DriverManager has used Factory Design Pattern
        Connection conn =  DriverManager.getConnection(
                "jdbc:mysql://64.227.160.186:3306/SR_DEV",
                "srdev_ro_automation",
                "Srdev@123");

        System.out.println("Connection established successfully: " + !conn.isClosed());

        Statement statement = conn.createStatement();
        ResultSet resultSet =  statement.executeQuery("SELECT first_name ,last_name ,mobile_number from tr_customer;");

        while(resultSet.next()){
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String mobileNumber = resultSet.getString("mobile_number");

            System.out.println(firstName + " | " + lastName +  " | " + mobileNumber);
        }


    }
}
