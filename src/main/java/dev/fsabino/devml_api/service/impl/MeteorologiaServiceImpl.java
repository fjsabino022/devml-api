package dev.fsabino.devml_api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dev.fsabino.devml_api.model.Betasoide;
import dev.fsabino.devml_api.model.Clima;
import dev.fsabino.devml_api.model.Ferengi;
import dev.fsabino.devml_api.model.Sol;
import dev.fsabino.devml_api.model.TipoClima;
import dev.fsabino.devml_api.model.Vulcano;
import dev.fsabino.devml_api.repository.ClimaRepository;
import dev.fsabino.devml_api.service.MeteorologiaService;
import dev.fsabino.devml_api.util.Geometria;

/**
 * Implementacion de metodos de la interfaz MeteorologiaService
 * 
 * @author francosabino
 */
@Service("meteorologiaServiceImpl")
public class MeteorologiaServiceImpl implements MeteorologiaService {

	final static Logger logger = LoggerFactory.getLogger(MeteorologiaServiceImpl.class);

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

	@Autowired
	@Qualifier("clima")
	Clima clima;

	@Autowired
	ClimaRepository climarepository;

	/**
	 * Metodo que permite obtener el string para los puntos 1,2 y 3 del
	 * enunciado.
	 * 
	 * @return String
	 * @author francosabino
	 */
	public String enunciado123() throws Exception {

		logger.debug("ingreso a funcion calcular()");

		try {
			int diasalineadosconsol = 0;
			int diasalineadossinsol = 0;
			int diassoldentrotriangulo = 0;
			int diassolfueratriangulo = 0;
			double maxperimetro = 0;
			int maxdia = 0;

			/*
			 * El dueño del sistema es el planeta VULCANO - 5 GRADOS/DIA
			 * 
			 * 360 grados / 5 grados = 72 dias
			 * 
			 * 72 dias * 10 = 720 dias
			 */
			for (int i = 0; i <= 3650; i++) {

				logger.debug("Dia: " + i);

				ferengi.setPunto(Geometria.getInstance().getPuntoEjeCartesiano(ferengi.getDistancia(),
						i * ferengi.getVelocidad()));
				betasoide.setPunto(Geometria.getInstance().getPuntoEjeCartesiano(betasoide.getDistancia(),
						i * betasoide.getVelocidad()));
				vulcano.setPunto(Geometria.getInstance().getPuntoEjeCartesiano(vulcano.getDistancia(),
						i * vulcano.getVelocidad()));

				logger.debug("P1x: " + ferengi.getPunto().getX() + " P1y: " + ferengi.getPunto().getY());
				logger.debug("P2x: " + betasoide.getPunto().getX() + " P2y: " + betasoide.getPunto().getY());
				logger.debug("P3x: " + vulcano.getPunto().getX() + " P3y: " + vulcano.getPunto().getY());

				/*
				 * Debemos calculas las pendientes de los puntos para saber si
				 * estan alineados
				 */
				double pendienteferevulca = Geometria.getInstance().getPendiente(ferengi.getPunto(),
						vulcano.getPunto());
				double pendientevulcabeta = Geometria.getInstance().getPendiente(vulcano.getPunto(),
						betasoide.getPunto());

				/* Redondeamos las pendientes */
				double pendientefvfijada = Geometria.getInstance().fijarNumero(pendienteferevulca,
						Geometria.cantdecimales);
				double pendientevbfijada = Geometria.getInstance().fijarNumero(pendientevulcabeta,
						Geometria.cantdecimales);

				logger.debug("Pendiente FERENGIS - VULCANO" + pendienteferevulca);
				logger.debug("Pendiente VULCANO - BETASOIDES" + pendientevulcabeta);
				logger.debug("Pendiente Redondeada FERENGIS - VULCANO" + pendientefvfijada);
				logger.debug("Pendiente Redondeada VULCANO - BETASOIDES" + pendientevbfijada);

				if (pendientefvfijada == pendientevbfijada) {

					/*
					 * Vemos si el sol esta alineado tambien, con que no este
					 * con uno ya esta
					 */
					double pendientesol = Geometria.getInstance().getPendiente(sol.getPunto(), vulcano.getPunto());
					double pendientesolfijada = Geometria.getInstance().fijarNumero(pendientesol,
							Geometria.cantdecimales);

					if (pendientesolfijada == pendientefvfijada) {
						diasalineadosconsol++;

					} else {
						diasalineadossinsol++;
					}
				} else {

					/*
					 * Significa que forman un triangulo
					 *
					 * Debemos ver si el sol esta dentro del triangulo
					 */
					if (Geometria.getInstance().isPuntoEnTriangulo(sol.getPunto(), ferengi.getPunto(),
							betasoide.getPunto(), vulcano.getPunto())) {
						logger.debug("DENTRO: Punto dentro del triangulo");
						diassoldentrotriangulo++;

						/* Probamos el metodo de Point2 */
						double distanciap1p2 = ferengi.getPunto().distance(betasoide.getPunto());
						double distanciap2p3 = betasoide.getPunto().distance(vulcano.getPunto());
						double distanciap3p1 = vulcano.getPunto().distance(ferengi.getPunto());

						logger.debug("Distancia Automatica p1p2: " + distanciap1p2);
						logger.debug("Distancia Automatica p2p3: " + distanciap2p3);
						logger.debug("Distancia Automatica p3p1: " + distanciap3p1);

						double sumadistancias = distanciap1p2 + distanciap2p3 + distanciap3p1;

						logger.debug("Distancia Total: " + sumadistancias);

						if (sumadistancias > maxperimetro) {
							maxperimetro = sumadistancias;
							maxdia = i;
						}

					} else {
						logger.debug("FUERA: Punto fuera del triangulo");
						diassolfueratriangulo++;
					}
				}
			}

			StringBuilder sb = new StringBuilder();

			sb.append("1- Cantidad de dias SEQUIA (planetas alineados con sol): " + diasalineadosconsol);
			sb.append("\r");
			sb.append("2- Cantidad de dias LLUVIA (sol dentro del triangulo): " + diassoldentrotriangulo);
			sb.append("\r");
			sb.append("2- Maximo dia de LLUVIA: " + maxdia + " perímetro: " + maxperimetro);
			sb.append("\r");
			sb.append("3- Cantidad de dias CONDICIONES OPTIMAS (planetas alineados sin sol): " + diasalineadossinsol);
			sb.append("\r");
			sb.append("Cantidad de dias CLIMA INDEFINIDO (sol fuera del triangulo): " + diassolfueratriangulo);
			sb.append("\r");

			logger.debug("salida a funcion calcular()");

			return sb.toString();

		} catch (ArithmeticException e) {
			e.printStackTrace();
			logger.error("ERROR: ArithmeticException");
			throw e;
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.error("ERROR: NullPointerException");
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR: Exception");
			throw e;
		}
	}

	/**
	 * Metodo que permite obtener el tipo de clima de un dia determinado
	 * 
	 * @param int
	 * @return TipoClima tipoclima
	 * @author francosabino
	 */
	private TipoClima getTipoClimaByDia(int dia) throws Exception {

		logger.debug("ingreso a funcion getTipoClimaByDia()");

		try {

			if (dia >= 0) {
				ferengi.setPunto(Geometria.getInstance().getPuntoEjeCartesiano(ferengi.getDistancia(),
						dia * ferengi.getVelocidad()));
				betasoide.setPunto(Geometria.getInstance().getPuntoEjeCartesiano(betasoide.getDistancia(),
						dia * betasoide.getVelocidad()));
				vulcano.setPunto(Geometria.getInstance().getPuntoEjeCartesiano(vulcano.getDistancia(),
						dia * vulcano.getVelocidad()));

				/*
				 * Debemos calculas las pendientes de los puntos para saber si
				 * estan alineados
				 */
				double pendienteferevulca = Geometria.getInstance().getPendiente(ferengi.getPunto(),
						vulcano.getPunto());
				double pendientevulcabeta = Geometria.getInstance().getPendiente(vulcano.getPunto(),
						betasoide.getPunto());

				/* Redondeamos las pendientes */
				double pendientefvfijada = Geometria.getInstance().fijarNumero(pendienteferevulca,
						Geometria.cantdecimales);
				double pendientevbfijada = Geometria.getInstance().fijarNumero(pendientevulcabeta,
						Geometria.cantdecimales);

				if (pendientefvfijada == pendientevbfijada) {

					double pendientesol = Geometria.getInstance().getPendiente(sol.getPunto(), vulcano.getPunto());
					double pendientesolfijada = Geometria.getInstance().fijarNumero(pendientesol,
							Geometria.cantdecimales);

					if (pendientesolfijada == pendientefvfijada) {
						return TipoClima.SEQUIA;
					} else {
						return TipoClima.OPTIMO;
					}
				} else {

					if (Geometria.getInstance().isPuntoEnTriangulo(sol.getPunto(), ferengi.getPunto(),
							betasoide.getPunto(), vulcano.getPunto())) {
						return TipoClima.LLUVIA;
					} else {
						return TipoClima.INDEFINIDO;
					}
				}
			} else {

				logger.debug("salida de funcion getTipoClimaByDia() NULL");
				return null;
			}
		} catch (ArithmeticException e) {
			e.printStackTrace();
			logger.error("ERROR: ArithmeticException");
			throw e;
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.error("ERROR: NullPointerException");
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR: Exception");
			throw e;
		}

	}

	/**
	 * Metodo que permite obtener el tipo de clima de un dia determinado
	 * 
	 * @param Integer
	 * @author francosabino
	 */
	public void calcularPronosticoExtendido(Integer dia) throws Exception {

		logger.debug("ingreso a funcion calcularPronosticoExtendido()");
		if (dia != null && dia >= 0) {
			for (int i = 0; i <= 720; i++) {
				getClimaByDia(i);
			}
		}
		logger.debug("salida de funcion calcularPronosticoExtendido()");
	}

	/**
	 * Metodo que permite obtener el clima de un determinado dia
	 * 
	 * @param Integer
	 * @return Clima clima
	 * @author francosabino
	 */
	@Override
	public Clima getClimaByDia(Integer dia) throws Exception {

		logger.debug("ingreso a funcion getClimaByDia()");
		if (dia != null && dia >= 0) {
			clima = climarepository.findClimaByDia(dia);

			if (clima == null) {
				clima = climarepository.saveClima(new Clima(dia, getTipoClimaByDia(dia)));
			}

			logger.debug("salida de funcion getClimaByDia()");
			return clima;
		} else {

			logger.debug("salida de funcion getClimaByDia() NULL");
			return null;
		}
	}
}
