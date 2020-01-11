package com.aot.codelabs.accelerator.context;

/**
 * Enum for holding all available orchestration clients.
 * 
 * @author Sumathi Thirumani
 */
public enum OrchestrationClientCategory {
	CAMUNDA("camundaClient");
	
	private final String value;
	
	public String getValue() {
		return value;
	}

	OrchestrationClientCategory(String value){
		this.value=value;
	}
	
	public static boolean contains(String id){
		for(OrchestrationClientCategory o : OrchestrationClientCategory.values()) {
			if(o.name().equals(id)){
				return true;
			}
		}
		return false;
	}
	
}