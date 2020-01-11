package com.aot.codelabs.processor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aot.codelabs.processor.model.FormSet;
import com.aot.codelabs.processor.model.WebForm;
import com.aot.codelabs.processor.model.SmartWebFormProcess;
import com.aot.codelabs.processor.model.WorkProcess;
import com.aot.codelabs.processor.repository.FormSetRepository;
import com.aot.codelabs.processor.repository.SmartWebFormProcessRepository;
import com.aot.codelabs.processor.repository.WebFormRepository;
import com.aot.codelabs.processor.repository.WorkProcessRepository;
import com.aot.codelabs.processor.service.ISmartWebFormAdminService;

/**
 * Implementation class for admin service operations.
 * 
 * @author Sumathi Thirumani
 */
@Service("smartWebFormAdminService")
public class SmartWebFormAdminService implements ISmartWebFormAdminService {
    
	@Autowired
	private FormSetRepository formSetRepository;
	
	@Autowired
	private WebFormRepository webFormRepository;
	
	@Autowired
	private SmartWebFormProcessRepository smartWebFormProcessRepository;
	
	@Autowired
	private WorkProcessRepository workProcessRepository;
	
	@Override
	public List<FormSet> getAllFormSet() {
		return formSetRepository.findAll();
	}

	@Override
	public FormSet getFormSetById(Integer formSetId) {
		return formSetRepository.findById(formSetId).get();
	}
	
	@Override
	public List<WorkProcess> getAllProcess() {
		return workProcessRepository.findAll();
	}

	
	@Override
	public WorkProcess getProcessById(Integer processId) {
		return workProcessRepository.findById(processId).get();
	}
	

	@Override
	public void saveWorkProcess(WorkProcess process) {
		workProcessRepository.save(process);
	}

	@Override
	public List<SmartWebFormProcess> getAllWebFormProcess() {
		return smartWebFormProcessRepository.findAll();
	}

	@Override
	public void saveWebFormProcess(SmartWebFormProcess process) {
		smartWebFormProcessRepository.save(process);
	}
	
	@Override
	public void saveFormSet(FormSet formSet) {
		formSetRepository.save(formSet);
	}

	@Override
	public List<WebForm> getAllForms() {
		return webFormRepository.findAll();
	}

	@Override
	public WebForm getWebFormById(Integer formId) {
		return webFormRepository.findById(formId).get();
	}

	@Override
	public void saveWebForm(WebForm form) {
		webFormRepository.save(form);
	}

	@Override
	public SmartWebFormProcess getWebFormProcess(Integer mapId) {
		return smartWebFormProcessRepository.findById(mapId).get();
	}


}