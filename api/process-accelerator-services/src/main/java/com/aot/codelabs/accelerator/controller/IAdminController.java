package com.aot.codelabs.accelerator.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aot.codelabs.processor.model.SmartWebFormProcess;
import com.aot.codelabs.processor.model.FormSet;
import com.aot.codelabs.processor.model.WebForm;
import com.aot.codelabs.processor.model.WorkProcess;

/**
 * Interface for admin operations.
 * 
 * @author Sumathi Thirumani
 */
@RequestMapping("/admin")
public interface IAdminController {
	
	@GetMapping(path="/formset")
	@ResponseBody
	List<FormSet> getAllFormSet();
	
	@GetMapping(path="/formset/{formsetId}")
	@ResponseBody
	FormSet getFormSet(@PathVariable Integer formsetId);
	
	@PostMapping(path="/formset/save")
	@ResponseBody
	void saveFormSet(@RequestBody FormSet formset);
	
	@GetMapping(path="/form")
	@ResponseBody
	List<WebForm> getAllForm();
	
	@GetMapping(path="/form/{formId}")
	@ResponseBody
	WebForm getForm(@PathVariable Integer formId);
	
	@PostMapping(path="/form/save")
	@ResponseBody
	void saveForm(@RequestBody WebForm form);
	
	@GetMapping(path="/process")
	@ResponseBody
	List<WorkProcess> getAllProcess();
	
	@PostMapping(path="/process/save")
	@ResponseBody
	void saveWorkProcess(@RequestBody WorkProcess process);
	
	@GetMapping(path="/process/{processId}")
	@ResponseBody
	WorkProcess getWorkProcess(@PathVariable Integer processId);
	
	@GetMapping(path="/forms/process")
	@ResponseBody
	List<SmartWebFormProcess> getAllActiveFormProcess();
	
	@PostMapping(path="/forms/process/save")
	@ResponseBody
	void saveFormProcess(@RequestBody SmartWebFormProcess process);
	
	@GetMapping(path="/forms/process/{mapId}")
	@ResponseBody
	SmartWebFormProcess getFormProcess(@PathVariable Integer mapId);
	
}