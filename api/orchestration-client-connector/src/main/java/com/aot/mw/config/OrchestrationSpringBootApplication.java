package com.aot.mw.config;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Application class.
 * 
 * @author Sumathi Thirumani
 */
@Import(OrchestrationClientConfiguration.class)
@PropertySource("classpath:orchestration-client.properties")
public class OrchestrationSpringBootApplication {
	
    public static void main(String[] args) throws Exception {
    	SpringApplication.run(OrchestrationSpringBootApplication.class);
    }
    
}