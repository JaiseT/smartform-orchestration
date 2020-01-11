package com.aot.codelabs.processor.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration Class.
 * 
 * @author Sumathi Thirumani
 */
@Configuration
@ComponentScan(basePackages="com.aot.codelabs.processor")
@EntityScan("com.aot.codelabs.processor.model")
@EnableJpaRepositories("com.aot.codelabs.processor.repository")
public class OrchestrationProcessorConfiguration {
	
}