package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import user.BaseTestLogIn;

import java.util.List;

import static org.apache.http.HttpStatus.*;

public class GetOrderTest extends BaseTestLogIn {
    private CreateOrder createOrder;
    private Order order;
    @Test
    @DisplayName("получение  заказа   неавторизированного пользователя")
    public void getOderNoAuthorizationTest() {
        createOrder = new CreateOrder();
        order = new Order();
        Response response = CreateOrder.getAllIngredients();
        List<String> list = response.then().extract().path("data._id");
        List<String> ingredients = order.getIngredients();
        ingredients.add(list.get(0));
        ingredients.add(list.get(4));
        ingredients.add(list.get(2));
        ingredients.add(list.get(0));
        CreateOrder.getOrdersNoLoggedInUser(order).then().assertThat().statusCode(SC_UNAUTHORIZED);
    }
    @Test
    @DisplayName("получение  заказа   авторизированного пользователя")
    public void getOderAuthorizationTest() {
        createOrder = new CreateOrder();
        order = new Order();
        Response response = CreateOrder.getAllIngredients();
        List<String> list = response.then().extract().path("data._id");
        List<String> ingredients = order.getIngredients();
        ingredients.add(list.get(0));
        ingredients.add(list.get(4));
        ingredients.add(list.get(2));
        ingredients.add(list.get(0));
        CreateOrder.getOrdersLoggedInUser(token, order).then().assertThat().statusCode(SC_OK);
    }
}
