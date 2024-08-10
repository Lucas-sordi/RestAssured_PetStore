package factory;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Data
public class StoreFactory {
    private int id;
    private int petId;
    private int quantity;
    private String shipDate;
    private String status;
    private boolean complete;

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public StoreFactory(Integer id, Integer petId, Integer quantity, String shipDate, String status, Boolean complete) {
        this.id = id != null ? id : faker.number().numberBetween(1, Integer.MAX_VALUE);
        this.petId = petId != null ? petId : faker.number().numberBetween(1, Integer.MAX_VALUE);
        this.quantity = quantity != null ? quantity : faker.number().numberBetween(1, 10);
        this.shipDate = shipDate != null ? shipDate : generateRandomDate();
        this.status = status != null ? status : "placed";
        this.complete = complete != null ? complete : faker.bool().bool();
    }

    private String generateRandomDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return sdf.format(new Date());
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static StoreFactory create(Integer id, Integer petId, Integer quantity, String shipDate, String status, Boolean complete) {
        return new StoreFactory(id, petId, quantity, shipDate, status, complete);
    }
}
