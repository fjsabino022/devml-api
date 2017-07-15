package dev.fsabino.devml_api.repository;

import dev.fsabino.devml_api.model.Clima;

/**
 * Declaracion de metodos del repositorio.
 * @author francosabino 
 */
public interface ClimaRepository {

	Clima saveClima(Clima clima) throws Exception;
	Clima findClimaByDia(Integer id) throws Exception;
}
