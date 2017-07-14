package dev.fsabino.devml_api.repository.impl;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import dev.fsabino.devml_api.config.JedisRepository;
import dev.fsabino.devml_api.model.Clima;
import dev.fsabino.devml_api.model.TipoClima;
import dev.fsabino.devml_api.repository.ClimaRepository;

/**
 * Interaccion con el repositorio de datos.
 * @author francosabino 
 */
@Repository
public class ClimaRepositoryImpl implements ClimaRepository {

	@Autowired
	@Qualifier("jedisrepository")
	JedisRepository jedis;
	
	@Override
	public Clima saveClima(Clima clima) {
		
		try {
		
			jedis.getInstance().set(clima.getDia().toString(), clima.getClima().getTipoclima());
		
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clima;
	}

	@Override
	public Clima findClimaByDia(Integer dia) {
		
		String tipoclima;
		try {
			tipoclima = jedis.getInstance().get(dia.toString());
			
			if (tipoclima == "" || tipoclima == null){
				return null;
			}
			
			return new Clima(dia, TipoClima.devolveTipoClima(tipoclima));	
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
	}
}
