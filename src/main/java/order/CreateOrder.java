package order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static constant.APIStellarBurgers.*;
import static io.restassured.RestAssured.given;

public class CreateOrder{
    @Step ("получение ингредиентов")
    public static Response getAllIngredients() {
        return given()
                .get(BASE_URL + ORDER_GET);
    }

    @Step ("заказ с авторизацией")
    public static Response createOrdersLoggedInUser( Order order,String token){
        return given()
               .header("Content-type", "application/json")
               .header("Authorization", token)
               .body(order)
               .post(BASE_URL + ORDER_POST_CREATE);
    }
    @Step ("заказ без авторизацией")
    public static Response createOrdersNoLoggedInUser(Order order){
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .post(BASE_URL + ORDER_POST_CREATE);
    }
    @Step ("получение заказов   авторезированного пользователя")
    public static Response getOrdersLoggedInUser( String token, Order order){
        return given()
                .header("Content-type", "application/json")
                .and()
                .header("Authorization", token)
                .get(BASE_URL + ORDER_POST_CREATE);
    }
    @Step ("получение заказов   неавторезированного пользователя")
    public static Response getOrdersNoLoggedInUser( Order order){
        return given()
                .header("Content-type", "application/json")
                .and()
                .get(BASE_URL + ORDER_POST_CREATE);
    }
}
