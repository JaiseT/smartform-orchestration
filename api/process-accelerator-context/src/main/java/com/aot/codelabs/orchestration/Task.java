package com.aot.codelabs.orchestration;

import java.io.Serializable;

/**
 * Base structure for Task.
 * 
 * @author Sumathi Thirumani
 */
public class Task implements Serializable {
	
	private static final long serialVersionUID = 7385149574486772404L;
	
	private String id;
    private String name;
    private String assignee;
    private String created;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
    
}