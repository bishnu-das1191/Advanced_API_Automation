package com.api.tests;

import com.api.constant.Role;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CountAPITest {

    @Test
    public void verifyCountAPIResponse() {
        // Test implementation goes here
        given()
                .baseUri(getProperty("BASE_URI"))
                .and()
                .header("Authorization", getToken(Role.FD))
                .log().uri()
                .log().method()
                .log().headers()
                .when()
                .get("/dashboard/count")
                .then()
                .log().all()
                .statusCode(200)
                .body("message", equalTo("Success"))
                .time(lessThan(3000L))
                .body("data", notNullValue())
                .body("data.size()", equalTo(3)) // since data is an Json Array we can use methods like size(), count, etc.
                .body("data.count", everyItem(greaterThanOrEqualTo(0)))
                .body("data.label", everyItem(not(blankOrNullString())))
                .body("data.key", containsInAnyOrder("pending_for_delivery", "created_today", "pending_fst_assignment"))
                .body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema_FD.json"));
    }

    @Test
    public void countAPITestWithMissingAuthToken() {
        given()
                .baseUri(getProperty("BASE_URI"))
                .log().uri()
                .log().method()
                .log().headers()
                .when()
                .get("/dashboard/count")
                .then()
                .log().all()
                .statusCode(401);
    }
}
