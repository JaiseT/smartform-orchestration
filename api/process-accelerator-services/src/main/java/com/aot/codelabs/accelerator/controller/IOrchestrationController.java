package com.aot.codelabs.accelerator.controller;


import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aot.codelabs.orchestration.ProcessDefinition;


/**
 * Interface for orchestration operations.
 *
 * @author Sumathi Thirumani
 */
@RequestMapping("/orchestration")
public interface IOrchestrationController {
	
	@PostMapping("/{clientId}/process/{pid}/create")
	Object createProcessInstance(@PathVariable String pid, @PathVariable String clientId, @RequestBody Map<String,String> addlAttribs);
	
	@GetMapping("/{clientId}/process/group/task")
	List<Object> getTaskForGroup(@PathVariable String clientId,String userId,List<String> groups);
	
	@GetMapping("/{clientId}/process/task/{taskId}")
	Object viewTask(@PathVariable String clientId,@PathVariable String taskId);
	
	@PostMapping("/{clientId}/process/task/{userId}/{taskId}/complete")
	void completeTask(@PathVariable String clientId,@PathVariable String userId,@PathVariable String taskId);
	
	@PostMapping("{clientId}/process/task/{userId}/{taskId}/claim")
	void claimTask(@PathVariable String clientId,@PathVariable String userId,@PathVariable String taskId);
	
	@GetMapping("{clientId}/process-definition")
	List<ProcessDefinition> getAllProcessDefinitions(@PathVariable String clientId);
	
}