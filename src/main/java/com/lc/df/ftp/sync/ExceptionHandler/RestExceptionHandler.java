package com.lc.df.ftp.sync.ExceptionHandler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice

public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String error = "Malformed JSON request";

        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }




        @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            IllegalArgumentException ex) {
        ApiError apiError = new ApiError(NO_CONTENT);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MultipartException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            MultipartException ex) {
        ApiError apiError = new ApiError(NO_CONTENT);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }


    private ResponseEntity buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
