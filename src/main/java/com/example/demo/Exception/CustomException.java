package com.example.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomException {
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse Error(Exception ex, WebRequest request){
        return new ErrorResponse(200,new Message(ex));
    }
}
