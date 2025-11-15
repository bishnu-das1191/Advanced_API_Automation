package com.api.tests;

import static io.restassured.RestAssured.*;

import com.api.pojo.UserCredentials;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

public class LoginAPITest {
    // Rest Assured test code for login API would go here

    @Test
    public void testLoginAPI() {

        UserCredentials userCredentials = new UserCredentials("iamfd", "password");

        //setup
        given()
                .baseUri("http://64.227.160.186:9000/v1")
                .and()
                .contentType(ContentType.JSON)
                .and()
                .accept(ContentType.JSON)
                .and()
                .body(userCredentials)
                .log().uri()
                .log().method()
                .log().headers()
                .log().body()
                // action
                .when()
                .post("login")
                .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(2000L))
                //validation
                .and()
                .body("message", equalTo("Success"))
                .and()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));




    }

}
