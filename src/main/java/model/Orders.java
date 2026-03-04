package model;

import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    private List<String> ingredients;
    private String _id;
    private String status;
    private int number;
    private String createdAt;
    private String updatedAt;

}
