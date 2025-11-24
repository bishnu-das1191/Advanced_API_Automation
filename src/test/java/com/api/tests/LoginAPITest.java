package com.api.tests;

import static io.restassured.RestAssured.*;

import com.api.request.model.UserCredentials;

import com.api.utils.SpecUtil;
import io.restassured.module.jsv.JsonSchemaValidator;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

public class LoginAPITest {
    // Rest Assured test code for login API would go here

    @Test
    public void testLoginAPI() {

        UserCredentials userCredentials = new UserCredentials("iamfd", "password");

        //setup
        given()
                .spec(SpecUtil.requestSpec(userCredentials))
                // action
                .when()
                    .post("login")
                .then()
                    .spec(SpecUtil.responseSpec_OK())
                //validation
                .and()
                    .body("message", equalTo("Success"))
                .and()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
    }

}
