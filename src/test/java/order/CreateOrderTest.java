package order;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import user.BaseTestLogIn;
import  java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import io.restassured.response.Response;
import static org.apache.http.HttpStatus.*;
public class CreateOrderTest extends BaseTestLogIn {

   private CreateOrder createOrder;
    private Order order;

    @Test
    @DisplayName("Создание заказа с  авторизацией")
    public void createOderWithAuthorizationTest() {
        createOrder = new CreateOrder();
        order = new Order();
        Response response = CreateOrder.getAllIngredients();
        List<String> list = response.then().extract().path("data._id");
        List<String> ingredients = order.getIngredients();
        ingredients.add(list.get(0));
        ingredients.add(list.get(4));
        ingredients.add(list.get(2));
       ingredients.add(list.get(0));
       Response responseCreateOrder = CreateOrder.createOrdersLoggedInUser(order,token);
        responseCreateOrder.then().assertThat().statusCode(SC_OK).body("success", equalTo(true));
    }
    @Test
    @DisplayName("Создание заказа без   авторизацией")
    public void createOderNoAuthorizationTest() {
        createOrder = new CreateOrder();
        order = new Order();
        Response response = CreateOrder.getAllIngredients();
        List<String> list = response.then().extract().path("data._id");
        List<String> ingredients = order.getIngredients();
        ingredients.add(list.get(0));
        ingredients.add(list.get(4));
        ingredients.add(list.get(2));
        ingredients.add(list.get(0));
        CreateOrder.createOrdersNoLoggedInUser(order).then().assertThat().statusCode(SC_OK);
    }
    @Test
    @DisplayName("Создание заказа неверный хеш с авторизацией")
    public void createOderWithAuthorizationInvalidHashTest() {
        createOrder = new CreateOrder();
        order = new Order();
         CreateOrder.getAllIngredients();
        List<String> ingredients = order.getIngredients();
        ingredients.add("hfhfhfhf");
        Response responseCreateOrder = CreateOrder.createOrdersLoggedInUser(order,token);
        responseCreateOrder.then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Создание заказа с  авторизацией без ингридиентов")
    public void createOderWithAuthorizationNoIngredientsTest() {
        createOrder = new CreateOrder();
        order = new Order();
        Response responseCreateOrder = CreateOrder.createOrdersLoggedInUser(order,token);
        responseCreateOrder.then().assertThat().statusCode(SC_BAD_REQUEST).body("success", equalTo(false));
    }

}