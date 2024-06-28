package org.example;

import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class test {

    String token = "";

    @Test
    public void AuthTest() {

        String baseUrl = "https://code-api-staging.easypayfinance.com/api/Authentication/login";
        String requestBody = "{\n" +
                "  \"username\": \"user\",\n" +
                "  \"password\": \"pass\"\n" +
                "}";

        Response response = given().contentType("application/json").body(requestBody).when().post(baseUrl);
        response.then().statusCode(200);
        response.then().body("token", notNullValue());
        token = response.jsonPath().getString("token");

        System.out.println("Token: " + token);


    }

    @Test
    public void getAllApps(){

        String baseUrl = "https://code-api-staging.easypayfinance.com/api/Authentication/login";
        String requestBody = "{\n" +
                "  \"username\": \"user\",\n" +
                "  \"password\": \"pass\"\n" +
                "}";

        Response response = given().contentType("application/json").body(requestBody).when().post(baseUrl);
        response.then().statusCode(200);
        response.then().body("token", notNullValue());
        token = response.jsonPath().getString("token");

        System.out.println("Token: " + token);

        String appUrl = "https://code-api-staging.easypayfinance.com/api/Application/all";

        Response response2 = given().header("Authorization", "Bearer " + token).log().all().when().get(appUrl);

        response2.then().log().all();
        response2.then().statusCode(200);

        //Parse response. Return ‘Name’ and ‘Amount’ for ‘applicationId’ = ' 2128 '
        String name = response2.jsonPath().getString("find { it.applicationId ==2138 }.name");
        Double amount = response2.jsonPath().getDouble("find { it.applicationId ==2138 }.amount");

        System.out.println("Name: " + name);
        System.out.println("Amount: " + amount);

    }

    @Test
    public void postApp(){
        String baseUrl = "https://code-api-staging.easypayfinance.com/api/Authentication/login";
        String requestBody = "{\n" +
                "  \"username\": \"user\",\n" +
                "  \"password\": \"pass\"\n" +
                "}";

        Response response = given().contentType("application/json").body(requestBody).when().post(baseUrl);
        response.then().statusCode(200);
        response.then().body("token", notNullValue());
        token = response.jsonPath().getString("token");

        System.out.println("Token: " + token);

        String postAppUrl = "https://code-api-staging.easypayfinance.com/api/Application";

        String appBody = "{\n" +
                "  \"applicationId\": 1234,\n" +
                "  \"name\": \"test1\",\n" +
                "  \"age\": \"25\",\n" +
                "  \"amount\": 50\n" +
                "}";

        Response response2 = given().header("Authorization", "Bearer " + token).contentType("application/json").body(appBody).log().all().when().post(postAppUrl);

        response2.then().log().all();
        response2.then().statusCode(200);
        response2.then().body("applicationId", equalTo(1234));
        response2.then().body("name", equalTo("test1"));
        response2.then().body("age", equalTo("25"));
        response2.then().body("amount", equalTo(50));






    }

}
