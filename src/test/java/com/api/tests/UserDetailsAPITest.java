package com.api.tests;

import static io.restassured.RestAssured.*;

import static com.api.utils.ConfigManager.*;

import static com.api.utils.AuthTokenProvider.*;

import static com.api.constant.Role.*;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.lessThan;

public class UserDetailsAPITest {

    @Test
    public void userDetailsAPITest() throws IOException {
        // Placeholder for user details API test implementation

        Header authHeader = new Header("Authorization", getToken(FD));

        given()
                .baseUri(getProperty("BASE_URI"))
                .and()
                .header(authHeader)
                .and()
                .accept(ContentType.JSON)
                .log().uri()
                .log().method()
                .log().body()
                .log().headers()
                .when()
                .get("userdetails")
                .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(5000L))
                .and()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));

    }
}
