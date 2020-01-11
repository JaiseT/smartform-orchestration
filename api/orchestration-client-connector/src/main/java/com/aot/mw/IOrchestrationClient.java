package com.aot.mw;

import java.util.List;

import com.aot.codelabs.accelerator.context.OrchestrationClientCategory;
import com.aot.codelabs.orchestration.ProcessDefinition;
import com.aot.codelabs.orchestration.Task;

/**
 * Interface for holding the common behavior of all orchestration client. 
 * Also, serves as a placeholder to hold default functional behaviors.
 * 
 * @author Sumathi Thirumani
 *
 * @param <T>
 */
public interface IOrchestrationClient<T extends Task> extends ITaskService<T> {
	OrchestrationClientCategory getServiceName();
	List<? extends ProcessDefinition> getAllProcesses();
}