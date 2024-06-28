package api.endpoints;

import api.payload.User;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserEndPoints {

    public static Response createUser(User payload)
    {
        Response response = given()
                .contentType("application/json")
                .accept("application/json")
                .body(payload)
                .when()
                .post(Routes.postUrl);
        return response;
    }

    public static Response readUser(String userName)
    {
        Response response = given()
                .pathParam("username", userName)
                .when()
                .get(Routes.getUrl);
        return response;
    }

    public static Response updateUser(String userName, User payload)
    {
        Response response = given()
                .contentType("application/json")
                .accept("application/json")
                .body(payload)
                .pathParam("username", userName)
                .when()
                .put(Routes.updateUrl);
        return response;
    }

    public static Response deleteUser(String userName)
    {
        Response response = given()
                .pathParam("username", userName)
                .when()
                .delete(Routes.deleteUrl);
        return response;
    }

}
