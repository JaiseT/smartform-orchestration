package com.aot.codelabs.processor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Entity class for holding form details.
 * 
 * @author Sumathi Thirumani
 */
@Entity
@Table(name = "WEBFORM")
public class WebForm extends BaseDataEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sm_wform")
	@SequenceGenerator(name = "seq_sm_wform", sequenceName = "seq_smart_webform", allocationSize=1)
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
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="COMMENTS")
	private String comments;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="FORMSET_ID", nullable=false)
	private FormSet formset;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public FormSet getFormset() {
		return formset;
	}

	public void setFormSet(FormSet formset) {
		this.formset = formset;
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
		builder.append(formset);
		builder.append("]");
		return builder.toString();
	}
}