package dto.response;

import model.Order;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderResponse {

    private String name;
    private Order order;
    private boolean success;

}
