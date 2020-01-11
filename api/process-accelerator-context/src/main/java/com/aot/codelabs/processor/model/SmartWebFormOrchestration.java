package com.aot.codelabs.processor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity for holding the orchestration lifecycle of smartforms.
 * 
 * @author Sumathi Thirumani
 */
@Entity
@Table(name="SMARTFORM_ORCHESTRATION")
public class SmartWebFormOrchestration extends BaseDataEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_smf_orchestration")
	@SequenceGenerator(name = "seq_smf_orchestration", sequenceName = "seq_smartform_orchestration", allocationSize=1)
	@Column(name="SYSTEM_CORR_ID")
	private Integer systemCorrId;
	
	@Column(name="PROCESS_INSTANCE_ID", updatable=false)
	private String processInstanceId;
	
	@Column(name="DATA_ID")
	private String dataId;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="PROCESS_OWNER_GROUP")
	private String processOwnerGroup;
	
	@Column(name="PROCESS_ATTRIBS")
	private String processAttribs;
	
	@Column(name="USER_ID", updatable=false)
	private String userId;
	
	@Column(name="MAP_ID", updatable=false)
	private Integer mapId;
	
	public Integer getSystemCorrId() {
		return systemCorrId;
	}

	public void setSystemCorrId(Integer systemCorrId) {
		this.systemCorrId = systemCorrId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProcessOwnerGroup() {
		return processOwnerGroup;
	}

	public void setProcessOwnerGroup(String processOwnerGroup) {
		this.processOwnerGroup = processOwnerGroup;
	}

	public String getProcessAttribs() {
		return processAttribs;
	}

	public void setProcessAttribs(String processAttribs) {
		this.processAttribs = processAttribs;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Integer getMapId() {
		return mapId;
	}

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SmartWebFormOrchestration [systemCorrId=");
		builder.append(systemCorrId);
		builder.append(", processInstanceId=");
		builder.append(processInstanceId);
		builder.append(", dataId=");
		builder.append(dataId);
		builder.append(", status=");
		builder.append(status);
		builder.append(", processOwnerGroup=");
		builder.append(processOwnerGroup);
		builder.append(", processAttribs=");
		builder.append(processAttribs);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", mapId=");
		builder.append(mapId);
		builder.append("]");
		return builder.toString();
	}

}