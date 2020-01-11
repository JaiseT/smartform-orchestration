package com.aot.codelabs.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class.
 * 
 * @author Sumathi Thirumani
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ComponentScan({"com.aot.codelabs","com.aot.mw"})
public class WebPortalSpringBootApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
        SpringApplication.run(WebPortalSpringBootApplication.class, args);
    }
	
	@Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	      return application.sources(WebPortalSpringBootApplication.class);
	  }
	
	@Bean
    public RestTemplate getRestTemplate(){
        RestTemplate serviceTemplate = new RestTemplate();
        return serviceTemplate;
    }
	
}