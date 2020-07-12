package com.sample.kvservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NodeNotFoundException  extends  RuntimeException {

    public NodeNotFoundException(String msg){
        super(msg);
    }
}
