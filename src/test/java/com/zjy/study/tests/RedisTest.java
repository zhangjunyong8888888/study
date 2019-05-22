package com.zjy.study.tests;

import com.zjy.study.StudyApplicationTests;
import com.zjy.study.modules.redis.bean.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisTest extends StudyApplicationTests {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testString() {
        ValueOperations<String,Object> valueOperations =  redisTemplate.opsForValue();
        valueOperations.set("user-1",new User("1","张三",18));
        System.out.println(valueOperations.get("user-1"));
    }
}
