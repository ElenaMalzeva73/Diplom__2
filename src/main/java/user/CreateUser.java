package user;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static constant.APIStellarBurgers.*;
import static io.restassured.RestAssured.given;

public class CreateUser {
    @Step("Создание пользователя")
    public static Response create(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post( BASE_URL + POST_USER_CREATE);
    }

    @Step("Логин пользователя  в системе")
    public Response userLogin(UserCreds creds) {
        return given()
                .header("Content-type", "application/json")
                .body(creds)
                .post(BASE_URL + POST_USER_LOGIN);
    }
    @Step("Изменение данных авторизированного пользователя")
    public static Response userChanges(String token, User user) {
        return given()
                .header("Authorization", token)
                .body(user)
                .patch( BASE_URL + DELETE_USER);
    }

    @Step("Изменение данных не авторизированного пользователя")
    public  static Response userChangesNoLogin(User user) {
        return given()
                .body(user)
                .patch(BASE_URL + DELETE_USER );

    }

    @Step("Удаление курьера")
    public static void userDelete(String token) {
        if (token != null) {
            given()
                    .header("Authorization", token)
                    .delete(BASE_URL + DELETE_USER);

        }

    }
}

