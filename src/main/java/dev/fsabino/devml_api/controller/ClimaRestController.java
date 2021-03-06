package dev.fsabino.devml_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.fsabino.devml_api.model.Clima;
import dev.fsabino.devml_api.service.MeteorologiaService;

/**
 * Clase restfull que es el punto de entrada de la aplicacion.
 * @author francosabino
 *
 **/
@RestController
@RequestMapping("/")
public class ClimaRestController {

	@Autowired
	@Qualifier("meteorologiaServiceImpl")
	private MeteorologiaService service;
	
	@GetMapping("/")
	public ResponseEntity<String> welcome() {
		return new ResponseEntity<String>("Test Franco Sabino.", HttpStatus.OK);
	}
	
	@GetMapping("/ping")
	public ResponseEntity<String> ping() {
		return new ResponseEntity<String>("pong..", HttpStatus.OK);
	}

	@GetMapping("/ejercicios")
     public ResponseEntity<String> calcular3Ejercicios() {
		try{
			 return new ResponseEntity<String>(service.enunciado123(), HttpStatus.OK); 
		}catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
     }
	
	@GetMapping("/clima")
    public ResponseEntity<Clima> getClimabyDia(@RequestParam(required = true, value = "dia") Integer dia) {
		try{
			if (dia!= null && dia >=0 && dia <= 720){
				Clima clima = service.getClimaByDia(dia);
				return new ResponseEntity<Clima>(clima, HttpStatus.OK);
			}else{
				return new ResponseEntity<Clima>(HttpStatus.NOT_ACCEPTABLE);
			}
		}catch (Exception e) {
			return new ResponseEntity<Clima>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
