package com.bizongo.redCarpet.redis.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bizongo.redCarpet.model.UserAccessInfoDtls;
import com.bizongo.redCarpet.redis.service.RedisService;

@Component
@SuppressWarnings("deprecation")
public class RedisHashComponent {

	@Autowired
    private RedisService redisService;

    public void set(String key, UserAccessInfoDtls value){
        redisService.getRadisConnection().set(key.getBytes(), redisService.getBytes(value));
    }

	public UserAccessInfoDtls get(String key){
       return redisService.getUserAccessInfoDtlsObject(redisService.getRadisConnection().get(key.getBytes()));
    }
}
