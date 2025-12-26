package com.api.tests.datadriven;

import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserBean;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class LoginAPIExcelDataDrivenTest {
    // Rest Assured test code for login API would go here

    @Test(description = "Verify Login API is working for multiple users - Data Driven with Excel File",
            groups = {"api","regression","datadriven"},
            dataProviderClass = com.dataproviders.DataProviderUtils.class,
            dataProvider = "LoginAPIExcelDataProvider")
    public void testLoginAPIwithExcel(UserBean userBean) {


        //setup
        given()
                .spec(requestSpec(userBean))
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
