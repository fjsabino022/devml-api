package dev.fsabino.devml_api.repository;

import dev.fsabino.devml_api.model.Clima;

public interface ClimaRepository {

	void saveClima(Clima clima);
	Clima findClimaByDia(Integer id);
	
	// Map<Object, Object> findAllStudents();
	//
	// void deleteStudent(String id);
}
