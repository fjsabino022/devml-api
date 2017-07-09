package dev.fsabino.devml_api.model;

/**
 * Enum Velocidad
 * @author francosabino
 * */
public enum Velocidad {
	
	V_1(1), 
	V_3(3),
	V_5(5); 
	
	private int velocidad;

	private Velocidad (int velocidad){
		this.velocidad = velocidad;
	}

	public int getVelocidad() {
		return velocidad;
	}
}
