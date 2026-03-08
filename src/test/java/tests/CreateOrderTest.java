package tests;

import com.google.gson.Gson;
import dto.request.CreateOrderRequest;
import dto.request.CreateUserRequest;
import dto.response.CreateAndLoginUserResponse;
import enums.APIEndpoints;
import enums.IngredientHash;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import steps.CheckSteps;
import steps.SendRequestStep;

import java.util.List;


public class CreateOrderTest {
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
    }

    @Test
    public void checkCreateOrderTest(){
        CreateOrderRequest requestHash = new CreateOrderRequest(List.of(IngredientHash.SPICY_SAUCE.getIngredient(),IngredientHash.PROTOSTOMIA_MEAT.getIngredient(),IngredientHash.MINI_SALAD.getIngredient(),IngredientHash.FLUORECENT_BUN.getIngredient()));
        Response response = sendRequestStep.sendPostRequestWithAuthorization(APIEndpoints.ACTIONS_ORDER.getPath(), gson.toJson(requestHash), userResponse.getAccessToken());
        checkSteps.checkRequestStatus(response, 200);
    }

    @Test
    public void checkCreateOrderWithoutAuthorizationTest(){
        Response response = sendRequestStep.sendPostRequestWithAuthorization(APIEndpoints.LOGOUT_USER.getPath(), String.format("{\"token\":\"%s\"}", userResponse.getRefreshToken()), userResponse.getAccessToken());
        checkSteps.checkRequestStatus(response, 200);
        CreateOrderRequest wrongHash = new CreateOrderRequest(List.of(IngredientHash.GREEK_SAUCE.getIngredient(),IngredientHash.MARCIAN_ALFASAHARID.getIngredient(),IngredientHash.MAGNOLIA_MEAT.getIngredient(),IngredientHash.CRATER_BUN.getIngredient()));
        response = sendRequestStep.sendPostRequest(APIEndpoints.ACTIONS_ORDER.getPath(), gson.toJson(wrongHash));
        checkSteps.checkRequestStatus(response, 403);
        checkSteps.checkRequestErrorMessage("jwt expired", response);
    }

    @Test
    public void checkCreateOrderWithWrongIngredientHashTest(){
        CreateOrderRequest wrongHash = new CreateOrderRequest(List.of(IngredientHash.SPACE_SAUCE.getIngredient(),IngredientHash.MINERAL_CIRCLES.getIngredient(),IngredientHash.TETRADONTIMFORM_MEAT.getIngredient(),"0"));
        Response response = sendRequestStep.sendPostRequestWithAuthorization(APIEndpoints.ACTIONS_ORDER.getPath(), gson.toJson(wrongHash), userResponse.getAccessToken());
        checkSteps.checkRequestStatus(response, 500);
    }

    @Test
    public void checkCreateOrderWithoutIngredientsTest(){
        Response response = sendRequestStep.sendPostRequestWithAuthorization(APIEndpoints.ACTIONS_ORDER.getPath(), "{\"ingredients\":[]}",userResponse.getAccessToken());
        checkSteps.checkRequestStatus(response,400);
        checkSteps.checkRequestErrorMessage("Ingredient ids must be provided", response);
    }

    @AfterEach
    void tearDown() {
        Response response = sendRequestStep.sendDeleteRequest(APIEndpoints.ACTIONS_USER.getPath(), userResponse.getAccessToken());
        checkSteps.checkRequestStatus(response, 202);
    }
}
