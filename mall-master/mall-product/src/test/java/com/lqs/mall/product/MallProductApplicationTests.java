package com.lqs.mall.product;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisPool;

@RunWith(SpringRunner.class)
@SpringBootTest
class MallProductApplicationTests {

	@Autowired
	private JedisPool jedisPool;

	@Autowired
	private RedissonClient redissonClient;


	@Test
	void contextLoads() {
		System.out.println(redissonClient);
	}

}
