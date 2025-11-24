package com.api.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.*;
import org.testng.annotations.Test;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MasterAPITest {

    @Test(description = "Verify Master API is working and response schema is valid", groups = {"api","regression","smoke"})
    public void masterAPITest() {
        // Sample test method
        given()
                .spec(requestSpecWithAuth(FD))
                .when()
                .post("/master") // in this application developer by mistake created post api instead of get api for /master endpoint
                .then()
                .spec(responseSpec_OK())
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
                .body(matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));

    }


    @Test(description = "Verify Master API returns 401 Unauthorized for invalid token", groups = {"api","regression", "negative","smoke"})
    public void masterAPITestWithInvalidToken() {
        given()
                .spec(requestSpec())
                .when()
                .post("master") // using GET instead of POST as per the api design
                .then()
                .spec(responseSpec_TEXT(401)); // expecting unauthorized error
    }
}
