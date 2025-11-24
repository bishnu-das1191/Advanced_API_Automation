package com.api.tests;


import static com.api.constant.Role.FD;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static com.api.utils.SpecUtil.*;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CountAPITest {

    @Test(description = "Verify Count API is working and response schema is valid", groups = {"api","regression","smoke"})
    public void verifyCountAPIResponse() {
        // Test implementation goes here
        given()
                .spec(requestSpecWithAuth(FD))
                .when()
                .get("/dashboard/count")
                .then()
                .spec(responseSpec_OK())
                .body("message", equalTo("Success"))
                .time(lessThan(3000L))
                .body("data", notNullValue())
                .body("data.size()", equalTo(3)) // since data is an Json Array we can use methods like size(), count, etc.
                .body("data.count", everyItem(greaterThanOrEqualTo(0)))
                .body("data.label", everyItem(not(blankOrNullString())))
                .body("data.key", containsInAnyOrder("pending_for_delivery", "created_today", "pending_fst_assignment"))
                .body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema_FD.json"));
    }

    @Test(description = "Verify Count API returns 401 Unauthorized for missing auth token", groups = {"api","regression", "negative","smoke"})
    public void countAPITestWithMissingAuthToken() {
        given()
                .spec(requestSpec())
                .when()
                .get("/dashboard/count")
                .then()
                .spec(responseSpec_TEXT(401));
    }
}
