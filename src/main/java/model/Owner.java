package model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

    private String email;
    private String createdAt;
    private String updatedAt;
    private String name;
}
