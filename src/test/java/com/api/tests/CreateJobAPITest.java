package com.api.tests;

import com.api.constant.Role;
import com.api.pojo.*;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static com.api.constant.Role.FD;
import static com.api.utils.AuthTokenProvider.getToken;
import static io.restassured.RestAssured.given;

public class CreateJobAPITest {


    Customer customer = new Customer("Bishnu", "Das", "1234567890", "", "somit.gate@gmail.com", "");
    CustomerAddress customerAddress = new CustomerAddress(
            "flat no 123","apartment name","street name",
            "near landmark","area name","412101",
            "India","Maharashtra"
    );

    CustomerProduct customerProduct = new CustomerProduct(
            "2025-04-03T18:30:00.000Z",
            "4004598687484620",
            "4004598687484620",
            "4004598687484620",
            "2025-04-03T18:30:00.000Z",
            1,
            1
    );

    Problems problem1 = new Problems(1, "battery issue");
    Problems[] problems = {problem1};

    CreateJobPayload createJobPayload = new CreateJobPayload(
            0,2,1,1,
            customer,customerAddress,customerProduct,problems);





    @Test
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
                .spec(SpecUtil.responseSpec_OK());


    }
}
