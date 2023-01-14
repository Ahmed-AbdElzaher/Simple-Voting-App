package com.userservice.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(value = HttpStatus.CONFLICT)
public class NotValidException extends RuntimeException {
    public NotValidException(String message){
        super(message);
    }
}
