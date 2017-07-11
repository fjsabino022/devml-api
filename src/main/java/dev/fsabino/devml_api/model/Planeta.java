package dev.fsabino.devml_api.model;

import java.awt.geom.Point2D;

/**
 * Bean Planeta
 * @author francosabino
 * */
public class Planeta {

	private int distancia;
	private int velocidad;
	private String nombre;
	private Point2D punto;
	
	public Planeta(int distancia, int velocidad, String nombre, Point2D punto) {
		super();
		this.distancia = distancia;
		this.velocidad = velocidad;
		this.nombre = nombre;
		this.punto = punto;
	}
	
	public Planeta(int distancia, int velocidad, String nombre) {
		super();
		this.distancia = distancia;
		this.velocidad = velocidad;
		this.nombre = nombre;
	}

	public Planeta() {
		super();
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Point2D getPunto() {
		return punto;
	}

	public void setPunto(Point2D punto) {
		this.punto = punto;
	}	
}
