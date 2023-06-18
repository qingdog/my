package com.itheima.exception;

import org.springframework.http.ResponseEntity;

public class BusinessException extends RuntimeException{

    private ResponseEntity responseEntity;

    public BusinessException(ResponseEntity responseEntity){
        this.responseEntity = responseEntity;
    }

    public ResponseEntity getResponseEntity() {
        return responseEntity;
    }
}
