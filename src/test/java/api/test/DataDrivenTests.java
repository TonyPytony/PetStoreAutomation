package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import api.utilities.LoggerUtility;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DataDrivenTests {

    public Logger logger = LoggerUtility.getLogger(this.getClass());

    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testPostUser(String userID, String userName, String firstName, String lastName, String email, String password, String phone)
    {
        User userPayload = new User();

        userPayload.setId(Integer.parseInt(userID));
        userPayload.setUsername(userName);
        userPayload.setFirstName(firstName);
        userPayload.setLastName(lastName);
        userPayload.setEmail(email);
        userPayload.setPassword(password);
        userPayload.setPhone(phone);

        logger.info("********* CREATING USER *********");
        Response response = UserEndPoints.createUser(userPayload);
        Assert.assertEquals(response.getStatusCode(), 200);
        response.then().log().body();
        logger.info("********* USER IS CREATED *********");
    }

    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testGetUserByName(String userName)
    {
        logger.info("********* READING USER INFO *********");
        Response response = UserEndPoints.readUser(userName);
        response.then().log().body();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("********* USER INFO IS DISPLAYED *********");
    }

    @Test(priority = 3, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testDeleteUserByName(String userName)
    {
        logger.info("********* DELETING USER *********");
        Response response = UserEndPoints.deleteUser(userName);
        Assert.assertEquals(response.getStatusCode(),200);
        response.then().log().body();
        logger.info("********* USER IS DELETED *********");
    }

}
