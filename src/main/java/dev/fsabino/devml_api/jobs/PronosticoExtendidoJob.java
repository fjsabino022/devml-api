package dev.fsabino.devml_api.jobs;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PronosticoExtendidoJob implements ApplicationListener<ApplicationReadyEvent>{
	
	  /**
	   * This event is executed as late as conceivably possible to indicate that 
	   * the application is ready to service requests.
	   */
	  @Override
	  public void onApplicationEvent(final ApplicationReadyEvent event) {
	 
		  //Set<String> keys = JedisRepository.getInstance().keys("*");
		  //for (String key : keys) {
		  //	  JedisRepository.getInstance().del(key);
		  //} 		  
		  //JedisRepository.getInstance().set("agus", "agus");
	    return;
	  }
}
