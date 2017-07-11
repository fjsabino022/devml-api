package dev.fsabino.devml_api.model;

import org.springframework.stereotype.Component;

@Component("ferengi")
public class Ferengi extends Planeta {
	public Ferengi() {
		super(Distancia.D_500.getDistancia(), -Velocidad.V_1.getVelocidad(), "ferengi");
	}
}
