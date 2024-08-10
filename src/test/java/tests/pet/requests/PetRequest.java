package tests.pet.requests;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class PetRequest {
    private final String PATH_PETS = "/pet";

    public Response getPetById(int id) {
        return given()
                .pathParam("petId", id)
                .when()
                .get(PATH_PETS + "/{petId}");
    }

    public Response findPetsByStatus(String status) {
        return given()
                .queryParam("status", status)
                .when()
                .get(PATH_PETS + "/findByStatus");
    }

    public Response createPet(String payload) {
        return given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post(PATH_PETS);
    }

    public Response updatePet(String payload) {
        return given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .put(PATH_PETS);
    }
}
