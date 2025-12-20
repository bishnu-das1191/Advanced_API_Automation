package com.api.tests.datadriven;

import com.api.constant.*;
import com.api.request.model.*;
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

public class CreateJobAPIDataDrivenTest {

    // here we are going to take the data from csv file




    @Test(description = "Verify Create Job API Inwarranty Flow is working and response schema is valid",
            groups = {"api","regression","datadriven", "csv"},
            dataProviderClass = com.dataproviders.DataProviderUtils.class,
            dataProvider = "CreateJobAPIDataProvider")
    public void createJobAPITest(CreateJobPayload createJobPayload) {

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
