package user;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.apache.http.HttpStatus.*;
import static  org.hamcrest.CoreMatchers.notNullValue;
import static user.UserCreds.credsFrom;
import io.qameta.allure.junit4.DisplayName;

public class EditUserTest extends BaseTestLogIn {

    @Test
    @DisplayName("Редактирование данных у авторизованного пользователя")
    public void userChangesRegister() {
        createUser.userLogin(credsFrom(user)).then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
        User newUser = UserGenerator.randomUser();
        CreateUser.userChanges(token, newUser)
                .then()
                .assertThat().statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user", notNullValue());
    }
    @Test
    @DisplayName("Редактирование данных у  не авторизованного пользователя")
    public void userChangesAreNotRegistered() {
        User newUser = UserGenerator.randomUser();
        CreateUser.userChangesNoLogin(newUser)
                .then()
                .assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));

    }
}


