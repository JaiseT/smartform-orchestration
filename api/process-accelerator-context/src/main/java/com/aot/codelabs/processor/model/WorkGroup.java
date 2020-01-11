package com.aot.codelabs.processor.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entity class for managing Group and forms.
 * 
 * @author Sumathi Thirumani
 */
@Entity
@Table(name="WORK_GROUP")
public class WorkGroup extends BaseDataEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="GROUP_ID")
	private Integer groupId;
	
	@Column(name="GROUP_NAME")
	private String groupName;
	
	@Column(name="GROUP_DESCRIPTION")
	private String groupDescription;
	
	@JsonManagedReference
	@OneToMany(mappedBy="group")
	private List<SmartWebForm> forms;
	
	public List<SmartWebForm> getForms() {
		return forms;
	}
	public void setForms(List<SmartWebForm> forms) {
		this.forms = forms;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupDescription() {
		return groupDescription;
	}
	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkGroup [groupId=");
		builder.append(groupId);
		builder.append(", groupName=");
		builder.append(groupName);
		builder.append(", groupDescription=");
		builder.append(groupDescription);
		builder.append("]");
		return builder.toString();
	}
	
}