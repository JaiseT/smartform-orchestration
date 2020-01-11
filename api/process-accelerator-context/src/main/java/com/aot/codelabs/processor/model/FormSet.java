package com.aot.codelabs.processor.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entity class for managing formSet and forms.
 * 
 * @author Sumathi Thirumani
 */
@Entity
@Table(name="FORMSET")
public class FormSet extends BaseDataEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_formset")
	@SequenceGenerator(name = "seq_formset", sequenceName = "seq_formset", allocationSize=1)
	@Column(name="FORMSET_ID")
	private Integer formsetId;
	
	@Column(name="FORMSET_NAME")
	private String formsetName;
	
	@Column(name="FORMSET_DESCRIPTION")
	private String formsetDescription;
	
	@JsonManagedReference
	@OneToMany(mappedBy="formset",cascade = CascadeType.ALL)
	private List<WebForm> forms;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="COMMENTS")
	private String comments;
	
	public List<WebForm> getForms() {
		return forms;
	}
	public void setForms(List<WebForm> forms) {
		this.forms = forms;
	}
	public Integer getformsetId() {
		return formsetId;
	}
	public void setformsetId(Integer formsetId) {
		this.formsetId = formsetId;
	}
	public String getformsetName() {
		return formsetName;
	}
	public void setformSetName(String formsetName) {
		this.formsetName = formsetName;
	}
	public String getformsetDescription() {
		return formsetDescription;
	}
	public void setformsetDescription(String formsetDescription) {
		this.formsetDescription = formsetDescription;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FormSet [formSetId=");
		builder.append(formsetId);
		builder.append(", formSetName=");
		builder.append(formsetName);
		builder.append(", formSetDescription=");
		builder.append(formsetDescription);
		builder.append("]");
		return builder.toString();
	}
	
}