package dev.fsabino.devml_api.model;

/**
 * Enumeracion que guarda las clasificaciones de los tipos de clima.
 *
 * @author francosabino
 * */
public enum Clima {
	
	LUVIA("luvia"), 
	SEQUIA("sequia"),
	INDEFINIDO("indefinido"), 
	OPTIMO("optimo");
	
	private String clima;

	private Clima (String clima){
		this.clima = clima;
	}

	public String getClima() {
		return clima;
	}
}
