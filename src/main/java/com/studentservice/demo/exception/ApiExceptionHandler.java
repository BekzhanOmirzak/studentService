package com.studentservice.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException ex) {
        //1. Create payload containing exception
        ApiException apiException = new ApiException(
                ex.getMessage(),
                BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        //2.Return response entity
        return new ResponseEntity<>(apiException, BAD_REQUEST);
    }






}
