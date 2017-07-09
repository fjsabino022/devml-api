package dev.fsabino.devml_api.service.impl;

import java.awt.geom.Point2D;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import dev.fsabino.devml_api.model.Distancia;
import dev.fsabino.devml_api.model.Velocidad;
import dev.fsabino.devml_api.service.MeteorologiaService;
import dev.fsabino.devml_api.util.Geometria;

@Service("meteorologiaServiceImpl")
public class MeteorologiaServiceImpl implements MeteorologiaService{

	final static Logger logger = Logger.getLogger(MeteorologiaServiceImpl.class);
	
	/*Distancia de los planetas respecto al sol*/
	public Point2D vectorP1 = new Point2D.Double();
	public Point2D vectorP2 = new Point2D.Double();
	public Point2D vectorP3 = new Point2D.Double();
	public Point2D vectorSol = new Point2D.Double(0,0);
	
	public String calcular() throws Exception{
		
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
				
				vectorP1 = Geometria.getInstance().getPuntoEjeCartesiano(Distancia.D_500.getDistancia(), getMovimientoDiario(i, -Velocidad.V_1.getVelocidad()));
				vectorP2 = Geometria.getInstance().getPuntoEjeCartesiano(Distancia.D_2000.getDistancia(), getMovimientoDiario(i, -Velocidad.V_3.getVelocidad()));
				vectorP3 = Geometria.getInstance().getPuntoEjeCartesiano(Distancia.D_1000.getDistancia(), getMovimientoDiario(i, Velocidad.V_5.getVelocidad()));
				
				logger.debug("P1x: "+ vectorP1.getX() + " P1y: "+ vectorP1.getY() );
				logger.debug("P2x: "+ vectorP2.getX() + " P2y: "+ vectorP2.getY() );
				logger.debug("P3x: "+ vectorP3.getX() + " P3y: "+ vectorP3.getY() );
			
				/*Debemos calculas las pendientes de los puntos para saber si estan alineados*/
				double pendienteferevulca = Geometria.getInstance().getPendiente(vectorP1, vectorP3);
				double pendientevulcabeta = Geometria.getInstance().getPendiente(vectorP3, vectorP2);
				
				//double pendientefvfijada = Math.round(pendienteferevulca * 10.0) / 10.0;
				//double pendientevbfijada = Math.round(pendientevulcabeta * 10.0) / 10.0;
				
				double pendientefvfijada = Geometria.getInstance().fijarNumero(pendienteferevulca,1);
				double pendientevbfijada = Geometria.getInstance().fijarNumero(pendientevulcabeta,1);
				
				logger.debug("Pendiente FERENGIS - VULCANO" + pendienteferevulca);
				logger.debug("Pendiente VULCANO - BETASOIDES" + pendientevulcabeta);
				logger.debug("Pendiente Redondeada FERENGIS - VULCANO" + pendientefvfijada);
				logger.debug("Pendiente Redondeada VULCANO - BETASOIDES" + pendientevbfijada);
				
				if (pendientefvfijada == pendientevbfijada){
					
					/*Vemos si el sol esta alineado tambien, con que no este con uno ya esta*/
					double pendientesol = Geometria.getInstance().getPendiente(vectorSol, vectorP3);
					double pendientesolfijada = Geometria.getInstance().fijarNumero(pendientesol, 1);
					
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
					if (Geometria.getInstance().isPuntoEnTriangulo(vectorSol, vectorP1, vectorP2, vectorP3)){
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
		}catch (NullPointerException e) {
			e.printStackTrace();
			logger.error("ERROR: NullPointerException");
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

}
