package dev.fsabino.devml_api.model;

/**
 * Enumeracion que guarda las clasificaciones de los tipos de clima.
 *
 * @author francosabino
 */
public enum TipoClima {

	LLUVIA("lluvia"), SEQUIA("sequia"), INDEFINIDO("indefinido"), OPTIMO("optimo");

	private String tipoclima;

	private TipoClima(String tipoclima) {
		this.tipoclima = tipoclima;
	}

	public String getTipoclima() {
		return tipoclima;
	}

	public static TipoClima devolveTipoClima(String tipoclima) {

		switch (tipoclima) {
		case "lluvia":
			return TipoClima.LLUVIA;
		case "sequia":
			return TipoClima.SEQUIA;
		case "indefinido":
			return TipoClima.INDEFINIDO;
		case "optimo":
			return TipoClima.OPTIMO;
		default:
			return null;
		}
	}
}
