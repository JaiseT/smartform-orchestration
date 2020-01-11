package com.aot.codelabs.orchestration.support.camunda;

import com.aot.codelabs.orchestration.ProcessDefinition;

public class CamundaProcessDefinition extends ProcessDefinition  {
	
	private static final long serialVersionUID = 1L;
	
	private Integer version;
	private String resource;
	private String deploymentId;
	private String diagram;
	private String versionTag;
	private Integer historyTimeToLive;
	
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getDiagram() {
		return diagram;
	}
	public void setDiagram(String diagram) {
		this.diagram = diagram;
	}
	public String getVersionTag() {
		return versionTag;
	}
	public void setVersionTag(String versionTag) {
		this.versionTag = versionTag;
	}
	public Integer getHistoryTimeToLive() {
		return historyTimeToLive;
	}
	public void setHistoryTimeToLive(Integer historyTimeToLive) {
		this.historyTimeToLive = historyTimeToLive;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProcessDefinition [id=");
		builder.append(getId());
		builder.append(", key=");
		builder.append(getKey());
		builder.append(", category=");
		builder.append(getCategory());
		builder.append(", description=");
		builder.append(getDescription());
		builder.append(", name=");
		builder.append(getName());
		builder.append(", version=");
		builder.append(version);
		builder.append(", resource=");
		builder.append(resource);
		builder.append(", deploymentId=");
		builder.append(deploymentId);
		builder.append(", diagram=");
		builder.append(diagram);
		builder.append(", suspended=");
		builder.append(getSuspended());
		builder.append(", tenantId=");
		builder.append(getTenantId());
		builder.append(", versionTag=");
		builder.append(versionTag);
		builder.append(", historyTimeToLive=");
		builder.append(historyTimeToLive);
		builder.append("]");
		return builder.toString();
	}
	
}