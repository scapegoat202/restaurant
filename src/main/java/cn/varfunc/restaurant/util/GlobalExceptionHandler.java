package cn.varfunc.restaurant.util;

import cn.varfunc.restaurant.domain.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse handleNoSuchElementExceptionException(NoSuchElementException e) {
        return ApiResponse.builder()
                .message(e.getMessage())
                .build();
    }
}
