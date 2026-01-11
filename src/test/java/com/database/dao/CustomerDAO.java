package com.database.dao;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;

import java.sql.*;

public class CustomerDAO {
    //Executing the query for the tr_customer table! which will get the details of the customer!

    // private constructor
    private CustomerDAO() {
    }

    private static final String CUSTOMER_DETAIL_QUERY =
            """
                  SELECT * from tr_customer WHERE id=?;
            """;

    public static CustomerDBModel getCustomerInfo(int customerId) {
        CustomerDBModel customerDBModel = null;
        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CUSTOMER_DETAIL_QUERY);
            // whenever we have requirement to set any value in the query we use preparedStatement
            // setting the customerId value in the query using preparedStatement
            // preparedStatement also helps to prevent SQL injection attacks
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                customerDBModel = new CustomerDBModel(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("mobile_number"),
                        resultSet.getString("mobile_number_alt"),
                        resultSet.getString("email_id"),
                        resultSet.getString("email_id_alt"),
                        resultSet.getInt("tr_customer_address_id")
                );

            }
        }
        catch (SQLException e) {
            System.err.println("SQL Exception occurred while fetching customer info: " + e.getMessage());
        }
        return customerDBModel;
    }
}
