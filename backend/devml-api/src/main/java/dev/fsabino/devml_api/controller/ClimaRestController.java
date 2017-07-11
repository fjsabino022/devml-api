package dev.fsabino.devml_api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.fsabino.devml_api.service.MeteorologiaService;

@RestController
@RequestMapping("/")
public class ClimaRestController {

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
			 return new ResponseEntity<String>(service.enunciado123(), HttpStatus.OK); 
		}catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
     }
	
	@GetMapping("/extendido/{dias}")
	public ResponseEntity<Map<Integer, String>> getEventList(@PathVariable("dias") int dias) {
		try{
			Map<Integer, String> eventList = service.getPronosticoExtendido(dias);
			return new ResponseEntity<Map<Integer, String>>(eventList, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<Map<Integer, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/clima")
    public ResponseEntity<String> getClimaDia(@RequestParam(required = true, value = "dia") int dia) {
		try{
			 return new ResponseEntity<String>("boludin", HttpStatus.OK); 
		}catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	

}
