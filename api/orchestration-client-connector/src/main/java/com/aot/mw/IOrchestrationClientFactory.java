package com.aot.mw;

/**
 * Factory class for Orchestration client.
 * 
 * @author Sumathi Thirumani
 */
public interface IOrchestrationClientFactory {
	@SuppressWarnings("rawtypes")
	IOrchestrationClient getOrchestrationClient(String clientID);
}