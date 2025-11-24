package com.api.tests;

import com.api.utils.SpecUtil;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MasterAPITest {

    @Test
    public void masterAPITest() {
        // Sample test method
        given()
                .spec(SpecUtil.requestSpecWithAuth(FD))
                .when()
                .post("/master") // in this application developer by mistake created post api instead of get api for /master endpoint
                .then()
                .spec(SpecUtil.responseSpec_OK())
                .body("message", equalTo("Success"))
                .body("data", notNullValue())
                .body("data",hasKey("mst_oem")) // verifying mst_oem key inside data
                .body("data", hasKey("mst_model"))
                .body("$",hasKey("message")) // verifying root level key
                .body("$",hasKey("data"))
                .body("data.mst_oem.size()", greaterThan(0)) // verifying mst_oem is not empty
                .body("data.mst_model.size()", greaterThan(0)) // verifying mst_model is not empty
                .body("data.mst_oem.id", everyItem(notNullValue())) // verifying id is not null inside each object of mst_oem array
                .body("data.mst_oem.name", everyItem(not(blankOrNullString()))) // verifying name is not blank or null inside each object of mst_oem array
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));

    }


    @Test
    public void masterAPITestWithInvalidToken() {
        given()
                .spec(SpecUtil.requestSpec())
                .when()
                .post("master") // using GET instead of POST as per the api design
                .then()
                .spec(SpecUtil.responseSpec_TEXT(401)); // expecting unauthorized error
    }
}
