package com.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CustomerDBModel {

    // also you need to install lombok plugin in your IDE to use lombok annotations
    // search for lombok project in google and go to that link and download page and then install jar file.
    private int id;
    private String first_name;
    private String last_name;
    private String mobile_number;
    private String mobile_number_alt;
    private String email_id;
    private String email_id_alt;
    private int tr_customer_address_id;

}
