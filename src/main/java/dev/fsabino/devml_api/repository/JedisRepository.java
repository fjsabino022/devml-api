package dev.fsabino.devml_api.repository;

import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

@Repository("jedisrepository")
public class JedisRepository {

	private static Jedis jedis = new Jedis("localhost");
	
	private JedisRepository(){
		
	}
	
	public static Jedis getInstance(){
		return jedis;
	}	
}
