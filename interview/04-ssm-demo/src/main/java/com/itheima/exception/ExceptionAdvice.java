package com.itheima.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)//处理不可控异常
    @ResponseBody
    public ResponseEntity exception(Exception e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器内部错误");
    }

    @ExceptionHandler(BusinessException.class)//处理可控异常  自定义异常
    @ResponseBody
    public ResponseEntity exception(BusinessException be){
        be.printStackTrace();
        return ResponseEntity.status(be.getResponseEntity().getStatusCode()).body(be.getResponseEntity().getBody());
    }
}
