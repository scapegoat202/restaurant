package cn.varfunc.restaurant.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    public NoSuchElementException handleException(NoSuchElementException e) {
        log.info("{}", e);
        return e;
    }

    // TODO: 2019/3/5 Improve Exception Handler

    // TODO: 2019/3/5 Standardize all of the response message format
}
