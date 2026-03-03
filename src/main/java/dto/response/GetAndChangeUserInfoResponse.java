package dto.response;

import lombok.*;
import model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAndChangeUserInfoResponse {

    private String success;
    private User user;

}
