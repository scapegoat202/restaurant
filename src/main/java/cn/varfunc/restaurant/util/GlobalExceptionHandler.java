package cn.varfunc.restaurant.util;

import cn.varfunc.restaurant.domain.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse handleNoSuchElementExceptionException(NoSuchElementException e) {
        log.info("{}", Arrays.toString(e.getStackTrace()));
        return ApiResponse.builder()
                .message(e.getMessage())
                .build();
    }
}
