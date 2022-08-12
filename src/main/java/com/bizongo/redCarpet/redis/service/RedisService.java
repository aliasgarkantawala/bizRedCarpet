package com.bizongo.redCarpet.redis.service;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bizongo.redCarpet.constant.BizRedCarpetConstants;
import com.bizongo.redCarpet.model.UserAccessInfoDtls;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

@Service
public class RedisService {

    private final Environment env;
    
    private Jedis jedis=null;

    public RedisService(Environment env) {
        this.env = env;
    }

    public Jedis getRadisConnection() {
    	if(jedis == null) {
    		HostAndPort hp = new HostAndPort(env.getProperty(BizRedCarpetConstants.BRC_RADIS_HOST_PROPERTY_NAME), 
        			Integer.parseInt(env.getProperty(BizRedCarpetConstants.BRC_RADIS_PORT_PROPERTY_NAME)));
    		jedis = new Jedis(hp); 
    	}
    	
    	return jedis;
    }
    
    public byte[] getBytes(UserAccessInfoDtls obj) {
    	byte[] data = SerializationUtils.serialize(obj);
    	return data;
    }
    
    public UserAccessInfoDtls getUserAccessInfoDtlsObject(byte[] data) {
    	if(data==null)
    		return null;
    	UserAccessInfoDtls obj = (UserAccessInfoDtls) SerializationUtils.deserialize(data);
    	return obj;
    }
}
