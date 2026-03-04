package tests;

import com.google.gson.Gson;
import dto.request.CreateUserRequest;
import dto.response.CreateAndLoginUserResponse;
import enums.APIEndpoints;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.CheckSteps;
import steps.SendRequestStep;

public class CreateUserTest {

    private final SendRequestStep sendRequestStep = new SendRequestStep();
    private final Gson gson = new Gson();
    private final CheckSteps checkSteps = new CheckSteps();

    @Test
    @DisplayName("Проверка успешного создания пользователя")
    public void checkCreateUserTest() {
        CreateUserRequest newUserRequest = new CreateUserRequest("email@emqil.ru", "Qwerty123@", "Тестовый юзер");
        Response response = sendRequestStep.sendPostRequest(APIEndpoints.CREATE_USER.getPath(), gson.toJson(newUserRequest));
        checkSteps.checkRequestStatus(response, 200);
        CreateAndLoginUserResponse newUserResponse = gson.fromJson(response.body().asString(), CreateAndLoginUserResponse.class);
        response = sendRequestStep.sendDeleteRequest(APIEndpoints.ACTIONS_USER.getPath(), newUserResponse.getAccessToken());
        checkSteps.checkRequestStatus(response, 202);
    }

    @Test
    @DisplayName("Проверка ошибки при создании дубликата пользователя")
    public void checkCreateUserDublicateErrorTest() {
        CreateUserRequest newUserRequest = new CreateUserRequest("email@emqil.ru", "Qwerty123@", "Тестовый юзер");
        Response response = sendRequestStep.sendPostRequest(APIEndpoints.CREATE_USER.getPath(), gson.toJson(newUserRequest));
        checkSteps.checkRequestStatus(response, 200);

        Response dublicateResponse = sendRequestStep.sendPostRequest(APIEndpoints.CREATE_USER.getPath(), gson.toJson(newUserRequest));
        checkSteps.checkRequestStatus(dublicateResponse, 403);
        checkSteps.checkRequestErrorMessage("User already exists", dublicateResponse);

        CreateAndLoginUserResponse newUserResponse = gson.fromJson(response.body().asString(), CreateAndLoginUserResponse.class);
        response = sendRequestStep.sendDeleteRequest(APIEndpoints.ACTIONS_USER.getPath(), newUserResponse.getAccessToken());
        checkSteps.checkRequestStatus(response, 202);
    }

    @Test
    @DisplayName("Проверка ошибки при создании пользователя без одного из обязательных полей")
    public void checkCreateUserWithoutRequiredFieldErrorTest() {
        CreateUserRequest newUserRequest = new CreateUserRequest("email", "Qwerty123@", "");
        Response response = sendRequestStep.sendPostRequest(APIEndpoints.CREATE_USER.getPath(), gson.toJson(newUserRequest));
        checkSteps.checkRequestStatus(response, 403);
        checkSteps.checkRequestErrorMessage("Email, password and name are required fields", response);
    }

}
