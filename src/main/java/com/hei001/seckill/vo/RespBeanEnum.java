package com.hei001.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author HEI001
 * @date 2022/2/28 12:59
 */
@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务器异常"),
    //登录模块
    LOGIN_ERROR(500210,"用户名或密码不正确"),
    MOBILE_ERROR(500211,"手机号码格式不正确"),
    BIND_ERROR(200212,"参数校验异常"),
    //秒杀模块
    Empty_STOCK(500500,"库存不足"),
    REPEATE_ERROR(500501,"该商品每人限购一件")

            ;

    private final Integer code;
    private final String message;
}
