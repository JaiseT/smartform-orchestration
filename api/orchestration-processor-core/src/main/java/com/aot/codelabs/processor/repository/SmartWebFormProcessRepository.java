package com.aot.codelabs.processor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aot.codelabs.processor.model.SmartWebFormProcess;

/**
 * Repository for entity SmartWebFormProcess.
 * SmartWebFormProcess: Entity for managing the mapping of webforms and orchestration process.
 * 
 * @author Sumathi Thirumani
 */
@Repository
public interface SmartWebFormProcessRepository extends JpaRepository<SmartWebFormProcess, Integer> { 
	
}

