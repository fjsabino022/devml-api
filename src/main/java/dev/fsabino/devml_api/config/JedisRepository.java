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

	public Jedis getInstance(){
		
		if (jedis==null){
			
			URI redisURI = null;
			
			try {
			
				//redisURI = new URI(System.getenv("REDIS_URL"));
				jedis = new Jedis("localhost");
				
			//} catch (URISyntaxException e) {
			//	e.printStackTrace();
			//	return null;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			
			
		}
		
		return jedis;
	}
}
