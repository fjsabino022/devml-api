package dev.fsabino.devml_api.model;

import org.springframework.stereotype.Component;

/**
 * Clase model del clima
 *
 * @author francosabino
 * */
@Component("clima")
public class Clima {

	private Integer dia;
	private TipoClima clima;
	
	public Clima(){
		
	}

	public Clima(Integer id, TipoClima clima) {
		super();
		this.dia = id;
		this.clima = clima;
	}

	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer dia) {
		this.dia = dia;
	}

	public TipoClima getClima() {
		return clima;
	}

	public void setClima(TipoClima clima) {
		this.clima = clima;
	}	
}
