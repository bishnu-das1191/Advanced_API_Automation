package com.api.tests;

import static io.restassured.RestAssured.*;
import com.api.request.model.UserCredentials;
import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginAPITest {
    // Rest Assured test code for login API would go here
    private UserCredentials userCredentials;

    @BeforeMethod(description = "Setup User Credentials Payload for Login API Request")
    public void setup(){
        userCredentials = new UserCredentials("iamfd", "password");

    }

    @Test(description = "Verify Login API is working for user iamfd", groups = {"api","regression","smoke"})
    public void testLoginAPI() {


        //setup
        given()
                .spec(requestSpec(userCredentials))
                // action
                .when()
                    .post("login")
                .then()
                    .spec(responseSpec_OK())
                //validation
                .and()
                    .body("message", equalTo("Success"))
                .and()
                    .body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
    }

}
