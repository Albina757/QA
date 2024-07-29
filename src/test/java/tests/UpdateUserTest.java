package tests;

import com.github.javafaker.Faker;
import dto.CreateUserRequest;
import dto.UpdateUserRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tests.BaseTest.postRequest;
import static tests.BaseTest.putRequest;

public class UpdateUserTest {

    Faker faker = new Faker();

    @Test
    public void updateLastName() {
//1. Create new user
//2. Change LastName
//3. Check that lastName is updated
        CreateUserRequest requestBody = new CreateUserRequest("Jack", "Black", "Jack209@yahoo.de", "male", "mr");
        Response response = postRequest("/user/create", 200, requestBody);
        String userId = response.body().jsonPath().getString("id");
        String lastName = response.body().jsonPath().getString("lastName");

       //Change last name
        String newLastName = "White";
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(newLastName, "Jack");
        Response updatedResponse = putRequest("/user/" + userId, 200, updateUserRequest);

        //Check that last name is updated
        String updatedLastName = updatedResponse.body().jsonPath().getString("lastName");
        assertEquals( newLastName, updatedLastName);

    }

    @Test
    public void updateFirstName() {
//1. Create new user
//2. Change firstName
//3. Check that firstName is updated
        String userEmail = faker.internet().emailAddress();
        CreateUserRequest requestBody = new CreateUserRequest("Jack", "Black", userEmail, "male", "mr");
        Response response = postRequest("/user/create", 200, requestBody);
        String userId = response.body().jsonPath().getString("id");
        String firstName = response.body().jsonPath().getString("firstName");

        //Change first name
        String newFirstName = "Andrew";
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("Black",newFirstName);
        Response updatedResponse = putRequest("/user/" + userId, 200, updateUserRequest);

        //Check that last name is updated
        String updatedFirstName = updatedResponse.body().jsonPath().getString("firstName");
        assertEquals( newFirstName, updatedFirstName);

    }

}

