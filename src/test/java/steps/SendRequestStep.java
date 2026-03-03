package steps;

import config.RequestConfig;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class SendRequestStep {

    @Step("Отправить с методом POST запрос на {url}")
    public Response sendPostRequest (String url, String body) {
        return given()
                .spec(RequestConfig.getRequestSpec())
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(url);
    }

    @Step("Отправить с методом POST запрос на {url}")
    public Response sendPostRequestWithAuthorization (String url, String body, String token) {
        return given()
                .spec(RequestConfig.getRequestSpec())
                .header("Content-type", "application/json")
                .and()
                .header("Authorization", token)
                .and()
                .body(body)
                .when()
                .post(url);
    }

    @Step("Отправить запрос с методом DELETE на {url}")
    public Response sendDeleteRequest (String url, String token) {
        return given().spec(RequestConfig.getRequestSpec()).header("Authorization", token).delete(url);
    }


}
