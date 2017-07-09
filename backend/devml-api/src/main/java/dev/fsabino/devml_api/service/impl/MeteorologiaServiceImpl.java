package dev.fsabino.devml_api.service.impl;

import java.awt.geom.Point2D;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import dev.fsabino.devml_api.service.MeteorologiaService;

@Service("meteorologiaServiceImpl")
public class MeteorologiaServiceImpl implements MeteorologiaService{

	final static Logger logger = Logger.getLogger(MeteorologiaServiceImpl.class);
	
	/*Distancia de los planetas respecto al sol*/
	public static final int DistanciaP1 = 500;
	public static final int DistanciaP2 = 2000;
	public static final int DistanciaP3 = 1000;
	
	public static final int VelocidadP1 = -1;
	public static final int VelocidadP2 = -3;
	public static final int VelocidadP3 =  5;
	
	public Point2D vectorP1 = new Point2D.Double();
	public Point2D vectorP2 = new Point2D.Double();
	public Point2D vectorP3 = new Point2D.Double();
	public Point2D vectorSol = new Point2D.Double(0,0);
	
	public String calcular() throws ArithmeticException, Exception{
		
		logger.debug("ingreso a funcion calcular()");
		
		try{
			int diasalineadosconsol = 0;
			int diasalineadossinsol = 0;
			int diassoldentrotriangulo = 0;
			int diassolfueratriangulo= 0;
			double maxperimetro = 0;
			int maxdia = 0;
			
			/*El dueño del sistema es el planeta VULCANO - 5 GRADOS/DIA
			 * 
			 * 360 grados / 5 grados = 72 dias
			 * 
			 * 72 dias * 10 = 720 dias
			 * */
			for (int i = 1; i < 3650; i++) {
				
				logger.debug("Dia: "+ i);
				
				vectorP1 = getPosicionEjeCartesiano(DistanciaP1, getMovimientoDiario(i, VelocidadP1), vectorP1);
				vectorP2 = getPosicionEjeCartesiano(DistanciaP2, getMovimientoDiario(i, VelocidadP2), vectorP2);
				vectorP3 = getPosicionEjeCartesiano(DistanciaP3, getMovimientoDiario(i, VelocidadP3), vectorP3);
				
				logger.debug("P1x: "+ vectorP1.getX() + " P1y: "+ vectorP1.getY() );
				logger.debug("P2x: "+ vectorP2.getX() + " P2y: "+ vectorP2.getY() );
				logger.debug("P3x: "+ vectorP3.getX() + " P3y: "+ vectorP3.getY() );
			
				/*Debemos calculas las pendientes de los puntos para saber si estan alineados*/
				double pendienteferevulca = getPendiente(vectorP1, vectorP3);
				double pendientevulcabeta = getPendiente(vectorP3, vectorP2);
				
				//double pendientefvfijada = Math.round(pendienteferevulca * 10.0) / 10.0;
				//double pendientevbfijada = Math.round(pendientevulcabeta * 10.0) / 10.0;
				
				double pendientefvfijada = fijarNumero(pendienteferevulca,1);
				double pendientevbfijada = fijarNumero(pendientevulcabeta,1);
				
				logger.debug("Pendiente FERENGIS - VULCANO" + pendienteferevulca);
				logger.debug("Pendiente VULCANO - BETASOIDES" + pendientevulcabeta);
				logger.debug("Pendiente Redondeada FERENGIS - VULCANO" + pendientefvfijada);
				logger.debug("Pendiente Redondeada VULCANO - BETASOIDES" + pendientevbfijada);
				
				if (pendientefvfijada == pendientevbfijada){
					
					/*Vemos si el sol esta alineado tambien, con que no este con uno ya esta*/
					double pendientesol = getPendiente(vectorSol, vectorP3);
					double pendientesolfijada = fijarNumero(pendientesol, 1);
					
					if (pendientesolfijada == pendientefvfijada){
						diasalineadosconsol++;	
						
					}else{
						diasalineadossinsol++;
					}
				}else{
					
					/*Significa que forman un triangulo
					 *
					 *Debemos ver si el sol esta dentro del triangulo
					 */
					if (getPuntoEnTriangulo(vectorSol, vectorP1, vectorP2, vectorP3)){
						logger.debug("DENTRO: Punto dentro del triangulo" );
						diassoldentrotriangulo++;
						
						/*Probamos la distancia calculada a mano*/
						//double distanciap1p2_man = getDistancia(vectorP1, vectorP2); 
						//double distanciap2p3_man = getDistancia(vectorP2, vectorP3);
						//double distanciap3p1_man = getDistancia(vectorP3, vectorP1);
						
						/*Probamos el metodo de Point2*/
						double distanciap1p2 = vectorP1.distance(vectorP2); 
						double distanciap2p3 = vectorP2.distance(vectorP3); 
						double distanciap3p1 = vectorP3.distance(vectorP1); 
						
						logger.debug("Distancia Automatica p1p2: "+ distanciap1p2);	
						logger.debug("Distancia Automatica p2p3: "+ distanciap2p3);
						logger.debug("Distancia Automatica p3p1: "+ distanciap3p1);
						
						double sumadistancias = distanciap1p2 + distanciap2p3 + distanciap3p1;
						
						logger.debug("Distancia Total: "+ sumadistancias);
						
						if (sumadistancias > maxperimetro){
							maxperimetro = sumadistancias;
							maxdia = i;
						}
						
					}else{
						logger.debug("FUERA: Punto fuera del triangulo" );
						diassolfueratriangulo++;
					}
				}	
			} 
			
			StringBuilder sb =  new StringBuilder();
			
			sb.append("Cantidad de dias alineados con sol: "+ diasalineadosconsol );sb.append("\r");
			sb.append("Cantidad de dias alineados sin sol: "+ diasalineadossinsol );sb.append("\r");
			sb.append("Cantidad de dias sol dentro del triangulo: "+ diassoldentrotriangulo );sb.append("\r");
			sb.append("Cantidad de dias sol fuera del triangulo: "+ diassolfueratriangulo );sb.append("\r");
			sb.append("Maximo perimetro de tringulo: "+ maxperimetro );sb.append("\r");
			sb.append("Maximo dia: "+ maxdia );

			logger.debug("salida a funcion calcular()");
			
			return sb.toString();
	
		}catch (ArithmeticException e) {
			e.printStackTrace();
			logger.error("ERROR: ArithmeticException");
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR: Exception");
			throw e;
		}

	}

	public int getMovimientoDiario(int dia, int velocidad){
		return dia * velocidad;
	}

	public Point2D getPosicionEjeCartesiano(int distanciaalsol, int movimientodiario, Point2D vector){
	
		logger.debug("Movimiento diario: "+ movimientodiario );
		double radianes = Math.toRadians(movimientodiario);
		double x = distanciaalsol * Math.cos(radianes);
		double y = distanciaalsol * Math.sin(radianes);
		vector.setLocation(x, y);
		return vector;	
	}

	public double getPendiente(Point2D punto1 , Point2D punto2){
		
		double dx = punto2.getX() - punto1.getX();
		double dy = punto2.getY() - punto1.getY();
		
		try{
			return dy/dx;	
		}catch (ArithmeticException e) {
			e.printStackTrace();
			logger.error("ERROR: Se intentó dividir por 0.");
			throw e;
		}
	}
	
	public static double fijarNumero(double numero, int digitos) {
        double resultado;
        resultado = numero * Math.pow(10, digitos);
        resultado = Math.round(resultado);
        resultado = resultado/Math.pow(10, digitos);
        return resultado;
    }
	
	/*(A1.x - A3.x) * (A2.y - A3.y) - (A1.y - A3.y) * (A2.x - A3.x)*/
	public boolean getPuntoEnTriangulo(Point2D p, Point2D planeta1, Point2D planeta2, Point2D planeta3){
		
		double o_p1p2p = getOrientacion(planeta1, planeta2, p);
		double o_p2p3p = getOrientacion(planeta2, planeta3, p);
		double o_p3p1p = getOrientacion(planeta3, planeta1, p);
		
		if (getOrientacion(planeta1, planeta2, planeta3) >= 0){
			logger.debug("Orientacion 3 Planetas Positiva");	
			
			if (o_p1p2p > 0 && o_p2p3p >0 && o_p3p1p > 0){
				return true;
			}else{
				return false;
			}
		}else{
			logger.debug("Orientacion 3 Planetas Negativa");
		
			if (o_p1p2p < 0 && o_p2p3p <0 && o_p3p1p < 0){
				return true;
			}else{
				return false;
			}
		}
	}
	
	public double getOrientacion(Point2D p1, Point2D p2, Point2D p3){
		
		double orientacion = (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p1.getY() - p3.getY()) * (p2.getX() - p3.getX());
		
		logger.debug("Orientacion: "+ orientacion);
		
		return orientacion;
	}
	
	@Deprecated
	public double getDistancia(Point2D punto1, Point2D punto2){
		double dist12x= Math.abs(punto1.getX()-punto2.getX());
		double dist12y= Math.abs(punto1.getY()-punto2.getY());
		
		return Math.sqrt((dist12x*dist12x)+(dist12y*dist12y));
	}

}
