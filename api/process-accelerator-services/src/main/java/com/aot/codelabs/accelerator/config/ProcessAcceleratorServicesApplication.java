package com.aot.codelabs.accelerator.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.aot.codelabs.processor.config.OrchestrationProcessorConfiguration;

/**
 * Application class.
 * 
 * @author Sumathi Thirumani
 */
@SpringBootApplication
@ComponentScan({"com.aot.codelabs.accelerator","com.aot.codelabs.processor","com.aot.mw"})
@Import(OrchestrationProcessorConfiguration.class)
public class ProcessAcceleratorServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessAcceleratorServicesApplication.class, args);
	}

}
