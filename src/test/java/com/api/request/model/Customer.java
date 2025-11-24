package com.api.request.model;

// record class to represent Customer details
//records are immutable data carriers that automatically provide implementations for methods like equals(), hashCode(), and toString()
// records were introduced in Java 14 as a preview feature and became a standard feature in Java 16.
public record Customer (
     String first_name,
     String last_name,
     String mobile_number,
     String mobile_number_alt,
     String email_id,
     String email_id_alt){

}
