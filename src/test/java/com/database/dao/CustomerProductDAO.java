package com.database.dao;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerProductDAO {

    private static final String PRODUCT_QUERY =
            """
            
            SELECT * from tr_customer_product where id = ?;
                    
            """;


    private CustomerProductDAO() {
    // Private constructor to prevent instantiation
    }

    public static CustomerProductDBModel getProductInfoFromDB(int tr_customer_product_id) {
        // Implementation for fetching product info from DB
        CustomerProductDBModel customerProductDBModel = null;
        try{
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(PRODUCT_QUERY);
            ps.setInt(1, tr_customer_product_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                customerProductDBModel = new CustomerProductDBModel(
                        rs.getInt("id"),
                        rs.getInt("tr_customer_id"),
                        rs.getInt("mst_model_id"),
                        rs.getString("dop"),
                        rs.getString("popurl"),
                        rs.getString("imei2"),
                        rs.getString("imei1"),
                        rs.getString("serial_number")
                );
                System.out.println("Customer Product Info: " + customerProductDBModel);
            }
        }catch (SQLException e){
            System.err.println("SQL Exception in getProductInfoFromDB: " + e.getMessage());
        }
        return customerProductDBModel;
    }
}
