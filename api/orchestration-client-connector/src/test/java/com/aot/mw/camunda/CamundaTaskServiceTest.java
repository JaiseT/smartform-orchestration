package com.aot.mw.camunda;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aot.codelabs.accelerator.context.OrchestrationClientCategory;
import com.aot.codelabs.orchestration.support.camunda.CamundaTask;
import com.aot.mw.IOrchestrationClient;
import com.aot.mw.IOrchestrationClientFactory;
import com.aot.mw.config.OrchestrationSpringBootApplication;

/**
 * Test class for Camunda service.
 * 
 * @author Sumathi Thirumani
 *
 */
@SpringBootTest(classes={OrchestrationSpringBootApplication.class})
public class CamundaTaskServiceTest {
	
	@Autowired
	private IOrchestrationClientFactory orchestrationClientFactory;
	
    @Test
    void testServiceName() {
        assertEquals("CAMUNDA", getInstance().getServiceName().name());
    }
    
    @Test
    void testgetTask() {
    	CamundaTask response = (CamundaTask) getInstance().getTask("75e09e9e-0006-11ea-b91a-9829a64213ec");
    	System.out.println(response.getCreated());
        assertEquals("approveInvoice", response.getTaskDefinitionKey());
    }
    
    @SuppressWarnings("unchecked")
	@Test
    void testgetTaskByUserGroup() {
    	List<String> groups = new ArrayList<String>();
    	groups.add("accounting");
    	List<CamundaTask> response = getInstance().getTaskForUserGroup("demo", groups);
        assertEquals("demo", response.get(0).getAssignee());
    }
	
	@SuppressWarnings("rawtypes")
	private IOrchestrationClient getInstance(){
    	return orchestrationClientFactory.getOrchestrationClient(OrchestrationClientCategory.CAMUNDA.getValue());
    }
	
}