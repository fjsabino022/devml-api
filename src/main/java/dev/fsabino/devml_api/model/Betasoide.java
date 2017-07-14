package dev.fsabino.devml_api.model;

import org.springframework.stereotype.Component;

/**
 * Planeta Betasoide.
 * @author francosabino
 * */
@Component("betasoide")
public class Betasoide extends Planeta {
	public Betasoide() {
		super(Distancia.D_2000.getDistancia(), -Velocidad.V_3.getVelocidad(), "betasoide");
	}
}
