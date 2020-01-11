package com.aot.codelabs.processor.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aot.codelabs.processor.config.OrchestrationProcessorSpringBootApplication;
import com.aot.codelabs.processor.model.SmartWebFormOrchestration;
import com.aot.codelabs.processor.model.SmartWebFormOrchestrationPK;
import com.aot.codelabs.processor.service.impl.SmartWebFormOrchestrationService;

@SpringBootTest(classes={OrchestrationProcessorSpringBootApplication.class})
public class SmartWebFormOrchestrationServiceTest {
	
	@Autowired
	private SmartWebFormOrchestrationService smartWebFormOrchestrationService;
	
	@Test
	void testCreate() {
		 SmartWebFormOrchestration orchObj = new SmartWebFormOrchestration();
			SmartWebFormOrchestrationPK orchObjPK = new SmartWebFormOrchestrationPK("SYSTEM",1,1);
			orchObj.setId(orchObjPK);
			orchObj.setDataId("test-id");
			SmartWebFormOrchestration resp = smartWebFormOrchestrationService.saveOrchestrationInstance(orchObj);
		assertNotNull(resp);
		
	}
}