package dev.fsabino.devml_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.fsabino.devml_api.service.MeteorologiaService;

@RestController
@RequestMapping("/")
public class ExerciseRestController {

	@Autowired
	@Qualifier("meteorologiaServiceImpl")
	private MeteorologiaService service;

	@GetMapping("/ping")
	public ResponseEntity<String> ping() {
		return new ResponseEntity<String>("pong..", HttpStatus.OK);
	}

	@GetMapping("/")
     public ResponseEntity<String> calcular3Ejercicios() {
		try{
			 return new ResponseEntity<String>(service.calcular(), HttpStatus.OK); 
		}catch (ArithmeticException e) {
			 return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
     }

}
