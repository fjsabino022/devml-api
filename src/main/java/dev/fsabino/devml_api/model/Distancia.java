package dev.fsabino.devml_api.model;

/**
 * Enum Distancia
 * @author francosabino
 * */
public enum Distancia {
	
	D_1000(1000), 
	D_500(500),
	D_0(0),
	D_2000(2000); 
	
	private int distancia;

	private Distancia (int distancia){
		this.distancia = distancia;
	}

	public int getDistancia() {
		return distancia;
	}
}
