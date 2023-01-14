package com.userservice.Exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice 
public class UserGlopalExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
        UserExceptionResponse exception = new UserExceptionResponse(
            ex.getMessage(),
            request.getDescription(false), 
            new Date());
        return new ResponseEntity<Object>(exception, HttpStatus.INTERNAL_SERVER_ERROR); 
    }

    @ExceptionHandler(NotFoundException.class) 
    public ResponseEntity<Object> handleNotFoundExceptions(Exception ex, WebRequest request){
        UserExceptionResponse exception = new UserExceptionResponse(
            ex.getMessage(),
            request.getDescription(false), 
            new Date());
        return new ResponseEntity<Object>(exception, HttpStatus.NOT_FOUND); 
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid( 
                                            MethodArgumentNotValidException ex, 
                                            HttpHeaders headers,
                                            HttpStatus status,
                                            WebRequest request){
        UserExceptionResponse exception = new UserExceptionResponse(
        "Invalid Inputs", 
        ex.getBindingResult().toString(), 
        new Date()); 
        return new ResponseEntity<Object>(exception, HttpStatus.BAD_REQUEST); 
    }

}
