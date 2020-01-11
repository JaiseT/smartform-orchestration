package com.aot.codelabs.portal.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aot.codelabs.portal.FormActivityInRefHolder;
import com.aot.codelabs.processor.model.SmartWebFormOrchestration;
import com.aot.codelabs.processor.model.SmartWebFormProcess;

/**
 * Hook for interaction with orbeon.
 * 
 * @author Sumathi Thirumani
 */
@RestController
@RequestMapping(path="/forms-manager/orbeon")
public class OrbeonDataController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrbeonDataController.class);
	
	@Autowired(required=true)
	private FormActivityInRefHolder formActivityInRefHolder;
	
	@Autowired(required=true)
    private RestTemplate restTemplate;
	
	@Value("${accelerator.service.URI}")
	private String serviceURI;

	@PostMapping(value = "/post-save", consumes = MediaType.APPLICATION_XML_VALUE)
	public void getDocumentID(@RequestParam Map<String, String> reqParam){
		logger.debug("Post save hook of orbeon service for {}", reqParam);
		String docId = null;
		String refId = null;
		String corrId = null;
		//Retrieve document ID
		for (Map.Entry<String,String> entry : reqParam.entrySet()) { 
			if(entry.getKey().matches("document")) {
				docId =  entry.getValue();
			}
			if(entry.getKey().matches("refId")) {
				refId =  entry.getValue();
			}
			if(entry.getKey().matches("corrId")) {
				corrId =  entry.getValue();
			}
		}
		if(StringUtils.isEmpty(docId)) {
			throw new RuntimeException("Error in persisting the webform");
		}
		logger.debug("Post save hook of orbeon service. refId : {} docId : {}", refId, docId);
		logger.debug("------------>"+formActivityInRefHolder);
		formActivityInRefHolder.getFromPool(refId);
		String userId = formActivityInRefHolder.getFromPool(refId).get("userid") ==null ? "SYSTEM" :
			(String) formActivityInRefHolder.getFromPool(refId).get("userid");
		if(StringUtils.isEmpty(corrId)) {
			//Create new process-flow record in application datasource.
			SmartWebFormProcess form = (SmartWebFormProcess) formActivityInRefHolder.getFromPool(refId).get("SmartWebFormProcess");
			SmartWebFormOrchestration orchObj = new SmartWebFormOrchestration();
			orchObj.setDataId(docId);
			orchObj.setCreatedBy(userId);
			orchObj.setUserId(userId);
			orchObj.setMapId(form.getMapId());
			logger.debug("Create orchestration instance {}", orchObj.toString());
			SmartWebFormOrchestration resp = restTemplate.postForObject(serviceURI+"/orchestration/data/save", orchObj, SmartWebFormOrchestration.class);
			formActivityInRefHolder.getFromPool(refId).put("SmartWebFormOrchestration", resp);
			logger.info("Orchestration instance successfully created for {}", orchObj.toString());
		} else {
			SmartWebFormOrchestration req = restTemplate.getForObject(serviceURI+"/orchestration/data/"+corrId, SmartWebFormOrchestration.class);
			req.setDataId(docId);
			req.setUpdatedBy(userId);
			SmartWebFormOrchestration resp = restTemplate.postForObject(serviceURI+"/orchestration/data/save", req, SmartWebFormOrchestration.class);
			formActivityInRefHolder.getFromPool(refId).put("SmartWebFormOrchestration", resp);
			logger.info("Orchestration instance successfully updated for {}", resp.toString());
		}
	}
	
	

}