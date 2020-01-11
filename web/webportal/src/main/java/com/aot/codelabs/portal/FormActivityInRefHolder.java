package com.aot.codelabs.portal;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Indirect Reference implementation to hide the data in client tier.
 * 
 * @author Sumathi Thirumani
 */
@Component("formActivityInRefHolder")
@Singleton
public class FormActivityInRefHolder {
	
	private final LinkedHashMap<String, Map<String,Object>> formsActivityMap = new LinkedHashMap<String,Map<String,Object>>();
	
	public String addToPool(Map<String,Object> data) {
		String key =getId();
		getFormsActivityMap().put(key, data);
		return key;
	}
	
	public Map<String,Object> getFromPool(String id) {
		return getFormsActivityMap().get(id);
	}
	
	public void removeFromPool(String id){
		getFormsActivityMap().remove(id);
	}
	
	private String getId() {
		return UUID.randomUUID().toString();
	}
	
	public LinkedHashMap<String, Map<String,Object>> getFormsActivityMap() {
		return formsActivityMap;
	}

}