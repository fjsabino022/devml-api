package dev.fsabino.devml_api.service;

import java.util.Map;

public interface MeteorologiaService {

	public String enunciado123() throws ArithmeticException, Exception;
	public Map<Integer, String> getPronosticoExtendido (int dias) throws Exception;
}
