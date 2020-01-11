package com.aot.mw.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.aot.mw.IOrchestrationClientFactory;

/**
 * Configuration class.
 * 
 * @author Sumathi Thirumani
 */
@Configuration
@ComponentScan(basePackages="com.aot.mw")
public class OrchestrationClientConfiguration {
	
	 @Bean
	    public RestTemplate getRestTemplate(){
	        RestTemplate serviceTemplate = new RestTemplate();
	        return serviceTemplate;
	    }
	    
	    @SuppressWarnings("rawtypes")
		@Bean
	    public FactoryBean serviceLocatorFactoryBean() {
	    	ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
	    	factoryBean.setServiceLocatorInterface(IOrchestrationClientFactory.class);
	    	return factoryBean;
	    }
}