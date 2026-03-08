package tests;

import com.google.gson.Gson;
import dto.request.CreateUserRequest;
import dto.request.LoginUserRequest;
import dto.response.CreateAndLoginUserResponse;
import enums.APIEndpoints;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import steps.CheckSteps;
import steps.SendRequestStep;

public class LoginUserTest {

    private final Gson gson = new Gson();
    private final SendRequestStep sendRequestStep = new SendRequestStep();
    private final CheckSteps checkSteps = new CheckSteps();
    private final CreateUserRequest newUserRequest = new CreateUserRequest("email@emqil.ru", "Qwerty123@", "Тестовый юзер");
    private CreateAndLoginUserResponse userResponse;

    @BeforeEach
    void setUp() {
        Response response = sendRequestStep.sendPostRequest(APIEndpoints.CREATE_USER.getPath(), gson.toJson(newUserRequest));
        checkSteps.checkRequestStatus(response, 200);
        userResponse = gson.fromJson(response.body().asString(), CreateAndLoginUserResponse.class);
    }

    @Test
    public void checkLoginRequestTest(){
        LoginUserRequest loginUserRequest = new LoginUserRequest(newUserRequest.getEmail(), newUserRequest.getPassword());
        Response response = sendRequestStep.sendPostRequest(APIEndpoints.LOGIN_USER.getPath(), gson.toJson(loginUserRequest));
        checkSteps.checkRequestStatus(response, 200);
        userResponse = gson.fromJson(response.body().asString(), CreateAndLoginUserResponse.class);
    }

    @Test
    public void checkLoginRequestWithWrongDataTest() {
        LoginUserRequest loginUserRequest = new LoginUserRequest("email@gmail.com", "123456");
        Response response = sendRequestStep.sendPostRequest(APIEndpoints.LOGIN_USER.getPath(), gson.toJson(loginUserRequest));
        checkSteps.checkRequestStatus(response, 401);
        checkSteps.checkRequestErrorMessage("email or password are incorrect", response);
    }

    @AfterEach
    void tearDown() {
        Response response = sendRequestStep.sendDeleteRequest(APIEndpoints.ACTIONS_USER.getPath(), userResponse.getAccessToken());
        checkSteps.checkRequestStatus(response, 202);
    }
}
