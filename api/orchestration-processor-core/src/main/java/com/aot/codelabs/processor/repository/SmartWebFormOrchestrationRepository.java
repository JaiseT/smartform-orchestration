package com.aot.codelabs.processor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aot.codelabs.processor.model.SmartWebFormOrchestration;

/**
 * Repository for entity SmartWebFormOrchestration.
 * SmartWebFormOrchestration: Entity for managing the orchestration of system.
 * 
 * @author Sumathi Thirumani
 */
@Repository
public interface SmartWebFormOrchestrationRepository extends JpaRepository<SmartWebFormOrchestration, Integer> {
		
	 @Modifying
	 @Transactional
	 @Query("update SmartWebFormOrchestration SET processInstanceId = :processInstanceId, status = :status, processOwnerGroup = :processOwnerGroup, processAttribs = :processAttribs "
	 		+ "WHERE systemCorrId = :systemCorrId")
	 void updateProcessInstanceID(@Param("processInstanceId") String processInstanceId,@Param("status") String status,
			 @Param("processOwnerGroup") String processOwnerGroup, @Param("processAttribs")String processAttribs,
			 @Param("systemCorrId") Integer systemCorrId);
	 
	 @Modifying
	 @Transactional
	 @Query(value = "update SmartWebFormOrchestration SET status = :status WHERE systemCorrId = :systemCorrId")
	 void updateProcessInstanceStatus(@Param("status") String status, @Param("systemCorrId") Integer systemCorrId);
	 
	 List<SmartWebFormOrchestration> findByStatusOrderByCreatedTs(String status);
	 
	 SmartWebFormOrchestration findByProcessInstanceId(String processInstanceId);
	 
	 @Query("Select s from SmartWebFormOrchestration s where s.processAttribs like %:processAttribValue%")
	 SmartWebFormOrchestration findByprocessAttribsIsLike(String processAttribValue);
	 
	 List<SmartWebFormOrchestration> findByUserIdAndStatusIn (String userId, List<String> status);
	 
	 List<SmartWebFormOrchestration> findByUserIdOrderByCreatedTsDesc(String userId);
	
	 List<SmartWebFormOrchestration> findByprocessOwnerGroupAndStatusInOrderByCreatedTs(String processOwnerGroup, List<String> status);
	 
}