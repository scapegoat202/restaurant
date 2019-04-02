package cn.varfunc.restaurant.util;

import cn.varfunc.restaurant.domain.response.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse handleException(Exception e) {
        e.printStackTrace();
        return ErrorResponse.builder()
                .message(e.getMessage())
                .data(e.getStackTrace())
                .build();
    }
}
