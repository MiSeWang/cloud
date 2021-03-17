package com.mrlv.api.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void Test() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
        redisTemplate.opsForValue().set("name","Korbin");
        redisTemplate.opsForValue().set("age",13);
//        redisTemplate.opsForValue().increment("age",2);
        System.out.println(redisTemplate.opsForValue().get("name"));
    }
}
