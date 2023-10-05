package user;
import org.junit.After;
import org.junit.Before;
import io.restassured.RestAssured;
import constant.APIStellarBurgers;

import static user.UserGenerator.randomUser;

public class BaseTest {
    public  CreateUser createUser;
    String  token;
    @Before
    public void setUp() {
        createUser = new CreateUser();

    }
    @After
    public void tearDown() {
        CreateUser.userDelete(token);
    }
}

