package com.hei001.seckill;

import com.hei001.seckill.config.RedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SeckillApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisScript<Boolean> script;

    @Test
    void contextLoads() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //占位，如果key1不存在可以设置成功
        Boolean lock = valueOperations.setIfAbsent("k1", "v1");
        //设置时间
        Boolean lock2 = valueOperations.setIfAbsent("k1", "v1",5, TimeUnit.SECONDS);

        //如果占位成功,进行征程操作
        if (lock){
            valueOperations.set("name","hei");
            String name = (String) valueOperations.get("name");
            System.out.println("name: "+name);
            redisTemplate.delete("k1");
        }else {
            System.out.println("有线程在使用");
        }
    }

    @Test
    void test01() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String value = UUID.randomUUID().toString();
        Boolean lock = valueOperations.setIfAbsent("k1", value, 5, TimeUnit.SECONDS);
        if (lock){
            valueOperations.set("name","hei");
            String name = (String) valueOperations.get("name");
            System.out.println("name = "+name);
            System.out.println(valueOperations.get("k1"));
            Boolean k1 = (Boolean) redisTemplate.execute(script, Collections.singletonList("k1"), value);
            System.out.println(k1);
        }else {
            System.out.println("有线程在使用");
        }
    }
}
