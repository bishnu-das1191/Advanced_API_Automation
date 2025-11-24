package com.api.tests;

import static io.restassured.RestAssured.*;
import static com.api.constant.Role.*;

import com.api.utils.SpecUtil;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import java.io.IOException;
import static org.hamcrest.Matchers.lessThan;

public class UserDetailsAPITest {

    @Test
    public void userDetailsAPITest() throws IOException {
        // Placeholder for user details API test implementation

        given()
                .spec(SpecUtil.requestSpecWithAuth(FD))
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
