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
    MOBILE_NOT_EXIST(500213,"手机号码不存在"),
    PASSWORD_UPDATE_FAIL(500213,"密码更新异常"),
    SESSION_ERROR(500214,"用户不存在"),
    //订单模块
    ORDER_NOT_EXIST(500300,"订单信息不存在"),
    //秒杀模块
    Empty_STOCK(500500,"库存不足"),
    REPEATE_ERROR(500501,"该商品每人限购一件"),
    REQUEST_ILLEGAL(500502,"请求非法，请重新尝试"),
    ERROR_CAPTCHA(500503,"验证码错误，请重新输入"),
    ACCESS_LIMIT_REAHCED(500503,"访问过于频繁，请稍后再试"),

    ;

    private final Integer code;
    private final String message;
}
