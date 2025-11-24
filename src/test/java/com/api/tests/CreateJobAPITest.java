package com.api.tests;

import com.api.constant.*;
import com.api.request.model.*;
import com.api.request.model.CustomerAddress;
import com.api.utils.DateTimeUtil;
import com.api.utils.SpecUtil;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.api.constant.Role.FD;
import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class CreateJobAPITest {

    private CreateJobPayload createJobPayload;

    @BeforeMethod(description = "Creating createJob api request payload.")
    public void setup(){
        // Any setup code if needed
        // Data Setup
        Customer customer = new Customer("Bishnu", "Das", "1234567890", "", "somit.gate@gmail.com", "");

        // this is how we get email id from record class
        System.out.println(customer.email_id());

        CustomerAddress customerAddress = new CustomerAddress(
                "flat no 123","apartment name","street name",
                "near landmark","area name","412101",
                "India","Maharashtra"
        );

        CustomerProduct customerProduct = new CustomerProduct(
                getTimeWithDaysAgo(10),
                "5344598687484620",
                "5344598687484620",
                "5344598687484620",
                getTimeWithDaysAgo(10),
                Product.NEXUS_2.getCode(),
                Model.NEXUS_2_BLUE.getCode()
        );

        Problems problem1 = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "battery issue");
        List<Problems> problemsList = new ArrayList<>();
        problemsList.add(problem1);

        createJobPayload = new CreateJobPayload(
                ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(),
                customer,customerAddress,customerProduct, problemsList);
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
