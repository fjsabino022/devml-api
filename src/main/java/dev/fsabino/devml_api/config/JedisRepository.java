package dev.fsabino.devml_api.config;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

@Component("jedisrepository")
public class JedisRepository {

	private static Jedis jedis = new Jedis("localhost");;
	
	private JedisRepository(){
		
	}
	
	public Jedis getInstance(){
		return jedis;
	}
}
