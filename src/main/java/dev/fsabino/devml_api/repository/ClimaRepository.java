package dev.fsabino.devml_api.repository;

import dev.fsabino.devml_api.model.Clima;

public interface ClimaRepository {

	Clima saveClima(Clima clima);
	Clima findClimaByDia(Integer id);
}