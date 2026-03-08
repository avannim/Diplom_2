package model;

import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String createdAt;
    private List<Ingredient> ingredients;
    private String name;
    private int number;
    private Owner owner;
    private int price;
    private String status;
    private String updatedAt;
    private String _id;

    public void Order(int number) {
        this.number = number;
    }

}
