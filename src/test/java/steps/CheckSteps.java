package steps;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import dto.response.ErrorResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckSteps {

    private Gson gson = new Gson();

    @Step("Проверить соответствие статуса запроса из ответа статусу {status}")
    public void checkRequestStatus(Response response, int status) {
        response.then().statusCode(status);
    }

    @Step("Проверить соответствие текста ответа сервера тексту из документации")
    public void checkRequestErrorMessage(String docMessage, Response response) {
        ErrorResponse errorResponse = gson.fromJson(response.body().asString(), ErrorResponse.class);
        assertEquals(docMessage, errorResponse.getMessage(), "Сервер вернул не корректное сообщение.");
    }
}
