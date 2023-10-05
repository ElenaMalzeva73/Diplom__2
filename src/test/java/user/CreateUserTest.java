package user;

import org.junit.Test;
import io.restassured.response.Response;
import static org.hamcrest.CoreMatchers.equalTo;
import static  org.hamcrest.CoreMatchers.notNullValue;
import io.qameta.allure.junit4.DisplayName;
import static org.apache.http.HttpStatus.*;
public class CreateUserTest extends BaseTest {

   private User user;
    @Test
    @DisplayName("Создание пользователя")
    public void createUserResponse200Created() {

        user = UserGenerator.randomUser();
        Response response = createUser.create(user);
        token = response.then().extract().path("accessToken").toString();
        response.then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());

    }

    @Test
    @DisplayName("Создание пользователя без email")
   public void createUserNoEmail() {
        user = UserGenerator.randomCourierNoEmail();
        Response response = createUser.create(user);
        response.then().assertThat().statusCode(SC_FORBIDDEN).body("success", equalTo(false));
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    public void createUserNoPassword() {
        user = UserGenerator.randomCourierNoPassword();
        Response response = createUser.create(user);
        response.then().assertThat().statusCode(SC_FORBIDDEN).body("success", equalTo(false));
    }
    @Test
    @DisplayName("Создание пользователя без имени")
    public void createUserNoName() {
        user = UserGenerator. randomCourierNoName();
        Response response = createUser.create(user);
        response.then().assertThat().statusCode(SC_FORBIDDEN).body("success", equalTo(false));
    }
    @Test
    @DisplayName("создание пользователя, который уже зарегистрирован")
    public void createNameRegistered(){
        user = UserGenerator.randomUser();
        createUser.create(user);
        Response response = createUser.create(user);
        response.then().assertThat().statusCode(SC_FORBIDDEN).body("success", equalTo(false));
    }

}