package dev.fsabino.devml_api.model;

import java.awt.geom.Point2D;

import org.springframework.stereotype.Component;

@Component("sol")
public class Sol extends Planeta {
	public Sol(){
		super(Distancia.D_0.getDistancia() , Velocidad.V_0.getVelocidad() , "sol", new Point2D.Double(0,0));
	}
}
