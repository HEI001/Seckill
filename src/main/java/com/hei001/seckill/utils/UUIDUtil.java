package com.hei001.seckill.utils;

import java.util.UUID;

/**
 * @author HEI001
 * @date 2022/2/28 13:55
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
