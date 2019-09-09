package br.com.pag;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ContextStartedListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(ContextStartedListener.class);
    
    @Autowired
    private Environment environment;
	
    @Override
    public void onApplicationEvent (ContextRefreshedEvent event) {

    	log.info("---------------------------------------------------");
		log.info("driver:   {}", environment.getProperty("spring.datasource.driverClassName"));
		log.info("url:      {}", environment.getProperty("spring.datasource.url"));
		log.info("username: {}", environment.getProperty("spring.datasource.username"));
		log.info("password: {}", environment.getProperty("spring.datasource.password"));
		log.info("profile:  {}", Arrays.toString(environment.getActiveProfiles()));
		log.info("=> Running ...");
		log.info("---------------------------------------------------");
    	
    }
}