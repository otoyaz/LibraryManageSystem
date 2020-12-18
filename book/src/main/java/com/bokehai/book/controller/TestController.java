package com.bokehai.book.controller;

import com.bokehai.book.common.JwtUtil;
import com.bokehai.book.common.RedisUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        Jedis jedis =  RedisUtil.getJedisFromPool();
        jedis.set("test",JwtUtil.createToken("1212"));
        return jedis.get("test");
    }
}


