package cn.varfunc.restaurant.domain.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Builder
@Data
@Accessors(chain = true)
public class ErrorResponse {
    @Builder.Default
    private String message = "No message available";
    private Object data;
}
