package com.aot.codelabs.processor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity for managing the smartform and orchestration mapping.
 * 
 * @author Sumathi Thirumani
 */
@Entity
@Table(name="SMARTFORM_PROCESS_MAPPER")
public class SmartWebFormProcess extends BaseDataEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prc_mapper")
	@SequenceGenerator(name = "seq_prc_mapper", sequenceName = "seq_smartform_process_mapper", allocationSize=1)
	@Column(name="MAP_ID")
	private Integer mapId;
	
	@Column(name="FORM_ID")
	private Integer formId;
	
	@Column(name="FORM_NAME")
	private String formName;
	
	@Column(name="FORMSET_ID")
	private Integer formsetId;
	
	@Column(name="FORMSET_NAME")
	private String formsetName;
	
	@Column(name="PROCESS_ID")
	private Integer processId;
	
	@Column(name="PROCESS_NAME")
	private String processName;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="COMMENTS")
	private String comments;


	public Integer getMapId() {
		return mapId;
	}

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
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

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public Integer getFormsetId() {
		return formsetId;
	}

	public void setFormsetId(Integer formsetId) {
		this.formsetId = formsetId;
	}

	public String getFormsetName() {
		return formsetName;
	}

	public void setFormsetName(String formsetName) {
		this.formsetName = formsetName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SmartWebFormProcess [mapId=");
		builder.append(mapId);
		builder.append(", formId=");
		builder.append(formId);
		builder.append(", formName=");
		builder.append(formName);
		builder.append(", formsetId=");
		builder.append(formsetId);
		builder.append(", formsetName=");
		builder.append(formsetName);
		builder.append(", processId=");
		builder.append(processId);
		builder.append(", processName=");
		builder.append(processName);
		builder.append(", status=");
		builder.append(status);
		builder.append(", comments=");
		builder.append(comments);
		builder.append("]");
		return builder.toString();
	}

	
	
}