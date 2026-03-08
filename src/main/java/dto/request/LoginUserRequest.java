package dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequest {

    private String email;
    private String password;

}
