package com.database.dao;

import com.api.request.model.Customer;
import com.database.model.CustomerDBModel;

import java.sql.SQLException;

public class DemoDaoRunnner {

    public static void main(String[] args) throws SQLException {
        CustomerDBModel customerDBModel =  CustomerDAO.getCustomerInfo();
        System.out.println(customerDBModel);

        System.out.println(customerDBModel.getFirst_name()); // Maeve
        System.out.println(customerDBModel.getLast_name());
        System.out.println(customerDBModel.getMobile_number());

        Customer customer = new Customer("Maeve", "Towne", "767-620-8358", "", "Ashley.Swift69@hotmail.com", "");
        customer.first_name(); // Maeve
    }
}
