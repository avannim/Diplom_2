package dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAndLoginUserResponse {
    private String success;
    private User user;
    private String accessToken;
    private String refreshToken;
}
