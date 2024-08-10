package tests.pet.tests;

import factory.PetFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.base.tests.BaseTest;
import tests.pet.requests.PetRequest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class PetTest extends BaseTest {

    PetRequest petRequest = new PetRequest();

    @Test
    @DisplayName("Pesquisar por um pet inexistente")
    public void deveBuscarPetComIdInvalido() {
        int invalidId = -1;

        petRequest.getPetById(invalidId)
                .then()
                .statusCode(404)
                .body(matchesJsonSchemaInClasspath("schemas/pet/buscaPetIdInvalido.json"));
    }

    @Test
    @DisplayName("Pesquisar por pets com status “pending”")
    public void deveBuscarPetsComStatusPendente() {
        String status = "pending";

        petRequest.findPetsByStatus(status)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/pet/buscaPetsPorStatus.json"))
                .body("status", everyItem(equalTo(status)));
    }

    @Test
    @DisplayName("Atualizar dados de um pet existente")
    public void deveCadastrarEAtualizarDadosDoPetExistente() {
        // Cadastra o pet
        PetFactory createPetData = PetFactory.create(null, null, null, null);
        int petId = createPetData.getId();

        petRequest.createPet(createPetData.toJson())
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/pet/cadastraPet.json"))
                .body("id", equalTo(petId))
                .body("category.id", equalTo(createPetData.getCategory().getId()))
                .body("category.name", equalTo(createPetData.getCategory().getName()))
                .body("name", equalTo(createPetData.getName()))
                .body("status", equalTo(createPetData.getStatus()));


        // Atualiza o pet com novos dados
        PetFactory updatePetData = PetFactory.create(petId, null, null, null);

        petRequest.updatePet(updatePetData.toJson())
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/pet/cadastraPet.json"))
                .body("id", equalTo(petId))
                .body("category.id", equalTo(updatePetData.getCategory().getId()))
                .body("category.name", equalTo(updatePetData.getCategory().getName()))
                .body("name", equalTo(updatePetData.getName()))
                .body("status", equalTo(updatePetData.getStatus()));


        // Verifica que o pet foi atualizado corretamente na busca de pet
        petRequest.getPetById(petId)
                .then()
                .statusCode(200)
                .body("id", equalTo(petId))
                .body("category.id", equalTo(updatePetData.getCategory().getId()))
                .body("category.name", equalTo(updatePetData.getCategory().getName()))
                .body("name", equalTo(updatePetData.getName()))
                .body("status", equalTo(updatePetData.getStatus()));
    }
}
