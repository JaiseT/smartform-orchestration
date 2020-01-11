package com.aot.codelabs.processor.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aot.codelabs.processor.config.OrchestrationProcessorSpringBootApplication;
import com.aot.codelabs.processor.model.WorkGroup;
import com.aot.codelabs.processor.service.impl.SmartWebFormAdminService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes={OrchestrationProcessorSpringBootApplication.class})
public class SmartWebFormAdminServiceTest {
	
	@Autowired
	private SmartWebFormAdminService smartWebFormAdminService;
	
	@Test
	void testgetAllGroups() {
		List<WorkGroup> groups = smartWebFormAdminService.getAllGroups();
		System.out.println(groups.size());
		assertNotNull(groups);
		
	}
	
}