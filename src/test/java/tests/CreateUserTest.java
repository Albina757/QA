package tests;

import com.github.javafaker.Faker;
import dto.CreateUserRequest;
import dto.UserDataFull;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tests.BaseTest.getRequest;
import static tests.BaseTest.postRequest;

public class CreateUserTest {
    Faker faker = new Faker();

    @Test
    public void successCreateUserRequiredFields() {
        String userEmail = faker.internet().emailAddress();

        CreateUserRequest requestBody = new CreateUserRequest("Jack", "Black", userEmail, "male", "mr");
        Response response = postRequest("/user/create", 200, requestBody);
        //Check that first name form response equals to name from request
        String firstNameFromResp = response.body().jsonPath().getString("firstName");
        assertEquals("Jack", firstNameFromResp);


    }

    @Test
    public void successCreateUserFaker() {
        String userEmail = faker.internet().emailAddress();
        String userFirstName = faker.name().firstName();
        String userLastName = faker.name().lastName();
        CreateUserRequest requestBody = new CreateUserRequest(userFirstName, userLastName, userEmail, "male", "mr");
        Response response = postRequest("/user/create", 200, requestBody);
        String firstNameFromResp = response.body().jsonPath().getString("firstName");
        String lastNameFromResp = response.body().jsonPath().getString("lastName");
        String emailFromResp = response.body().jsonPath().getString("email");
        assertEquals(userFirstName, firstNameFromResp);
        assertEquals(userLastName, lastNameFromResp);
        assertEquals(userEmail, emailFromResp);
    }

    @Test
    public void successCreateUserAdditionalFields() {

        //gender
        //title
        String userEmail = faker.internet().emailAddress();
        String userFirstName = faker.name().firstName();
        String userLastName = faker.name().lastName();
        CreateUserRequest requestBody = new CreateUserRequest(userFirstName, userLastName, userEmail, "female", "mrs");
        Response response = postRequest("/user/create", 200, requestBody);
        String genderFromResp = response.body().jsonPath().getString("gender");
        String titleFromResp = response.body().jsonPath().getString("title");
        assertEquals("female", genderFromResp);
        assertEquals("mrs", titleFromResp);
        // Parse json data to UserDataFull class
        UserDataFull userData = response.body().jsonPath().getObject("", UserDataFull.class);
        //gender
        //tittle
        //phone
        //fn
        //ln
        //email
        String firstNameFromResp = response.body().jsonPath().getString("firstName");
        String lastNameFromResp = response.body().jsonPath().getString("lastName");
        String emailFromResp = response.body().jsonPath().getString("email");
        assertEquals(userFirstName, firstNameFromResp);
        assertEquals(userLastName, lastNameFromResp);
        assertEquals(userEmail, emailFromResp);
        assertEquals("female", genderFromResp);
    }

    @Test
    public void withoutEmail() {
        //we send first name, last name and no email,
        //make sure status code is 400 and "Path email is required" message
        String userFirstName = faker.name().firstName();
        String userLastName = faker.name().lastName();
        //CreateUserRequest requestBody = new CreateUserRequest("Jack", "Black", "", "male", "mr");
        CreateUserRequest requestBody = CreateUserRequest.builder()
                .firstName(userFirstName)
                .lastName(userLastName)
                .gender("female")
                .title("mrs")
                .build();
        //builder tolko dlia sozdania opredelennih polei v konstruktore

        Response response = postRequest("/user/create", 400, requestBody);
        assertTrue(response.body().jsonPath().getString("data.email").contains("Path `email` is required."));
    }


    @Test
    public void withoutFirstName() {
        String userLastName = faker.name().lastName();
        String userEmail = faker.internet().emailAddress();
        CreateUserRequest requestBody = CreateUserRequest.builder()
                .lastName(userLastName)
                .email(userEmail)
                .gender("female")
                .title("mrs")
                .build();


        Response response = postRequest("/user/create", 400, requestBody);
        String errorMessage = response.body().jsonPath().getString("data.firstName");
        assertEquals("Path `firstName` is required.", errorMessage);
    }


    @Test
    public void withoutLastName() {
        String userFirstName = faker.name().firstName();
        String userEmail = faker.internet().emailAddress();
        CreateUserRequest requestBody = CreateUserRequest.builder()
                .firstName(userFirstName)
                .email(userEmail)
                .gender("female")
                .title("mrs")
                .build();


        Response response = postRequest("/user/create", 400, requestBody);
        String errorMessage = response.body().jsonPath().getString("data.lastName");
        assertEquals("Path `lastName` is required.", errorMessage);
    }

    @Test
    public void withoutAllRequiredFields() {
        CreateUserRequest requestBody = CreateUserRequest.builder()
                .gender("female")
                .title("mrs")
                .build();


        Response response = postRequest("/user/create", 400, requestBody);
        String errorMessageLastName = response.body().jsonPath().getString("data.lastName");
        String errorMessageFirstName = response.body().jsonPath().getString("data.firstName");
        String errorMessageEmail = response.body().jsonPath().getString("data.email");


        assertEquals("Path `lastName` is required.", errorMessageLastName);
        assertEquals("Path `firstName` is required.", errorMessageFirstName);
        assertEquals("Path `email` is required.", errorMessageEmail);

        String errorMessage = response.body().jsonPath().getString("error");
        assertEquals("BODY_NOT_VALID", errorMessage);
    }


}
