package com.aot.mw;

import java.util.List;
import java.util.Map;

/**
 * Interface captures all task specific behaviors.
 * 
 * @author Sumathi Thirumani
 *
 * @param <T>
 */
public interface ITaskService<T> {
	T getTask(String taskId);
	List<T> getTaskForUserGroup(String userName,List<String> groups);
	T createNewInstance(T task,Map<String,String> addlAttributes);
	void claimTask(String taskId,String userId);
	void completeTask(String taskId,String userId);
}
