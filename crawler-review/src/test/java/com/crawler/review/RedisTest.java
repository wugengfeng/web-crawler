package com.crawler.review;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by acer on 2019/2/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AukeyCrawlerReviewApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void append() {
        redisTemplate.opsForValue().set("test", "test");
    }

    @Test
    public void get() {
        System.out.println(redisTemplate.opsForValue().get("test"));
    }

    @Test
    public void hSet() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        redisTemplate.opsForHash().put("hash", "key", "value");

    }

    @Test
    public void hGet() {
        System.out.println(redisTemplate.opsForHash().get("hash", "key"));
    }


}
