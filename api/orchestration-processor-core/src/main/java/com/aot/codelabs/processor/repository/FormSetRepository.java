package com.aot.codelabs.processor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aot.codelabs.processor.model.FormSet;

/**
 * Repository for entity WorkGroup.
 * WorkGroup: Entity for managing the Group and webform association.
 * 
 * @author Sumathi Thirumani
 */
@Repository
public interface FormSetRepository extends JpaRepository<FormSet, Integer> { 
	
}

