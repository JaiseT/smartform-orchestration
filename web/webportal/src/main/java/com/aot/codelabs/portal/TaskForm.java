package com.aot.codelabs.portal;

import com.aot.codelabs.orchestration.Task;

public class TaskForm {
	
	private String formSource;
	private String mwSource;
	
	private Task task;

	public String getFormSource() {
		return formSource;
	}

	public void setFormSource(String formSource) {
		this.formSource = formSource;
	}

	public String getMwSource() {
		return mwSource;
	}

	public void setMwSource(String mwSource) {
		this.mwSource = mwSource;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
}