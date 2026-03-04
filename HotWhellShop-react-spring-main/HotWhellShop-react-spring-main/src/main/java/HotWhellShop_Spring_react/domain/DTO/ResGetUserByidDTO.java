package HotWhellShop_Spring_react.domain.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResGetUserByidDTO {
    private String name;
    private Long id;
    private String email;
}
