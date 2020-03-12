package com.guoss.student.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import javax.jws.soap.SOAPBinding;
import java.util.Map;

@Component
public class TestRedis {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

//    @Autowired
//    ObjectMapper  objectMapper;

    public void testRedis(){

//        redisTemplate.opsForValue().set("k1","ooxx");
//        System.out.print(redisTemplate.opsForValue().get("k1"));

        RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
        conn.set("k2".getBytes(),"guoss".getBytes());
        System.out.print(new String(conn.get("k2".getBytes())));

//        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
//        hash.put("guoss","name","leader");
//        hash.put("guoss","age","18");
//
//        System.out.println(hash.entries("guoss"));


//        Jackson2HashMapper jm = new Jackson2HashMapper(objectMapper, false);

//        stringRedisTemplate.opsForHash().putAll("sean01",jm.toHash(p));

        Map map = stringRedisTemplate.opsForHash().entries("sean01");
//
//        User per = objectMapper.convertValue(map, User.class);
//        System.out.println(per.getName());


        stringRedisTemplate.convertAndSend("ooxx","hello");

        RedisConnection cc = stringRedisTemplate.getConnectionFactory().getConnection();

        cc.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] pattern) {
                byte[] body = message.getBody();
                System.out.println(new String(body));
            }
        }, "ooxx".getBytes());

        while(true){
            stringRedisTemplate.convertAndSend("ooxx","hello  from wo zi ji ");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
}
