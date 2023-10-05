package user;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

public class BaseTestLogIn {
    public CreateUser createUser;
    public User user;
    public String token;
    @Before
    public void setUp() {

        createUser = new CreateUser();
        user = UserGenerator.randomUser();
        Response response = createUser.create(user);
        token = response.then().extract().path("accessToken").toString();

    }

    @After
    public void tearDown() {
        CreateUser.userDelete(token);
    }

}
