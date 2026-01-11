package com.database.dao;

import com.database.DatabaseManager;
import com.database.model.CustomerAddressDBModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerAddressDAO {

    private CustomerAddressDAO() {
        // Private constructor to prevent instantiation
    }

    private static final String CUSTOMER_ADDRESS_QUERY =
            """
                    
                    SELECT id,
                            flat_number,
                            apartment_name,
                            street_name,
                            landmark,
                            area,
                            pincode,
                            country,
                            state
                    from tr_customer_address
                    where id = ?
                    
            """;

    // ? is a placeholder for prepared statement


    public static CustomerAddressDBModel getCustomerAddressBData(int customerAddressId) {

        // Logic to execute the query using a prepared statement
        // and retrieve customer address by id

        CustomerAddressDBModel customerAddressDbModel = null;
        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(CUSTOMER_ADDRESS_QUERY);
            ps.setInt(1, customerAddressId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customerAddressDbModel = new CustomerAddressDBModel(
                        rs.getInt("id"),
                        rs.getString("flat_number"),
                        rs.getString("apartment_name"),
                        rs.getString("street_name"),
                        rs.getString("landmark"),
                        rs.getString("area"),
                        rs.getString("pincode"),
                        rs.getString("country"),
                        rs.getString("state")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerAddressDbModel;
    }
}
