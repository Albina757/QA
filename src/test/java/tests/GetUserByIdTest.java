package tests;

import dto.InvalidUser;
import dto.UserData;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static tests.BaseTest.getRequest;

public class GetUserByIdTest {
    @Test
    public void validUserDataTest() {
        //    "id": "668b9e03de200ca4507b7227"
        //get user by id, log all from response
        String requestedId = "668b9e03de200ca4507b7227";
        getRequest("user/" + requestedId, 200);
//        UserData user =
//                given().baseUri("https://dummyapi.io/data/v1")
//                        .header("app-id", "65faa96105388e5be212542e")
//                        .when().log().all()
//                        .get("/user/668b9e03de200ca4507b7227")
//                        .then().log().all().statusCode(200).extract().body().jsonPath().getObject("", UserData.class);


        //extract data from response json to instance of UserDAta class


    }

    @Test
    public void allFieldsAreNotEmptyTest() {
        UserData user =
                given().baseUri("https://dummyapi.io/data/v1")
                        .header("app-id", "65faa96105388e5be212542e")
                        .when().log().all()
                        .get("/user/668b9e03de200ca4507b7227")
                        .then().log().all().statusCode(200).extract().body().jsonPath().getObject("", UserData.class);

        assertFalse(user.getId().isEmpty());
        assertFalse(user.getFirstName().isEmpty());
        assertFalse(user.getLastName().isEmpty());
        assertFalse(user.getEmail().isEmpty());
        assertFalse(user.getUpdatedDate().isEmpty());
        assertFalse(user.getRegisterDate().isEmpty());

    }

    //Check that id value from response is matching to id from endpoint
    @Test
    public void idFromResponseAndRequestTest() {
        String requestedId = "668b9e03de200ca4507b7227";
        UserData user =
                given().baseUri("https://dummyapi.io/data/v1")
                        .header("app-id", "65faa96105388e5be212542e")
                        .when().log().all()
                        .get("/user/" + requestedId)
                        .then().log().all().statusCode(200).extract().body().jsonPath().getObject("", UserData.class);

        assertEquals(requestedId, user.getId());

    }

    //Check that registerDate is the same with updatedDate
    @Test
    public void registerDateNUpdateDateEquals() {
        String requestedId = "668b9e03de200ca4507b7227";
        UserData user =
                given().baseUri("https://dummyapi.io/data/v1")
                        .header("app-id", "65faa96105388e5be212542e")
                        .when().log().all()
                        .get("/user/" + requestedId)
                        .then().log().all().statusCode(200).extract().body().jsonPath().getObject("", UserData.class);
        assertEquals("2024-07-08T08:06:27.134Z", user.getRegisterDate());
        assertEquals(user.getUpdatedDate(), user.getRegisterDate());
    }

    @Test
    public void invalidUserTest() {
        //Check that error message with text "PARAMS_NOT_VALID" is displayed
//        InvalidUser invalidUser =
//                given().baseUri("https://dummyapi.io/data/v1")
//                        .header("app-id", "65faa96105388e5be212542e")
//                        .when().log().all()
//                        .get("/user/123" )
//                        .then().log().all().statusCode(400).extract().body().jsonPath().getObject("", InvalidUser.class);
//
//        System.out.println(invalidUser.getError());
//       assertEquals( "PARAMS_NOT_VALID",invalidUser.getError());
//
//    }

        String requestedId = "123";//invalid user Id
        Response response = getRequest("user/" + requestedId, 400);
        InvalidUser error = response.body().jsonPath().getObject("", InvalidUser.class);

        assertEquals("PARAMS_NOT_VALID", error.getError());
    }
}
