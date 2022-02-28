package com.hei001.seckill.exception;

import com.hei001.seckill.vo.RespBean;
import com.hei001.seckill.vo.RespBeanEnum;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理类
 * @author HEI001
 * @date 2022/2/28 13:30
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)////拦截所有异常
    public RespBean exceptionHandler(Exception e){
        if(e instanceof GlobalException) {
            GlobalException ex = (GlobalException)e;
            return RespBean.error(ex.getRespBeanEnum());
        }else if(e instanceof BindException) {
            BindException ex = (BindException)e;
            RespBean respBean=RespBean.error(RespBeanEnum.BIND_ERROR);
            respBean.setMessage("参数校验器异常"+ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;
        }
            return RespBean.error(RespBeanEnum.ERROR);

    }

}
