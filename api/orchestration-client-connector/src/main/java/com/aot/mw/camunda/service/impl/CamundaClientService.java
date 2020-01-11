package  com.aot.mw.camunda.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aot.codelabs.accelerator.context.OrchestrationClientCategory;
import com.aot.codelabs.orchestration.ProcessDefinition;
import com.aot.codelabs.orchestration.support.camunda.CamundaProcessDefinition;
import com.aot.codelabs.orchestration.support.camunda.CamundaTask;
import com.aot.mw.camunda.service.ICamundaClientService;


/**
 * Implementation class for Camunda.
 * Note: Service name should match with OrchestrationClientCategory value.
 * 
 * @author Sumathi Thirumani
 */
@Service("camundaClient")
public class CamundaClientService implements ICamundaClientService {
	
	private static final Logger logger = LoggerFactory.getLogger(CamundaClientService.class);

	@Autowired
    private RestTemplate restTemplate;
	
	@Value("${camunda.service.URI}")
	private String clientURI;
	
	@Value("${camunda.tenant.Group}")
	private String tenantGroup;
	
	@Override
	public CamundaTask getTask(String taskId) {
		logger.debug("Fetching task details for id: {} ", taskId);
    	CamundaTask task = null ;
			try {
				CamundaTask[] taskArr = restTemplate.getForObject(getClientURI()+"/history/task?taskId=" + taskId , CamundaTask[].class);
				if(taskArr != null && taskArr.length > 0) {
					task = taskArr[0];
				}
			} catch (RuntimeException e) {
				logger.error("Exception occured on fetching task details", e);
			}
    	return task;
	}
	
	@Override
	public List<CamundaTask> getTaskForUserGroup(String userName,List<String> groups) {
		logger.debug("Fetching task details for username: {} ; groups: {} ", userName, groups);
		Map<String,CamundaTask> taskMap = new HashMap<String,CamundaTask>();
		//Get all active users task
		try {
		CamundaTask[] userTasks = getRestTemplateInstance().getForObject(getClientURI()+"/task?assignee=" + userName, CamundaTask[].class);
		if(userTasks != null && userTasks.length > 0 ){
			for(CamundaTask task: userTasks) {
				if(!taskMap.containsKey(task.getId())) { taskMap.put(task.getId(), task); } 
			}
		}
		//Get all tasks associated with groups
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	if(!CollectionUtils.isEmpty(groups)){
		for(String group: groups) {
			JSONObject taskJsonObject = new JSONObject();
			taskJsonObject.put("candidateGroup", group.replaceAll("/", ""));
			HttpEntity<String> requestEntity = new HttpEntity<String>(taskJsonObject.toString(),headers);
			CamundaTask[] groupTasks = getRestTemplateInstance().postForObject(getClientURI()+"/task", requestEntity, CamundaTask[].class);
			if(groupTasks != null && groupTasks.length > 0 ){
				for(CamundaTask task: userTasks) {
					if(!taskMap.containsKey(task.getId())) { taskMap.put(task.getId(), task); } 
				}
			}
		}
    	}
		} catch (RuntimeException | JSONException e) {
			logger.error("Exception occured on fetching task details", e);
		}
		List<CamundaTask> taskList = new ArrayList<CamundaTask>();
		if(taskMap.size() > 0) {
			taskList.addAll(taskMap.values());
		}
		return taskList;
	}
	
	@Override
	public void claimTask(String taskId,String userId) {
		logger.debug("Claiming task for id: {} ", taskId);
		try {
			HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_JSON);
	    	JSONObject taskJsonObject = new JSONObject();
	    	taskJsonObject.put("userId", userId);
	    	HttpEntity<String> requestEntity = new HttpEntity<String>(taskJsonObject.toString(),headers);
	    	getRestTemplateInstance().postForEntity(getClientURI()+"/task/"+taskId+"/claim", requestEntity,String.class);
		} catch (RuntimeException | JSONException e) {
			logger.error("Exception occured on claiming task", e);
		}
	}

	@Override
	public void completeTask(String taskId,String userId) {
		logger.debug("Completing task for id: {} ; userid: {} ", taskId, userId);
		try {
			HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_JSON);
	    	JSONObject taskJsonObject = new JSONObject();
	    	taskJsonObject.put("userId", userId);
	    	HttpEntity<String> requestEntity = new HttpEntity<String>(taskJsonObject.toString(),headers);
	    	getRestTemplateInstance().postForEntity(getClientURI()+"/task/"+taskId+"/complete", requestEntity,String.class);
		} catch (RuntimeException | JSONException e) {
			logger.error("Exception occured on completing task", e);
		}
	}
	
	@Override
	public CamundaTask createNewInstance(CamundaTask task,Map<String,String> addlAttributes) {
		logger.debug("Creating task for {} ; attributes: {} ", task.toString(), addlAttributes);
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	JSONObject variableListJsonObject = new JSONObject();
    	JSONObject variableJsonObject = new JSONObject();
    	JSONObject variableValueJsonObject = new JSONObject();
    	JSONObject tempObject = new JSONObject();
    	try {
    		for (Map.Entry mapElement : addlAttributes.entrySet()) {
    			variableValueJsonObject.put("value",  (String)mapElement.getValue());
    			tempObject = new JSONObject(variableValueJsonObject.toString());
    			variableValueJsonObject.remove("value");
    			variableJsonObject.put( (String)mapElement.getKey(), tempObject );
    			variableListJsonObject.put("variables", variableJsonObject);
    		}
    		HttpEntity<String> request = new HttpEntity<String>(variableListJsonObject.toString(),headers);
    		if(StringUtils.isEmpty(task.getProcessDefinitionId())) {
    			throw new RuntimeException("Process Definition cannot be empty.");
    		}
    		ResponseEntity<String> response  = getRestTemplateInstance().postForEntity(getClientURI() + "/process-definition/key/" + task.getProcessDefinitionId() + "/tenant-id/"+task.getTenantId()+"/start", request, String.class);
    		JSONObject json = new JSONObject(response.getBody());
    		String processInstanceId = (String) json.get("id");
    		if(StringUtils.isNotEmpty(processInstanceId)) {
    			task.setProcessInstanceId(processInstanceId);
    			//Get TaskId
    			task = getTaskId(processInstanceId);
    		} else {
    			throw new RuntimeException("Unknown exception occured in process instance creation.");
    		}
    	}
    	catch(RuntimeException | JSONException e) {
    		logger.error("Exception occured on creating new task instance", e);
    	}
    	return task;
	}
	
	private RestTemplate getRestTemplateInstance() {
		return restTemplate;
	}

	public String getClientURI() {
		return clientURI;
	}

	public void setClientURI(String clientURI) {
		this.clientURI = clientURI;
	}

	public String getTenantGroup() {
		return tenantGroup;
	}

	public void setTenantGroup(String tenantGroup) {
		this.tenantGroup = tenantGroup;
	}

	public OrchestrationClientCategory getServiceName() {
		return OrchestrationClientCategory.CAMUNDA;
	}

	/**
	 * Utility method to get the task ID.
	 * 
	 * @param processInstanceId
	 * @return
	 */
	private CamundaTask getTaskId(String processInstanceId) {
		logger.debug("Fetching task details for processInstanceId: {} ", processInstanceId);
    	CamundaTask task = null ;
			try {
				CamundaTask[] taskArr = restTemplate.getForObject(getClientURI()+"/task?processInstanceId=" + processInstanceId , CamundaTask[].class);
				task = taskArr != null && taskArr.length > 0 ? (CamundaTask) Arrays.asList(taskArr).get(0) : null;
			} catch (RuntimeException e) {
				logger.error("Exception occured on fetching task details", e);
			}
    	return task;
	}

	@Override
	public List<? extends ProcessDefinition> getAllProcesses() {
		List<String> tenantIds = new ArrayList<String>();
		if(StringUtils.isNotEmpty(tenantGroup)) { tenantIds = Arrays.asList(tenantGroup.split(",")); }
		logger.debug("Fetching all process definition for tenantId: {} ", tenantIds);
    	Map<String,CamundaProcessDefinition> prcMap = new HashMap<String,CamundaProcessDefinition>() ;
			try {
				CamundaProcessDefinition[] prdfArr = restTemplate.getForObject(getClientURI()+"/process-definition" , CamundaProcessDefinition[].class);
				for(CamundaProcessDefinition defn : prdfArr) {
					if(StringUtils.isNotEmpty(defn.getTenantId()) && tenantIds.contains(defn.getTenantId()) && !prcMap.containsKey(defn.getName())) {
						prcMap.put(defn.getName(),defn);
					}
				}
			} catch (RuntimeException e) {
				logger.error("Exception occured on fetching task details", e);
			}
		
    	return new ArrayList<CamundaProcessDefinition>(prcMap.values());
	}

}