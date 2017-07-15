package dev.fsabino.devml_api.util;

import java.awt.geom.Point2D;

/**
 * Clase que se encarga de realizar los calculos geometros
 *
 * @author francosabino
 * */
public class Geometria {

	private static Geometria geometria = null;
	public static final int cantdecimales = 1;
	
	private Geometria(){
		
	}
	
	public static Geometria getInstance(){
		
		if (geometria == null){
			Geometria.geometria =  new Geometria();
		}
		return geometria;
	}
	
	/**
	 * Metodo que se encarga de devolver un punto del eje cartesiano a partir de coordenadas polares.
	 *	x' = r * cos(α)
	 *	y' = r * sen(α)
	 * @param int distancia
	 * @param int movimiento
	 * @return Point2D punto 
	 * @throws Exception
	 * @author francosabino
	 * */
	public Point2D getPuntoEjeCartesiano(int distancia, int movimiento) throws Exception{
	
		double radianes = Math.toRadians(movimiento);
		double x = distancia * Math.cos(radianes);
		double y = distancia * Math.sin(radianes);
		return new Point2D.Double(x, y);
	}
	
	/**
	 * Metodo que se encarga de calcular la orientacion de un triangulo
	 *	(A1.x - A3.x) * (A2.y - A3.y) - (A1.y - A3.y) * (A2.x - A3.x)
	 * @param Point2D p1
	 * @param Point2D p2
	 * @param Point2D p3
	 * @return double orientacion 
	 * @throws NullPointerException
	 * @author francosabino
	 * */
	public double getOrientacion(Point2D p1, Point2D p2, Point2D p3) throws NullPointerException{
		
		double orientacion = (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p1.getY() - p3.getY()) * (p2.getX() - p3.getX());
		return orientacion;
	}
	
	/**
	 * Metodo que se determina si un punto esta dentro de un triangulo
	 * @param Point2D p
	 * @param Point2D p1
	 * @param Point2D p2
	 * @param Point2D p3
	 * @return boolean 
	 * @throws Exception
	 * @author francosabino
	 * */
	public boolean isPuntoEnTriangulo(Point2D p, Point2D p1, Point2D p2, Point2D p3) throws Exception{
		
		double o_p1p2p = getOrientacion(p1, p2, p);
		double o_p2p3p = getOrientacion(p2, p3, p);
		double o_p3p1p = getOrientacion(p3, p1, p);
		
		if (getOrientacion(p1, p2, p3) >= 0){
			
			if (o_p1p2p > 0 && o_p2p3p >0 && o_p3p1p > 0){
				return true;
			}else{
				return false;
			}
		}else{
			
			if (o_p1p2p < 0 && o_p2p3p <0 && o_p3p1p < 0){
				return true;
			}else{
				return false;
			}
		}
	}
	
	/**
	 * Metodo que se determina la pendiente entre dos puntos
	 * @param Point2D p1
	 * @param Point2D p2
	 * @return double valor pendiente
	 * @author francosabino
	 * */
	public double getPendiente(Point2D p1 , Point2D p2){
		
		try{
			double dx = p2.getX() - p1.getX();
			double dy = p2.getY() - p1.getY();
			
			return dy/dx;	
		
		}catch (NullPointerException e) {
			e.printStackTrace();
			throw e;
		}catch (ArithmeticException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Metodo que permite cortar los digitos de un numero.
	 * @param double numero
	 * @param int digitos
	 * @return double numero
	 * @throws Exception
	 * @author francosabino
	 * */
	public double fijarNumero(double numero, int digitos) throws Exception{
		
		double resultado;
        resultado = numero * Math.pow(10, digitos);
        resultado = Math.round(resultado);
        resultado = resultado/Math.pow(10, digitos);
        return resultado;
	}
	
	public double getDistancia(Point2D punto1, Point2D punto2){
		double dist12x= Math.abs(punto1.getX()-punto2.getX());
		double dist12y= Math.abs(punto1.getY()-punto2.getY());
		
		return Math.sqrt((dist12x*dist12x)+(dist12y*dist12y));
	}
}


