package com.aot.codelabs.accelerator.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aot.codelabs.processor.model.SmartWebFormOrchestration;

/**
 * Interface for data services.
 * 
 * @author Sumathi Thirumani
 */
@RequestMapping("/orchestration/data")
public interface IOrchestrationDataController {

	@PostMapping(path="/save")
	SmartWebFormOrchestration saveOrchestrationInstance(@RequestBody SmartWebFormOrchestration data);
	
	@PostMapping(path="/update")
	SmartWebFormOrchestration updateOrchestrationInstance(@RequestBody SmartWebFormOrchestration data);
	
	@PostMapping(path="/update/{systemCorrId}/{status}")
	void updateOrchestrationStatus(@PathVariable Integer systemCorrId, @PathVariable String status);
	
	@GetMapping(path="/{systemCorrId}")
	SmartWebFormOrchestration getOrchestrationInstance(@PathVariable Integer systemCorrId);

	@GetMapping(path="/all")
	List<SmartWebFormOrchestration> getAllInstances();
	
	@GetMapping(path="/unclaimed")
	List<SmartWebFormOrchestration> getAllUnclaimedInstances();
	
	@GetMapping(path="/unclaimed/group/{processOwnerGroup}")
	List<SmartWebFormOrchestration> getAllUnclaimedInstancesForGroup(@PathVariable String processOwnerGroup);
	
	@GetMapping(path="/user/{userId}")
	List<SmartWebFormOrchestration> getInstancesByUser(@PathVariable String userId);
	
	@GetMapping(path="/group/{userId}")
	List<SmartWebFormOrchestration> getInstancesByProcessOwnerGroup(@PathVariable String processOwnerGroup);
	
}