package dev.fsabino.devml_api.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dev.fsabino.devml_api.model.Betasoide;
import dev.fsabino.devml_api.model.Ferengi;
import dev.fsabino.devml_api.model.Sol;
import dev.fsabino.devml_api.model.Vulcano;
import dev.fsabino.devml_api.service.MeteorologiaService;
import dev.fsabino.devml_api.util.Geometria;

@Service("meteorologiaServiceImpl")
public class MeteorologiaServiceImpl implements MeteorologiaService{

	final static Logger logger = Logger.getLogger(MeteorologiaServiceImpl.class);
	
	@Autowired
	@Qualifier("ferengi")
	Ferengi ferengi;
	
	@Autowired
	@Qualifier("betasoide")
	Betasoide betasoide;

	@Autowired
	@Qualifier("vulcano")
	Vulcano vulcano;
	
	@Autowired
	@Qualifier("sol")
	Sol sol;
	
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
			
				ferengi.setPunto(Geometria.getInstance().getPuntoEjeCartesiano(ferengi.getDistancia(), i *ferengi.getVelocidad()));
				betasoide.setPunto(Geometria.getInstance().getPuntoEjeCartesiano(betasoide.getDistancia(), i * betasoide.getVelocidad()));
				vulcano.setPunto(Geometria.getInstance().getPuntoEjeCartesiano(vulcano.getDistancia(), i * vulcano.getVelocidad()));
				
				logger.debug("P1x: "+ ferengi.getPunto().getX() + " P1y: "+ ferengi.getPunto().getY() );
				logger.debug("P2x: "+ betasoide.getPunto().getX() + " P2y: "+ betasoide.getPunto().getY() );
				logger.debug("P3x: "+ vulcano.getPunto().getX() + " P3y: "+ vulcano.getPunto().getY() );
			
				/*Debemos calculas las pendientes de los puntos para saber si estan alineados*/
				double pendienteferevulca = Geometria.getInstance().getPendiente(ferengi.getPunto(), vulcano.getPunto());
				double pendientevulcabeta = Geometria.getInstance().getPendiente(vulcano.getPunto(), betasoide.getPunto());

				
				double pendientefvfijada = Geometria.getInstance().fijarNumero(pendienteferevulca,1);
				double pendientevbfijada = Geometria.getInstance().fijarNumero(pendientevulcabeta,1);
				
				logger.debug("Pendiente FERENGIS - VULCANO" + pendienteferevulca);
				logger.debug("Pendiente VULCANO - BETASOIDES" + pendientevulcabeta);
				logger.debug("Pendiente Redondeada FERENGIS - VULCANO" + pendientefvfijada);
				logger.debug("Pendiente Redondeada VULCANO - BETASOIDES" + pendientevbfijada);
				
				if (pendientefvfijada == pendientevbfijada){
					
					/*Vemos si el sol esta alineado tambien, con que no este con uno ya esta*/
					double pendientesol = Geometria.getInstance().getPendiente(sol.getPunto(), vulcano.getPunto());
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
					if (Geometria.getInstance().isPuntoEnTriangulo(sol.getPunto(), ferengi.getPunto(), betasoide.getPunto(), vulcano.getPunto())){
						logger.debug("DENTRO: Punto dentro del triangulo" );
						diassoldentrotriangulo++;
						
						/*Probamos el metodo de Point2*/
						double distanciap1p2 = ferengi.getPunto().distance(betasoide.getPunto()); 
						double distanciap2p3 = betasoide.getPunto().distance(vulcano.getPunto()); 
						double distanciap3p1 = vulcano.getPunto().distance(ferengi.getPunto()); 
						
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
}
