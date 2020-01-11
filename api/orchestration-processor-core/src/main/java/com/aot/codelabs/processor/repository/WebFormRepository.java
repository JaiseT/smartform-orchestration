package com.aot.codelabs.processor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aot.codelabs.processor.model.WebForm;

/**
 * Repository for entity smart webforms.
 * 
 * @author Sumathi Thirumani
 */
@Repository
public interface WebFormRepository extends JpaRepository<WebForm, Integer> { 
	
}

