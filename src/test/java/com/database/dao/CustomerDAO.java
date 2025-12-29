package com.database.dao;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO {
    //Executing the query for the tr_customer table! which will get the details of the customer!

    private static final String CUSTOMER_DETAIL_QUERY =
            """
                  SELECT * from tr_customer WHERE id=141277;
            """;

    public static CustomerDBModel getCustomerInfo() throws SQLException {
        Connection connection = DatabaseManager.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(CUSTOMER_DETAIL_QUERY);
        CustomerDBModel customerDBModel = null;
        while(resultSet.next()) {
            customerDBModel = new CustomerDBModel(resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("mobile_number"),
                    resultSet.getString("mobile_number_alt"),
                    resultSet.getString("email_id"),
                    resultSet.getString("email_id_alt")
            );

        }
        return customerDBModel;
    }
}
