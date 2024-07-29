package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.http.*;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.*;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseTest {
    final static String BASE_URI = "https://dummyapi.io/data/v1";
    final static String APP_ID_VALUE = "65faa96105388e5be212542e";
     static RequestSpecification specification = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .addHeader("app-id", APP_ID_VALUE)
            .setContentType(ContentType.JSON)
            .build();

    public static Response getRequest(String endpoint, Integer expectedStatusCode){
    Response response = given()
            .spec(specification)
            .when()
            .log().all()
            .get(endpoint)
            .then()
            .log().all()
            .statusCode(expectedStatusCode)
            .extract().response();
    return response;
    }

    public static Response postRequest(String endpoint, Integer expectedStatusCode, Object body){
        Response response = given()
                .spec(specification)
                .body(body)
                .when()
                .log().all()
                .post(endpoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();

    return response;
    }

    public static Response putRequest(String endpoint, Integer expectedStatusCode, Object body){
        Response response = given()
                .spec(specification)
                .body(body)
                .when()
                .log().all()
                .put(endpoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

}


