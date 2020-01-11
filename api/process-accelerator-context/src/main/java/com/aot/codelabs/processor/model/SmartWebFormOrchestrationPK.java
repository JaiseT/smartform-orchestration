package com.aot.codelabs.processor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Key class for SmartWebFormOrchestration
 * 
 * @author Sumathi Thirumani
 */
@Embeddable
public class SmartWebFormOrchestrationPK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="GROUP_ID")
	private Integer groupId;
	
	@Column(name="FORM_ID")
	private Integer formId;
	
	public SmartWebFormOrchestrationPK(){
	}

	public SmartWebFormOrchestrationPK(String userId, Integer groupId, Integer formId) {
		this.userId = userId;
		this.groupId = groupId;
		this.formId = formId;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SmartFormOrchestrationPK [userId=");
		builder.append(userId);
		builder.append(", groupId=");
		builder.append(groupId);
		builder.append(", formId=");
		builder.append(formId);
		builder.append("]");
		return builder.toString();
	}
	
}