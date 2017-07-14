package dev.fsabino.devml_api.model;

import org.springframework.stereotype.Component;

/**
 * Planeta Vulcano.
 * @author francosabino
 * */
@Component("vulcano")
public class Vulcano extends Planeta {
	public Vulcano() {
		super(Distancia.D_1000.getDistancia(), Velocidad.V_5.getVelocidad(), "vulcano");
	}
}
