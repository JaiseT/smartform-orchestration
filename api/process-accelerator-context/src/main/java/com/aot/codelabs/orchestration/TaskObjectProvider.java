package com.aot.codelabs.orchestration;

import org.springframework.stereotype.Component;

import com.aot.codelabs.accelerator.context.OrchestrationClientCategory;
import com.aot.codelabs.orchestration.support.camunda.CamundaTask;

@Component("taskObjectProvider")
public class TaskObjectProvider {
	
	public Task getInstance(OrchestrationClientCategory type) {
		Task ins = null;
		switch(type) {
		case CAMUNDA :
			ins = new CamundaTask();
			return ins;
		default :
			throw new RuntimeException("Unsupported category");
		}
	}
}