package dev.fsabino.devml_api.util;

import java.awt.geom.Point2D;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que se encarga de realizar los calculos geometros
 *
 * @author francosabino
 */
public class Geometria {

	final static Logger logger = LoggerFactory.getLogger(Geometria.class);

	private static Geometria geometria = null;
	public static final int cantdecimales = 1;

	private Geometria() {

	}

	public static Geometria getInstance() {

		if (geometria == null) {
			Geometria.geometria = new Geometria();
		}
		return geometria;
	}

	/**
	 * Metodo que se encarga de devolver un punto del eje cartesiano a partir de
	 * coordenadas polares. x' = r * cos(α) y' = r * sen(α)
	 * 
	 * @param int
	 * @param int
	 * @return Point2D punto
	 * @throws Exception
	 * @author francosabino
	 */
	public Point2D getPuntoEjeCartesiano(int distancia, int movimiento) throws Exception {

		logger.debug("ingreso a funcion getPuntoEjeCartesiano()");

		double radianes = Math.toRadians(movimiento);
		double x = distancia * Math.cos(radianes);
		double y = distancia * Math.sin(radianes);

		logger.debug("ingreso a funcion getPuntoEjeCartesiano()");
		return new Point2D.Double(x, y);
	}

	/**
	 * Metodo que se encarga de calcular la orientacion de un triangulo (A1.x -
	 * A3.x) * (A2.y - A3.y) - (A1.y - A3.y) * (A2.x - A3.x)
	 * 
	 * @param Point2D
	 * @param Point2D
	 * @param Point2D
	 * @return double orientacion
	 * @throws NullPointerException
	 * @author francosabino
	 */
	public double getOrientacion(Point2D p1, Point2D p2, Point2D p3) throws NullPointerException {
		logger.debug("ingreso a funcion getOrientacion()");

		double orientacion = (p1.getX() - p3.getX()) * (p2.getY() - p3.getY())
				- (p1.getY() - p3.getY()) * (p2.getX() - p3.getX());

		logger.debug("salida a funcion getOrientacion()");
		return orientacion;
	}

	/**
	 * Metodo que se determina si un punto esta dentro de un triangulo
	 * 
	 * @param Point2D
	 * @param Point2D
	 * @param Point2D
	 * @param Point2D
	 * @return boolean
	 * @throws Exception
	 * @author francosabino
	 */
	public boolean isPuntoEnTriangulo(Point2D p, Point2D p1, Point2D p2, Point2D p3) throws Exception {

		logger.debug("ingreso a funcion isPuntoEnTriangulo()");
		
		double o_p1p2p = getOrientacion(p1, p2, p);
		double o_p2p3p = getOrientacion(p2, p3, p);
		double o_p3p1p = getOrientacion(p3, p1, p);

		if (getOrientacion(p1, p2, p3) >= 0) {

			if (o_p1p2p > 0 && o_p2p3p > 0 && o_p3p1p > 0) {
				
				logger.debug("salida a funcion isPuntoEnTriangulo() true");
				return true;
			} else {
				
				logger.debug("salida a funcion isPuntoEnTriangulo() false");
				return false;
			}
		} else {

			if (o_p1p2p < 0 && o_p2p3p < 0 && o_p3p1p < 0) {
				
				logger.debug("salida a funcion isPuntoEnTriangulo() true");
				return true;
			} else {
				
				logger.debug("salida a funcion isPuntoEnTriangulo() false");
				return false;
			}
		}
	}

	/**
	 * Metodo que se determina la pendiente entre dos puntos
	 * 
	 * @param Point2D
	 *            p1
	 * @param Point2D
	 *            p2
	 * @return double valor pendiente
	 * @author francosabino
	 */
	public double getPendiente(Point2D p1, Point2D p2) {

		logger.debug("ingreso a funcion getPendiente()");

		try {
			double dx = p2.getX() - p1.getX();
			double dy = p2.getY() - p1.getY();

			logger.debug("salida a funcion getPendiente()");
			return dy / dx;

		} catch (NullPointerException e) {
			logger.error("ERROR: NullPointerException");
			e.printStackTrace();
			throw e;
		} catch (ArithmeticException e) {
			logger.error("ERROR: ArithmeticException");
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Metodo que permite cortar los digitos de un numero.
	 * 
	 * @param double
	 * @param int
	 * @return double numero
	 * @throws Exception
	 * @author francosabino
	 */
	public double fijarNumero(double numero, int digitos) throws Exception {
		logger.debug("ingreso a funcion fijarNumero()");

		double resultado;
		resultado = numero * Math.pow(10, digitos);
		resultado = Math.round(resultado);
		resultado = resultado / Math.pow(10, digitos);

		logger.debug("salida a funcion fijarNumero()");
		return resultado;
	}

	/**
	 * Metodo que permite calcular la distancia entre dos puntos.
	 * 
	 * @param Point2D
	 * @param Point2D
	 * @return double
	 * @author francosabino
	 */
	public double getDistancia(Point2D punto1, Point2D punto2) {
		logger.debug("ingreso a funcion getDistancia()");

		double dist12x = Math.abs(punto1.getX() - punto2.getX());
		double dist12y = Math.abs(punto1.getY() - punto2.getY());

		logger.debug("salida a funcion getDistancia()");

		return Math.sqrt((dist12x * dist12x) + (dist12y * dist12y));
	}
}
