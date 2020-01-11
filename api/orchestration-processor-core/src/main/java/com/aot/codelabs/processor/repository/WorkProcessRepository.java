package com.aot.codelabs.processor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aot.codelabs.processor.model.WorkProcess;

/**
 * Repository for entity WorkGroup.
 * WorkProcess: Entity for managing the orchestration processes.
 * 
 * @author Sumathi Thirumani
 */
@Repository
public interface WorkProcessRepository extends JpaRepository<WorkProcess, Integer> { 
	
}