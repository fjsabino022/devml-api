package dev.fsabino.devml_api.service;

import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;

import org.junit.Test;

import dev.fsabino.devml_api.model.Betasoide;
import dev.fsabino.devml_api.model.Ferengi;
import dev.fsabino.devml_api.util.Geometria;

public class CalcularDistanciaTest {

	/**
	 * Validamos que el calculo de la distancia entre dos puntos 
	 * sea exacto de acuerdo a la formula matematica
	 * */
	@Test
	public void probar_calculo_distancia(){
	
		Ferengi ferengi =  new Ferengi();
		Betasoide betasoide =  new Betasoide();
		ferengi.setPunto(new Point2D.Double(10, 60));
		betasoide.setPunto(new Point2D.Double(60, 45));
		
		double distanciaauto = ferengi.getPunto().distance(betasoide.getPunto());
		
		double distanciamanual = Geometria.getInstance().getDistancia(ferengi.getPunto(), betasoide.getPunto());
	
		assertTrue(distanciaauto == distanciamanual);
	}
}
