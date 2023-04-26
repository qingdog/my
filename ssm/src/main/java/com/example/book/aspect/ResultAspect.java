package com.example.book.aspect;

import com.alibaba.fastjson.JSONObject;
import com.example.config.common.Code;
import com.example.config.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;

@Component
@Aspect
public class ResultAspect {
    @Pointcut("execution(Object com.example.book.controller.*Controller.*(..))")
    private void point() {
    }

    @Around("point()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 表示对原始操作的调用
        Object proceed = proceedingJoinPoint.proceed();
        if (proceed instanceof Result) {
            return proceed;
        }

        String message = "";
        String detail = null;
        if (proceed == null) {
            message = "数据为空！";

            //获取执行签名信息
            Signature signature = proceedingJoinPoint.getSignature();
            //通过签名获取执行操作名称(接口名)
            String className = signature.getDeclaringTypeName();
            //通过签名获取执行操作名称(方法名)
            String methodName = signature.getName();

            HashMap<String, String> map = new HashMap<>();
            map.put("methodName", methodName);
            map.put("args", Arrays.toString(proceedingJoinPoint.getArgs()));
            detail = new ObjectMapper().writeValueAsString(map);
        }
        return new Result(proceed != null ? Code.GET_OK : Code.GET_ERR, proceed, message, detail);
    }
}
