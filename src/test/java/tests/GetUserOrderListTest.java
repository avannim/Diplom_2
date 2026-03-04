package tests;

import com.google.gson.Gson;
import dto.request.CreateOrderRequest;
import dto.request.CreateUserRequest;
import dto.response.CreateAndLoginUserResponse;
import dto.response.GetOrdersResponse;
import enums.APIEndpoints;
import enums.IngredientHash;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import steps.CheckSteps;
import steps.SendRequestStep;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUserOrderListTest {

    private final Gson gson = new Gson();
    private final SendRequestStep sendRequestStep = new SendRequestStep();
    private final CheckSteps checkSteps = new CheckSteps();
    private CreateAndLoginUserResponse userResponse;

    @BeforeEach
    void setUp() {
        CreateUserRequest newUserRequest = new CreateUserRequest("email@emqil.ru", "Qwerty123@", "Тестовый юзер");
        Response response = sendRequestStep.sendPostRequest(APIEndpoints.CREATE_USER.getPath(), gson.toJson(newUserRequest));
        checkSteps.checkRequestStatus(response, 200);
        userResponse = gson.fromJson(response.body().asString(), CreateAndLoginUserResponse.class);

        CreateOrderRequest requestHash = new CreateOrderRequest(List.of(IngredientHash.SPICY_SAUCE.getIngredient(),IngredientHash.PROTOSTOMIA_MEAT.getIngredient(),IngredientHash.MINI_SALAD.getIngredient(),IngredientHash.FLUORECENT_BUN.getIngredient()));
        response = sendRequestStep.sendPostRequestWithAuthorization(APIEndpoints.ACTIONS_ORDER.getPath(), gson.toJson(requestHash), userResponse.getAccessToken());
        checkSteps.checkRequestStatus(response, 200);
    }

    @Test
    public void checkGetUserOrderListTest(){
        Response response = sendRequestStep.sendGetRequestWithAuthorization(APIEndpoints.ACTIONS_ORDER.getPath(), userResponse.getAccessToken());
        checkSteps.checkRequestStatus(response, 200);
        GetOrdersResponse orderList = gson.fromJson(response.body().asString(), GetOrdersResponse.class);
        assertEquals(1, orderList.getOrders().size(), "Количество заказов в ответе не совпадает с 1");
    }

    @Test
    public void checkGetUserOrderListWhithoutAuthorizationTest() {
        Response response = sendRequestStep.sendGetRequest(APIEndpoints.ACTIONS_ORDER.getPath());
        checkSteps.checkRequestStatus(response, 401);
        checkSteps.checkRequestErrorMessage("You should be authorised", response);
    }

    @AfterEach
    void tearDown() {
        Response response = sendRequestStep.sendDeleteRequest(APIEndpoints.ACTIONS_USER.getPath(), userResponse.getAccessToken());
        checkSteps.checkRequestStatus(response, 202);
    }
}
