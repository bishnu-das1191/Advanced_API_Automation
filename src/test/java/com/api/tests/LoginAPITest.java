package com.api.tests;

import static io.restassured.RestAssured.*;

import com.api.pojo.UserCredentials;
import static com.api.utils.ConfigManager.*;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginAPITest {
    // Rest Assured test code for login API would go here

    @Test
    public void testLoginAPI() {

        UserCredentials userCredentials = new UserCredentials("iamfd", "password");

        //setup
        given()
                .baseUri(getProperty("BASE_URI")) // ConfigManagerOLD.getProperty("BASE_URI")
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
