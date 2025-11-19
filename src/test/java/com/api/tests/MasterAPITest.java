package com.api.tests;

import com.api.constant.Role;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.api.utils.AuthTokenProvider.getToken;
import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MasterAPITest {

    @Test
    public void masterAPITest() {
        // Sample test method
        given()
                .baseUri(getProperty("BASE_URI"))
                .and()
                .header("Authorization", getToken(Role.FD))
                .contentType("") // in post api we need to set content type and restassured by defaults adds it.
                .log().uri()
                .log().method()
                .log().headers()
                .when()
                .post("/master") // in this application developer by mistake created post api instead of get api for /master endpoint
                .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(1000L))
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
                .baseUri(getProperty("BASE_URI"))
                .and()
                .header("Authorization","")
                .and()
                .contentType("") // in post api we need to set content type and restassured by defaults adds it.
                .log().uri()
                .log().method()
                .log().headers()
                .when()
                .post("master") // using GET instead of POST as per the api design
                .then()
                .log().all()
                .statusCode(401); // expecting unauthorized error
    }
}
