package com.hei001.seckill.config;

import com.hei001.seckill.pojo.User;

/**
 * @author HEI001
 * @date 2022/3/2 20:57
 */
public class UserContext {
    private static ThreadLocal<User> userHolder=new ThreadLocal<>();
    public static void setUser(User user){
        userHolder.set(user);
    }
    public static User getUser(){
        return userHolder.get();
    }
}
