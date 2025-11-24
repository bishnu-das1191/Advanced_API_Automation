package com.api.utils;

import static com.api.constant.Role.*;


import com.api.constant.Role;
import com.api.request.model.UserCredentials;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;

public class AuthTokenProvider {

    private AuthTokenProvider() {
        // private constructor to prevent instantiation
    }

    public static String getToken(Role role) {
        // Make the request for the login api and we want to extract
        // the token from the response

        UserCredentials userCredentials = null;
        if(role == FD){
            userCredentials = new UserCredentials("iamfd", "password");
        } else if(role == SUP){
            userCredentials = new UserCredentials("iamsup", "password");
        } else if(role == ENG){
            userCredentials = new UserCredentials("iameng", "password");
        } else if (role == QC) {
            userCredentials = new UserCredentials("iamqc", "password");
        }

        return given()
                .baseUri(ConfigManager.getProperty("BASE_URI"))
                .contentType(ContentType.JSON)
                .body(userCredentials)
                .when()
                .post("login")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("message", equalTo("Success"))
                .extract()
                .body().jsonPath().getString("data.token");

    }
}
