package dto.response;

import model.Orders;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrdersResponse {

    private boolean success;
    private List<Orders> orders;
    private int total;
    private int totalToday;

}
