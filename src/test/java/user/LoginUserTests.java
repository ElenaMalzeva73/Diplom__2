package user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.Matchers.notNullValue;
import static user.UserCreds.credsFrom;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.apache.http.HttpStatus.*;
public class LoginUserTests extends BaseTestLogIn {
    @Test
    @DisplayName("Авторизация пользователя")
    public void loginUserSuccess() {
        createUser.userLogin(credsFrom(user)).then().assertThat().statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue());
    }
    @Test
    @DisplayName("Авторизация пользователя с некорректным паролем")
    public void loginUserPasswordIncorrect() {
        UserCreds loginUserPasswordIncorrectFail = new UserCreds(user.getEmail(), "7577676558676765876758585");
       createUser.userLogin(loginUserPasswordIncorrectFail).then().assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
    @Test
    @DisplayName("Авторизация пользователя с некорректным email")
    public void loginUserEmailIncorrect() {
        UserCreds loginUserPasswordIncorrectFail = new UserCreds("jhhjghgjghghjghjgjhgjgjhgjhjgjgj", user.getPassword());
        createUser.userLogin(loginUserPasswordIncorrectFail).then().assertThat().statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));

    }
}


