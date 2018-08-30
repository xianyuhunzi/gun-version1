package com.stylefeng.guns.redis;/**
 * Created by zhengr on 2018/8/30.
 *
 * @Description todo
 */

import com.stylefeng.guns.core.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * code is far away from bug with the animal protecting
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 * @Description
 * ---------------------------------
 * @Author : zr
 * @Date :  2018/8/3017:20
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestRedis {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private  RedisUtil redisUtil;

    @Test
    public  void testSet(){
       boolean flag= redisUtil.set("zhang3",18);
       if(flag){
           logger.info("redis set {}  successfully","zhang3");
       }
    }

    @Test
    public void testGet(){
        redisUtil.get("zhang3");

    }


}

