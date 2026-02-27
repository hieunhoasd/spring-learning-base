package HotWhellShop_Spring_react.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestResponse<T> {
    private int status;
    private String message;
    private T data;
    private String error;

}
