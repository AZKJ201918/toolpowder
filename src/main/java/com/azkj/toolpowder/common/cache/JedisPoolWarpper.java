package com.azkj.toolpowder.common.cache;


import com.azkj.toolpowder.common.constants.Parameters;
import com.azkj.toolpowder.common.exception.ToolPowderExcption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

@Component
public class JedisPoolWarpper {

    private JedisPool jedisPool=null;

    private Logger logger= LoggerFactory.getLogger(JedisPoolWarpper.class);

    @Autowired
    private Parameters parameters;

    @PostConstruct
    public  void  init() throws ToolPowderExcption {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxWaitMillis(parameters.getRedisMaxWaitMillis());
            config.setMaxIdle(parameters.getRedisMaxIdle());
            config.setMaxTotal(parameters.getRedisMaxTotal());
            jedisPool = new JedisPool(config, parameters.getRedisHost(), parameters.getRedisPort(), 2000);
        }catch (Exception e){
            logger.error("fail to inittialize redis pool" ,e);
            throw  new ToolPowderExcption("redis链接异常");
        }
    }

    public JedisPool  getJedisPool(){

        return jedisPool;
    }



}
