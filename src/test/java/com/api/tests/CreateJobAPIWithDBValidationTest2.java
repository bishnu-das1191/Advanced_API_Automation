package com.api.tests;

import com.api.constant.*;
import com.api.request.model.*;
import com.api.response.model.CreateJobResponseModel;
import com.api.utils.SpecUtil;
import com.database.dao.CustomerAddressDAO;
import com.database.dao.CustomerDAO;
import com.database.dao.CustomerProductDAO;
import com.database.dao.JobHeadDAO;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.JobHeadModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.api.constant.Role.FD;
import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class CreateJobAPIWithDBValidationTest2 {

    private CreateJobPayload createJobPayload;
    private Customer customer;
    private CustomerAddress customerAddress;
    private CustomerProduct customerProduct;

    @BeforeMethod(description = "Creating createJob api request payload.")
    public void setup(){
        // Any setup code if needed
        // Data Setup
        customer = new Customer("Bishnu", "Das", "1234567890", "", "somit.gate@gmail.com", "");

        // this is how we get email id from record class
        System.out.println(customer.email_id());

        customerAddress = new CustomerAddress(
                "flat no 123","apartment name","street name",
                "near landmark","area name","412101",
                "India","Maharashtra"
        );

        customerProduct = new CustomerProduct(
                getTimeWithDaysAgo(10),
                "5449698687484624",
                "5449698687484624",
                "5449698687484624",
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
    public void createJobAPITestWithDBValidation() {

        // Implementation for creating a job via API

        CreateJobResponseModel createJobResponseModel = given()
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
                //.extract().response();
                        .extract().as(CreateJobResponseModel.class);
        System.out.println(createJobResponseModel);


        System.out.println("--------------------- Create Job API Response -------------------");

        //int customerId = response.then().extract().body().jsonPath().getInt("data.tr_customer_id");
        int customerId = createJobResponseModel.getData().getTr_customer_id();
        System.out.println("Customer ID created via Create Job API: " + customerId);

        CustomerDBModel customerDataFromDB = CustomerDAO.getCustomerInfo(customerId);
        System.out.println("Customer Data fetched from DB: " + customerDataFromDB);

        // Validate API request payload data with DB fetched data
        Assert.assertEquals(customerDataFromDB.getFirst_name(), customer.first_name());
        Assert.assertEquals(customerDataFromDB.getLast_name(), customer.last_name());
        Assert.assertEquals(customerDataFromDB.getMobile_number(), customer.mobile_number());
        Assert.assertEquals(customerDataFromDB.getEmail_id(), customer.email_id());
        Assert.assertEquals(customerDataFromDB.getEmail_id_alt(), customer.email_id_alt());
        Assert.assertEquals(customerDataFromDB.getMobile_number_alt(), customer.mobile_number_alt());

        // Validate customer address id is present
        System.out.println(customerDataFromDB.getTr_customer_address_id());
        CustomerAddressDBModel customerAddressDataFromDB = CustomerAddressDAO
                .getCustomerAddressBData(customerDataFromDB.getTr_customer_address_id());
        System.out.println("Customer Address Data fetched from DB: " + customerAddressDataFromDB);
        Assert.assertEquals(customerAddressDataFromDB.getFlat_number(), customerAddress.flat_number());
        Assert.assertEquals(customerAddressDataFromDB.getApartment_name(), customerAddress.apartment_name());
        Assert.assertEquals(customerAddressDataFromDB.getStreet_name(), customerAddress.street_name());
        Assert.assertEquals(customerAddressDataFromDB.getLandmark(), customerAddress.landmark());
        Assert.assertEquals(customerAddressDataFromDB.getArea(), customerAddress.area());
        Assert.assertEquals(customerAddressDataFromDB.getPincode(), customerAddress.pincode());


        // Further DB validations for customer product
        //int productId = response.then().extract().body().jsonPath().getInt("data.tr_customer_product_id");
        int productId = createJobResponseModel.getData().getTr_customer_product_id();
        CustomerProductDBModel customerProductDBModel = CustomerProductDAO.getProductInfoFromDB(productId);
        System.out.println("Customer Product Data fetched from DB: " + customerProductDBModel);
        Assert.assertEquals(customerProductDBModel.getImei1(), customerProduct.imei1());
        Assert.assertEquals(customerProductDBModel.getImei2(), customerProduct.imei2());
        Assert.assertEquals(customerProductDBModel.getSerial_number(), customerProduct.serial_number());
        Assert.assertEquals(customerProductDBModel.getDop(), customerProduct.dop().substring(0,10));
        Assert.assertEquals(customerProductDBModel.getPopurl(), customerProduct.popurl());
        Assert.assertEquals(customerProductDBModel.getMst_model_id(), customerProduct.mst_model_id());

        // Further DB validations for job head
        JobHeadModel jobHeadDataFromDB = JobHeadDAO.getJobHeadData(customerId);
        Assert.assertEquals(jobHeadDataFromDB.getMst_oem_id(), createJobPayload.mst_oem_id());
        Assert.assertEquals(jobHeadDataFromDB.getMst_platform_id(), createJobPayload.mst_platform_id());
        Assert.assertEquals(jobHeadDataFromDB.getMst_service_location_id(), createJobPayload.mst_service_location_id());
        Assert.assertEquals(jobHeadDataFromDB.getMst_warrenty_status_id(), createJobPayload.mst_warrenty_status_id());


    }

}
