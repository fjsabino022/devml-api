package dev.fsabino.devml_api.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import dev.fsabino.devml_api.service.MeteorologiaService;

/**
 * Clase que se ejecuta al inicial el servidor
 * @author francosabino
 *
 **/
@Component
public class PronosticoExtendidoJob implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	@Qualifier("meteorologiaServiceImpl")
	private MeteorologiaService service;

	/**
	 * This event is executed as late as conceivably possible to indicate that
	 * the application is ready to service requests.
	 */
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		try {
			service.calcularPronosticoExtendido(720);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return;
	}
}
