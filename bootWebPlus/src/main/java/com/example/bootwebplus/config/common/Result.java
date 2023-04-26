package com.example.bootwebplus.config.common;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Result implements Serializable {

    // 数据列表
    private Object data;

    private String message;

    public Result(Object data) {
        this.data = data;
    }

    public Result(Object data, String message) {
        this.data = data;
        this.message = message;
    }

}