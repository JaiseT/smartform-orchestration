package com.aot.codelabs.processor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity for orchestration process.
 * 
 * @author Sumathi Thirumani
 */
@Entity
@Table(name="WORK_PROCESS")
public class WorkProcess extends BaseDataEntity {
	
	private static final long serialVersionUID = 6007406702010937533L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_work_process")
	@SequenceGenerator(name = "seq_work_process", sequenceName = "seq_work_process", allocationSize=1)
	@Column(name="PROCESS_ID")
	private Integer processId;
	
	@Column(name="PROCESS_NAME")
	private String processName;
	
	@Column(name="PROCESS_DESCRIPTION")
	private String processDescription;
	
	@Column(name="PROCESS_SOURCE")
	private String processSource;
	
	@Column(name="PROCESS_KEY")
	private String processKey;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="COMMENTS")
	private String comments; 

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

	public String getProcessDescription() {
		return processDescription;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}

	public String getProcessSource() {
		return processSource;
	}

	public void setProcessSource(String processSource) {
		this.processSource = processSource;
	}

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkProcess [processId=");
		builder.append(processId);
		builder.append(", processName=");
		builder.append(processName);
		builder.append(", processDescription=");
		builder.append(processDescription);
		builder.append(", processSource=");
		builder.append(processSource);
		builder.append(", processKey=");
		builder.append(processKey);
		builder.append("]");
		return builder.toString();
	}
}