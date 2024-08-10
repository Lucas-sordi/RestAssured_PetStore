package factory;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import lombok.Data;

import java.util.Locale;

@Data
public class PetFactory {
    private int id;
    private Category category;
    private String name;
    private String status;

    private static final Faker faker = new Faker(new Locale("pt-BR"));

    public PetFactory(Integer id, Category category, String name, String status) {
        this.id = id != null ? id : faker.number().numberBetween(1, Integer.MAX_VALUE);
        this.category = category != null ? category : new Category(faker.number().numberBetween(1, Integer.MAX_VALUE), faker.animal().name());
        this.name = name != null ? name : faker.name().name();
        this.status = status != null ? status : faker.options().option("available", "sold", "pending");
    }

    @Data
    public static class Category {
        private int id;
        private String name;

        public Category(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static PetFactory create(Integer id, Category category, String name, String status) {
        return new PetFactory(id, category, name, status);
    }
}
