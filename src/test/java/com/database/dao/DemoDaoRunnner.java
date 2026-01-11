package com.database.dao;

import com.api.request.model.Customer;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;

import java.sql.SQLException;

public class DemoDaoRunnner {

    public static void main(String[] args) throws SQLException {



        /*CustomerDBModel customerDBModel =  CustomerDAO.getCustomerInfo(141277);
        System.out.println(customerDBModel);

        System.out.println(customerDBModel.getFirst_name()); // Maeve
        System.out.println(customerDBModel.getLast_name());
        System.out.println(customerDBModel.getMobile_number());

        Customer customer = new Customer("Maeve", "Towne", "767-620-8358", "", "Ashley.Swift69@hotmail.com", "");
        customer.first_name(); // Maeve*/


        // Fetching Customer Address Data

//        CustomerAddressDBModel customerAddressDBModel = CustomerAddressDAO.getCustomerAddressBData(151368);
//        System.out.println(customerAddressDBModel);

        // Fetching Customer Product Data
        CustomerProductDBModel customerProductDBModel = CustomerProductDAO.getProductInfoFromDB(152131);
        System.out.println(customerProductDBModel);
    }
}
