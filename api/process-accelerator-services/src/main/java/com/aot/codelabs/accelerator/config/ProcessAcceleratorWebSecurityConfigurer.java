package com.aot.codelabs.accelerator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This class handles all web security behaviors.
 * 
 * @author Sumathi Thirumani
 */
@Configuration
@EnableWebSecurity
public class ProcessAcceleratorWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	     http.csrf().disable()
	     .authorizeRequests()
	     .antMatchers("/process-accelerator/*")
	     .authenticated();
	}
}