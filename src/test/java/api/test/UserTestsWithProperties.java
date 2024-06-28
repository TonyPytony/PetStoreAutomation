package api.test;

import api.endpoints.UserEndPointsWithProperties;
import api.payload.User;
import api.utilities.LoggerUtility;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTestsWithProperties {

    Faker faker;
    User userPayload;
    public Logger logger;

    @BeforeClass
    public void setup()
    {
        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5,10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());

        //For logs
        logger = LoggerUtility.getLogger(this.getClass());
    }

    @Test(priority = 1)
    public void testPostUser()
    {
        logger.info("********* CREATING USER *********");
        Response response = UserEndPointsWithProperties.createUser(userPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("********* USER IS CREATED *********");
    }

    @Test(priority = 2)
    public void testGetUserByName()
    {
        logger.info("********* READING USER INFO *********");
        Response response = UserEndPointsWithProperties.readUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("********* USER INFO IS DISPLAYED *********");
    }

    @Test(priority = 3)
    public void testUpdateUserByName()
    {
        //update some data
        logger.info("********* UPDATING USER *********");
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setPhone(faker.phoneNumber().cellPhone());

        Response response = UserEndPointsWithProperties.updateUser(this.userPayload.getUsername(), userPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("********* USER IS UPDATED *********");

        //Check data after update
        logger.info("********* READING USER INFO AFTER UPDATE *********");
        Response responseAfterUpdate = UserEndPointsWithProperties.readUser(this.userPayload.getUsername());
        responseAfterUpdate.then().log().all();
        Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);
        logger.info("********* USER INFO IS DISPLAYED AFTER UPDATE *********");
    }

    @Test(priority = 4)
    public void  testDeleteUserByName()
    {
        logger.info("********* DELETING USER *********");
        Response response = UserEndPointsWithProperties.deleteUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("********* USER IS DELETED *********");
    }

}
