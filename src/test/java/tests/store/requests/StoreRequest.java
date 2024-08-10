package tests.store.requests;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class StoreRequest {
    private final String PATH_STORE = "/store";

    public Response createStore(String payload) {
        return given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post(PATH_STORE + "/order");
    }
}