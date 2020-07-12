package com.sample.kvservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class KeyNotFoundException extends  RuntimeException  {

    public KeyNotFoundException(String msg){
        super(msg);
    }
}
