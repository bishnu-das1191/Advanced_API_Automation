package com.api.utils;

import com.api.request.model.*;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FakerDemo2 {

    private final static String COUNTRY = "India";

    public static void main(String[] args) {

        Faker faker = new Faker(new Locale("en-IND")); // for India locale

        //customer info
        String fname = faker.name().firstName();
        String lname = faker.name().lastName();
        String mobile = faker.numerify("70########");
        String alternateMobile = faker.numerify("70########");
        String customEmailAddress = faker.internet().emailAddress();
        String altCustomEmailAddress = faker.internet().emailAddress();

        Customer customer = new Customer(fname, lname, mobile, alternateMobile, customEmailAddress, altCustomEmailAddress);
        System.out.println(customer);


        // customer address
        String flatNo = faker.numerify("###");
        String apartmentName = faker.address().streetName();
        String streetName = faker.address().streetName();
        String landmark = faker.address().streetName();
        String area = faker.address().streetName();
        String pincode = faker.numerify("######");
        String state = faker.address().state();

        CustomerAddress customerAddress = new CustomerAddress(flatNo, apartmentName, streetName, landmark, area, pincode, COUNTRY, state);
        System.out.println(customerAddress);

        //customer product info can be added similarly

        String dop = DateTimeUtil.getTimeWithDaysAgo(10);
        String imeiSerialNumber = faker.numerify("###############");
        String popUrl = faker.internet().url();

        CustomerProduct customerProduct = new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber, popUrl, 1, 1);
        System.out.println(customerProduct);


        // Problems can be added similarly
        String fakeRemark = faker.lorem().sentence(5);
        // Because in app id is from 1 to 27
        // we need to generate random number between 1 to 27 and pass to Problem id
        Random random = new Random();
        int problemId = random.nextInt(26) + 1; // generates a number between 1 and 27
        // +1 to shift range from 0-26 to 1-27

        Problems problems = new Problems(problemId, fakeRemark);
        System.out.println(problems);

        List<Problems> problemsList = new ArrayList<>();
        problemsList.add(problems);


        // CreateJobPayload

        CreateJobPayload createJobPayload = new CreateJobPayload(
                0, 2, 1,
                1,
                customer,
                customerAddress,
                customerProduct,
                problemsList
        );

    }
}
