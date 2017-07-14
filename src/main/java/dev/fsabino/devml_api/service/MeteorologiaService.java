package dev.fsabino.devml_api.service;

import dev.fsabino.devml_api.model.Clima;

public interface MeteorologiaService {

	public String enunciado123() throws ArithmeticException, Exception;
	public void calcularPronosticoExtendido (Integer dia) throws Exception;
	public Clima getClimaByDia(Integer dia) throws Exception;
}
