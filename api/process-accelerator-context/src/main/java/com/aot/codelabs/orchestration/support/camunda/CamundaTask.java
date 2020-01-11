package com.aot.codelabs.orchestration.support.camunda;

import com.aot.codelabs.orchestration.Task;

/**
 * Carrier object for Camunda task.
 * 
 * @author Sumathi Thirumani
 */
public class CamundaTask extends Task {
	
	private static final long serialVersionUID = 4951449662158461716L;
	
		private String due;
	    private String followUp;
	    private String delegationState;
	    private String description;
	    private String executionId;
	    private String owner;
	    private String parentTaskId;
	    private int priority;
	    private String processDefinitionId;
	    private String processInstanceId;
	    private String taskDefinitionKey;
	    private String caseExecutionId;
	    private String caseInstanceId;
	    private String caseDefinitionId;
	    private boolean suspended;
	    private String formKey;
	    private String tenantId;
	    
	    private String deleteReason;
	    private Long duration;
	    private String startTime;
	    private String endTime;
	    private String groupName;
	    
		public String getDue() {
			return due;
		}
		public void setDue(String due) {
			this.due = due;
		}
		public String getFollowUp() {
			return followUp;
		}
		public void setFollowUp(String followUp) {
			this.followUp = followUp;
		}
		public String getDelegationState() {
			return delegationState;
		}
		public void setDelegationState(String delegationState) {
			this.delegationState = delegationState;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getExecutionId() {
			return executionId;
		}
		public void setExecutionId(String executionId) {
			this.executionId = executionId;
		}
		public String getOwner() {
			return owner;
		}
		public void setOwner(String owner) {
			this.owner = owner;
		}
		public String getParentTaskId() {
			return parentTaskId;
		}
		public void setParentTaskId(String parentTaskId) {
			this.parentTaskId = parentTaskId;
		}
		public int getPriority() {
			return priority;
		}
		public void setPriority(int priority) {
			this.priority = priority;
		}
		public String getProcessDefinitionId() {
			return processDefinitionId;
		}
		public void setProcessDefinitionId(String processDefinitionId) {
			this.processDefinitionId = processDefinitionId;
		}
		public String getProcessInstanceId() {
			return processInstanceId;
		}
		public void setProcessInstanceId(String processInstanceId) {
			this.processInstanceId = processInstanceId;
		}
		public String getTaskDefinitionKey() {
			return taskDefinitionKey;
		}
		public void setTaskDefinitionKey(String taskDefinitionKey) {
			this.taskDefinitionKey = taskDefinitionKey;
		}
		public String getCaseExecutionId() {
			return caseExecutionId;
		}
		public void setCaseExecutionId(String caseExecutionId) {
			this.caseExecutionId = caseExecutionId;
		}
		public String getCaseInstanceId() {
			return caseInstanceId;
		}
		public void setCaseInstanceId(String caseInstanceId) {
			this.caseInstanceId = caseInstanceId;
		}
		public String getCaseDefinitionId() {
			return caseDefinitionId;
		}
		public void setCaseDefinitionId(String caseDefinitionId) {
			this.caseDefinitionId = caseDefinitionId;
		}
		public String getGroupName() {
			return groupName;
		}
		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
		public boolean isSuspended() {
			return suspended;
		}
		public void setSuspended(boolean suspended) {
			this.suspended = suspended;
		}
		public String getFormKey() {
			return formKey;
		}
		public void setFormKey(String formKey) {
			this.formKey = formKey;
		}
		public String getTenantId() {
			return tenantId;
		}
		public void setTenantId(String tenantId) {
			this.tenantId = tenantId;
		}
		public String getDeleteReason() {
			return deleteReason;
		}
		public void setDeleteReason(String deleteReason) {
			this.deleteReason = deleteReason;
		}
		public Long getDuration() {
			return duration;
		}
		public void setDuration(Long duration) {
			this.duration = duration;
		}
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
	    
}