package com.xcale.cart.exception;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handleValidationException(BusinessException ex,
                                                               WebRequest request) {
        return handleMessage(ex);
    }

    private ResponseEntity<Object> handleMessage(Exception ex) {
        logger.error(ex.getMessage(), ex);
        Map<String, String> map = new HashMap<>();
        map.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(map);
    }
}
