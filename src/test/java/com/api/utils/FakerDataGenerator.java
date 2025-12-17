package com.api.utils;

import com.api.request.model.*;
import com.github.javafaker.Faker;

import java.util.*;

public class FakerDataGenerator {

    // Because all these utils are following Utility Design Pattern,

    private static Faker faker = new Faker(new Locale("en-IND"));
    private final static Random RANDOM = new Random();
    private final static String COUNTRY = "India";
    private final static int MST_SERVCE_LOCATION_ID = 0;
    private final static int MST_PLATFORM_ID = 2;
    private final static int MST_WARRANTY_STATUS_ID = 1;
    private final static int MST_OEM_ID = 1;
    private final static int PRODUCT_ID = 1;
    private final static int MST_MODEL_ID = 1;

    private final static int validProblemIds[] = {
            1,2,3,4,5,6,7,8,9,10,
            11,12,15,16,17,
            19,20,22,24,26,27,28,29
    };




    private FakerDataGenerator() {
        // private constructor to prevent instantiation
    }


    public static CreateJobPayload generateFakeCreateJobData() {
        // Implementation for generating fake job data

        Customer customer = generateFakeCustomerData();
        CustomerAddress customerAddress = generateFakeCustomerAddressData();
        CustomerProduct customerProduct = generateFakeCustomerProduct();
        List<Problems> problemsList = generateFakeProblemsList(); // generating 3 fake problems
        CreateJobPayload createJobPayload = new CreateJobPayload(
                MST_SERVCE_LOCATION_ID, MST_PLATFORM_ID, MST_WARRANTY_STATUS_ID,
                MST_OEM_ID, customer, customerAddress, customerProduct,
                problemsList
        );

        return createJobPayload;
    }


    public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {

        List<CreateJobPayload> createJobPayloadList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Customer customer = generateFakeCustomerData();
            CustomerAddress customerAddress = generateFakeCustomerAddressData();
            CustomerProduct customerProduct = generateFakeCustomerProduct();
            List<Problems> problemsList = generateFakeProblemsList(); // generating 3 fake problems
            CreateJobPayload createJobPayload = new CreateJobPayload(
                    MST_SERVCE_LOCATION_ID, MST_PLATFORM_ID, MST_WARRANTY_STATUS_ID,
                    MST_OEM_ID, customer, customerAddress, customerProduct,
                    problemsList
            );
            createJobPayloadList.add(createJobPayload);
        }
        return createJobPayloadList.iterator();
    }

    private static List<Problems> generateFakeProblemsList() {

        // Problems can be added similarly
        String fakeRemark = faker.lorem().sentence(5);

        // Because in app id is from 1 to 27
        // we need to generate random number between 1 to 27 and pass to Problem id
        //int problemId = RANDOM.nextInt(26) + 1;
        // +1 to shift range from 0-26 to 1-27
        // For example, if random.nextInt(26) gives 0, adding 1 makes it 1. Because app problem ids start from 1.
        // If it gives 26, adding 1 makes it 27.

        int randomProblemIdIndex = RANDOM.nextInt(validProblemIds.length); // get the number from problem ids array length
        // because in app, there are specific problem ids are valid, so we are picking random index from that array

        Problems problems = new Problems(validProblemIds[randomProblemIdIndex], fakeRemark);
        System.out.println(problems);

        List<Problems> problemsList = new ArrayList<>();
        problemsList.add(problems);
        return problemsList;
    }

    private static CustomerProduct generateFakeCustomerProduct() {
        //customer product info can be added similarly

        String dop = DateTimeUtil.getTimeWithDaysAgo(10);
        String imeiSerialNumber = faker.numerify("###############");
        String popUrl = faker.internet().url();

        CustomerProduct customerProduct = new CustomerProduct(
                dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber, popUrl,
                PRODUCT_ID, MST_MODEL_ID
        );
        return customerProduct;
    }

    private static CustomerAddress generateFakeCustomerAddressData() {
        // customer address
        String flatNo = faker.numerify("###");
        String apartmentName = faker.address().streetName();
        String streetName = faker.address().streetName();
        String landmark = faker.address().streetName();
        String area = faker.address().streetName();
        String pincode = faker.numerify("######");
        String state = faker.address().state();

        CustomerAddress customerAddress = new CustomerAddress(flatNo, apartmentName, streetName, landmark, area, pincode, COUNTRY, state);
        return customerAddress;
    }

    private static Customer generateFakeCustomerData() {
        //customer info
        String fname = faker.name().firstName();
        String lname = faker.name().lastName();
        String mobile = faker.numerify("70########");
        String alternateMobile = faker.numerify("70########");
        String customEmailAddress = faker.internet().emailAddress();
        String altCustomEmailAddress = faker.internet().emailAddress();

        Customer customer = new Customer(fname, lname, mobile, alternateMobile, customEmailAddress, altCustomEmailAddress);
        return customer;
    }
}
