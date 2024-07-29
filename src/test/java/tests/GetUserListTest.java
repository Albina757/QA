package tests;

import dto.UserDataFull;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static tests.BaseTest.getRequest;

public class GetUserListTest {
    @Test
    public void getUserList() {
        //Request /user
        Response response = getRequest("/user", 200);
//parse List of users
        List<UserDataFull> users = getRequest("/user", 200)
                .body().jsonPath().getList("data", UserDataFull.class);
        System.out.println(users.get(0).getId());

        //check that 20 users are in list
        assertEquals(20, users.size());


        //Check that users quantity from list match with limit
        Integer limitValue = getRequest("user/", 200)
                .body().jsonPath().getInt("limit");
        assertEquals(limitValue, users.size());


        //Response response = getRequest("user/" , 200);
        //Integer limitValue  =  response
        //.body().jsonPath().getInt("limit");
        Integer pageValue = response
                .body().jsonPath().getInt("page");
        System.out.println(limitValue + " , " + pageValue);

        assertEquals(limitValue, users.size());

        //Check that id of each user is not empty
        for (UserDataFull user : users) {
            assertFalse(user.getId().isEmpty());
            //Check that all pictures in jpg format
            assertTrue(user.getPicture().endsWith(".jpg"));

        }


    }

    @Test
    public void getUserListWithSpecLimit() {
        //limit =5
        //check that 5 jsons in users list
        int limit = 5;
        Response response = getRequest("/user?limit=" + limit, 200);
        List<UserDataFull> users = response
                .body().jsonPath().getList("data", UserDataFull.class);


        // check that 5 jsons in users list
        assertEquals(limit, users.size());

    }

    @Test
    public void getUserListLimitInvalidLess() {
        //limit < 5
        //check that limit =5
        int limit = 4;
        Response response = getRequest("/user?limit=" + limit, 200);
        List<UserDataFull> users = response
                .body().jsonPath().getList("data", UserDataFull.class);


        // check that 5 jsons in users list
        assertEquals(5, users.size());

    }

    @Test
    public void getUserListLimitInvalidMore() {
        //limit > 50
        //check that limit =50

        int limit = 100;
        Response response = getRequest("/user?limit=" + limit, 200);
        List<UserDataFull> users = response
                .body().jsonPath().getList("data", UserDataFull.class);

        System.out.println(users.size());//check the limit
        // check that 5 jsons in users list
        assertEquals(50, users.size());
    }

    @Test
    //5->5, 23->23, 2->5, 78->50, -1->5
//ParametrizedTest
    @ParameterizedTest
    @MethodSource("validData")
    public void getUserListLimitInvalidSize(int limit, int expectedLimit) {
        //5->5, 23->23, 2->5, 78->50, -1->5
        //ParametrizedTest
        //assertEquals(expectedLimit, limit);
        Response response = getRequest("/user?limit=" + limit, 200);
        List<UserDataFull> users = response
                .body().jsonPath().getList("data", UserDataFull.class);
        assertEquals(expectedLimit, users.size());
    }

    static Stream<Arguments> validData() {
        return Stream.of(
                arguments(5, 5),
                arguments(23, 23),
                arguments(2, 5),
                arguments(78, 50),
                arguments(-1, 5)
        );
    }


}

