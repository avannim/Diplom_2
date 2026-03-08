package tests;

import com.google.gson.Gson;
import dto.request.CreateUserRequest;
import dto.response.CreateAndLoginUserResponse;
import dto.response.GetAndChangeUserInfoResponse;
import enums.APIEndpoints;
import io.restassured.response.Response;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import steps.CheckSteps;
import steps.SendRequestStep;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeUserDataTest {

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

    @ParameterizedTest
    @MethodSource("userChangeData")
    @DisplayName("Проверка успешного изменения данных пользователя")
    public void checkChangeUserInfoWithAuthorizationTest(String body, User value, String errorMessage) {
        Response response= sendRequestStep.sendPatchRequestWithAuthorization(APIEndpoints.ACTIONS_USER.getPath(), body, userResponse.getAccessToken());
        checkSteps.checkRequestStatus(response, 200);
        GetAndChangeUserInfoResponse userInfoResponse = gson.fromJson(response.body().asString(), GetAndChangeUserInfoResponse.class);
        assertEquals(value, userInfoResponse.getUser(), errorMessage);
    }

    @ParameterizedTest
    @MethodSource("userChangeData")
    @DisplayName("Проверка изменения данных пользователя без авторизации")
    public void checkChangeUserInfoWithoutAuthorizationTest(String body) {
        Response response= sendRequestStep.sendPatchRequest(APIEndpoints.ACTIONS_USER.getPath(), body);
        checkSteps.checkRequestStatus(response, 401);
        checkSteps.checkRequestErrorMessage("You should be authorised", response);
    }

    @AfterEach
    void tearDown() {
        Response response = sendRequestStep.sendDeleteRequest(APIEndpoints.ACTIONS_USER.getPath(), userResponse.getAccessToken());
        checkSteps.checkRequestStatus(response, 202);
    }

    static Stream<Arguments> userChangeData() {
        return Stream.of(
                Arguments.of("{\"name\":\"Georg\"}", new User("email@emqil.ru", "Georg"), "Имя пользователя не изменено"),
                Arguments.of("{\"email\":\"georg@ya.com\"}", new User("georg@ya.com", "Тестовый юзер"), "Email пользователя не изменен"),
                Arguments.of("{\"password\":\"georg#8nM\"}", new User("email@emqil.ru", "Тестовый юзер"), "Данные пользователя не отличаются")
        );
    }
}
