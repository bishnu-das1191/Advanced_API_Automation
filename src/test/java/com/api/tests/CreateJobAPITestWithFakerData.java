package com.api.tests;

import com.api.request.model.*;
import com.api.utils.FakerDataGenerator;
import com.api.utils.SpecUtil;
import com.database.dao.CustomerAddressDAO;
import com.database.dao.CustomerDAO;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.checkerframework.checker.units.qual.A;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class CreateJobAPITestWithFakerData {

    private CreateJobPayload createJobPayload;
    private final static String COUNTRY = "India";


    @BeforeMethod(description = "Creating createJob api request payload.")
    public void setup(){
        // Any setup code if needed
        // Data Setup using FakerDataGenerator utility class
       createJobPayload = FakerDataGenerator.generateFakeCreateJobData();
    }


    @Test(description = "Verify Create Job API Inwarranty Flow is working and response schema is valid",
            groups = {"api","regression","smoke"})
    public void createJobAPITest() {

        // Implementation for creating a job via API

        int customerId = given()
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
                .body("data.job_number", startsWith("JOB_"))
                .extract().body().jsonPath().getInt("data.tr_customer_id");

                // DB Validations are added here using the extracted customerId if needed
        // Validate customer data in DB
        Customer expectedCustomerData = createJobPayload.customer();
        CustomerDBModel actualCustomerDataInDB = CustomerDAO.getCustomerInfo(customerId);

        Assert.assertEquals(actualCustomerDataInDB.getFirst_name(), expectedCustomerData.first_name());
        Assert.assertEquals(actualCustomerDataInDB.getLast_name(), expectedCustomerData.last_name());
        Assert.assertEquals(actualCustomerDataInDB.getMobile_number(), expectedCustomerData.mobile_number());
        Assert.assertEquals(actualCustomerDataInDB.getEmail_id(), expectedCustomerData.email_id());
        Assert.assertEquals(actualCustomerDataInDB.getEmail_id_alt(), expectedCustomerData.email_id_alt());
        Assert.assertEquals(actualCustomerDataInDB.getMobile_number_alt(), expectedCustomerData.mobile_number_alt());

        // Customer address DB validations
        CustomerAddressDBModel actualCustomerAddressInDB = CustomerAddressDAO
                .getCustomerAddressBData(actualCustomerDataInDB.getTr_customer_address_id());

        Assert.assertEquals(actualCustomerAddressInDB.getFlat_number(), createJobPayload.customer_address().flat_number());
        Assert.assertEquals(actualCustomerAddressInDB.getApartment_name(), createJobPayload.customer_address().apartment_name());
        Assert.assertEquals(actualCustomerAddressInDB.getStreet_name(), createJobPayload.customer_address().street_name());
        Assert.assertEquals(actualCustomerAddressInDB.getLandmark(), createJobPayload.customer_address().landmark());
        Assert.assertEquals(actualCustomerAddressInDB.getArea(), createJobPayload.customer_address().area());
        Assert.assertEquals(actualCustomerAddressInDB.getPincode(), createJobPayload.customer_address().pincode());
        Assert.assertEquals(actualCustomerAddressInDB.getCountry(), createJobPayload.customer_address().country());



    }
}
