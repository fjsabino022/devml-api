package dev.fsabino.devml_api.config;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

/**
 * Clase que permite la conexion con la base de datos en memoria Redis.
 * 
 * @author francosabino
 *
 **/
@Component("jedisrepository")
public class JedisRepository {

	private static Jedis jedis = null;

	private JedisRepository() {

	}

	public Jedis getInstance() throws URISyntaxException {
		
		if (jedis==null){
			URI redisURI = new URI(System.getenv("REDIS_URL"));
			jedis = new Jedis(redisURI);
		}
		
		return jedis;
	}
}
