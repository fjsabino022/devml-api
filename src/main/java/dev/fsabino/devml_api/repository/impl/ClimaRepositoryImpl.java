package dev.fsabino.devml_api.repository.impl;

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
		jedis.getInstance().set(clima.getDia().toString(), clima.getClima().getTipoclima());
		return clima;
	}

	@Override
	public Clima findClimaByDia(Integer dia) {
		
		String tipoclima = jedis.getInstance().get(dia.toString());
		
		if (tipoclima == "" || tipoclima == null){
			return null;
		}
		
		return new Clima(dia, TipoClima.devolveTipoClima(tipoclima));	
	}
}
