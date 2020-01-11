package com.aot.codelabs.processor.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Application class.
 * 
 * @author Sumathi Thirumani
 */
@SpringBootApplication
@Import(OrchestrationProcessorConfiguration.class)
@PropertySource("classpath:processor-core.properties")
public class OrchestrationProcessorSpringBootApplication {
	
    public static void main(String[] args) throws Exception {
    	SpringApplication.run(OrchestrationProcessorSpringBootApplication.class);
    }
    
}