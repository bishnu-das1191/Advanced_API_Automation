package com.api.utils;

import com.github.javafaker.Faker;

import java.util.Locale;

public class FakerDemo {

    public static void main(String[] args) {

        Faker faker = new Faker(new Locale("en-IND")); // for India locale
        String name = faker.name().firstName();
        System.out.println(name);

        // unique alphanumeric id
        System.out.println(faker.numerify("USER_####"));
        System.out.println(faker.numerify("USER_####"));
        System.out.println(faker.numerify("USER_####"));
        System.out.println(faker.numerify("USER_####"));

        // email
        System.out.println(faker.internet().emailAddress());
    }
}
