package com.api.tests;

import com.api.constant.Role;

import static com.api.constant.Role.FD;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import com.api.utils.SpecUtil;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CountAPITest {

    @Test
    public void verifyCountAPIResponse() {
        // Test implementation goes here
        given()
                .spec(SpecUtil.requestSpecWithAuth(FD))
                .when()
                .get("/dashboard/count")
                .then()
                .spec(SpecUtil.responseSpec_OK())
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
                .spec(SpecUtil.requestSpec())
                .when()
                .get("/dashboard/count")
                .then()
                .spec(SpecUtil.responseSpec_TEXT(401));
    }
}
