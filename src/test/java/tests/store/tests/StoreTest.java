package tests.store.tests;

import factory.PetFactory;
import factory.StoreFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.base.tests.BaseTest;
import tests.pet.requests.PetRequest;
import tests.store.requests.StoreRequest;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class StoreTest extends BaseTest {

    private final PetRequest petRequest = new PetRequest();
    private final StoreRequest storeRequest = new StoreRequest();

    @Test
    @DisplayName("Cadastrar um pedido para o pet com sucesso")
    public void deveCadastrarPetECadastrarPedidoParaOPet() {
        // Cadastra um pet
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


        // Cadastra um pedido para o pet
        StoreFactory createStoreData = StoreFactory.create(null, petId, null, null, null, null);

        storeRequest.createStore(createStoreData.toJson())
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/store/cadastraPedido.json"))
                .body("id", equalTo(createStoreData.getId()))
                .body("petId", equalTo(petId))
                .body("quantity", equalTo(createStoreData.getQuantity()))
                .body("status", equalTo(createStoreData.getStatus()))
                .body("complete", equalTo(createStoreData.isComplete()));
    }
}
