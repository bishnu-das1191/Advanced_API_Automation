package com.api.tests;

import com.api.constant.*;
import com.api.request.model.*;
import com.api.utils.DateTimeUtil;
import com.api.utils.SpecUtil;
import com.github.javafaker.Faker;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.api.constant.Role.FD;
import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class CreateJobAPITest2 {

    private CreateJobPayload createJobPayload;
    private final static String COUNTRY = "India";


    @BeforeMethod(description = "Creating createJob api request payload.")
    public void setup(){

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

        Problems problems = new Problems(problemId, fakeRemark);
        System.out.println(problems);

        List<Problems> problemsList = new ArrayList<>();
        problemsList.add(problems);


        // CreateJobPayload

        createJobPayload = new CreateJobPayload(
                0, 2, 1,
                1,
                customer,
                customerAddress,
                customerProduct,
                problemsList
        );

    }


    @Test(description = "Verify Create Job API Inwarranty Flow is working and response schema is valid", groups = {"api","regression","smoke"})
    public void createJobAPITest() {

        // Implementation for creating a job via API

        given()
                .spec(SpecUtil.requestSpecWithAuth(FD, createJobPayload))
                 // we are using Text Block feature of Java 15
                // to create multi line JSON String here below
                /*.body("""
                        
                        {
                            "mst_service_location_id": 0,
                            "mst_platform_id": 2,
                            "mst_warrenty_status_id": 1,
                            "mst_oem_id": 1,
                            "customer": {
                                "first_name": "Brayan",
                                "last_name": "Larson",
                                "mobile_number": "583-498-1443",
                                "mobile_number_alt": "",
                                "email_id": "Linnea31@gmail.com",
                                "email_id_alt": ""
                            },
                            "customer_address": {
                                "flat_number": "fake Flat",
                                "apartment_name": "fake apartment",
                                "street_name": "fake street",
                                "landmark": "Fake Landmark",
                                "area": "Fake Area",
                                "pincode": "412101",
                                "country": "India",
                                "state": "Maharashtra"
                            },
                            "customer_product": {
                                "dop": "2025-04-03T18:30:00.000Z",
                                "serial_number": "3004598687484620",
                                "imei1": "3004598687484620",
                                "imei2": "3004598687484620",
                                "popurl": "2025-04-03T18:30:00.000Z",
                                "product_id": 1,
                                "mst_model_id": 1
                            },
                            "problems": [
                                {
                                    "id": 1,
                                    "remark": "battery issue"
                                }
                            ]
                        }
                        
                        """)*/
                .when()
                .post("/job/create")
                .then()
                .spec(SpecUtil.responseSpec_OK())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
                .body("message", equalTo("Job created successfully. "))
                .body("data.mst_service_location_id", equalTo(1))
                .body("data.job_number", startsWith("JOB_"));


    }
}
