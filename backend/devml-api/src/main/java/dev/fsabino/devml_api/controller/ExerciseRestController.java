package dev.fsabino.devml_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ExerciseRestController {

	 @GetMapping("/ping")
     public ResponseEntity<String> ping() {
             return new ResponseEntity<String>("pong...", HttpStatus.OK);
     }
	 
	 
}
