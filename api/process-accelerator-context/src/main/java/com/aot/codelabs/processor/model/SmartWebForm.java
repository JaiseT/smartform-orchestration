package com.aot.codelabs.processor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Entity class for holding form details.
 * 
 * @author Sumathi Thirumani
 */
@Entity
@Table(name = "SMART_WEBFORM")
public class SmartWebForm extends BaseDataEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="FORM_ID")
	private Integer formId;
	
	@Column(name="FORM_NAME")
	private String formName;
	
	@Column(name="FORM_DESCRIPTION")
	private String formDescription;
	
	@Column(name="FORM_SOURCE")
	private String formSource;
	
	@Column(name="FORM_LOCATION")
	private String formLocation;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="GROUP_ID", nullable=false)
	private WorkGroup group;

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormDescription() {
		return formDescription;
	}

	public void setFormDescription(String formDescription) {
		this.formDescription = formDescription;
	}

	public String getFormSource() {
		return formSource;
	}

	public void setFormSource(String formSource) {
		this.formSource = formSource;
	}

	public String getFormLocation() {
		return formLocation;
	}

	public void setFormLocation(String formLocation) {
		this.formLocation = formLocation;
	}

	public WorkGroup getGroup() {
		return group;
	}

	public void setGroup(WorkGroup group) {
		this.group = group;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SmartWebFormGroup [formId=");
		builder.append(formId);
		builder.append(", formName=");
		builder.append(formName);
		builder.append(", formDescription=");
		builder.append(formDescription);
		builder.append(", formSource=");
		builder.append(formSource);
		builder.append(", formLocation=");
		builder.append(formLocation);
		builder.append(", group=");
		builder.append(group);
		builder.append("]");
		return builder.toString();
	}
}