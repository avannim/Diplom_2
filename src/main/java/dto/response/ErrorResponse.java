package dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String success;
    private String message;

}
