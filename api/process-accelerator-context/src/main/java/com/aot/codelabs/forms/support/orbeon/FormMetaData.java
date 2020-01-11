package com.aot.codelabs.forms.support.orbeon;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import org.joda.time.DateTime;

public class FormMetaData implements Serializable {

	private static final long serialVersionUID = -567277341189437768L;

	@JacksonXmlProperty(localName="application-name")
	private String appName;
	
	@JacksonXmlProperty(localName="form-name")
	private String formName;
	
	@JacksonXmlProperty(localName="title")
	private Title title;
	
	@JacksonXmlProperty(localName="last-modified-time")
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private DateTime lastModifiedDate;
	
	@JacksonXmlProperty(localName="form-version")
	private Integer version;
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	public Title getTitle() {
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
	}
	public DateTime getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(DateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public String getDescription() {
		return getTitle().getDescription();
	}
	
	public String getSource() {
		return "ORBEON";
	}
	
	static class Title {
		
		@JacksonXmlProperty(isAttribute=true)
		private String language;
		
		@JacksonXmlText
		private String description;
		
		public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	}
	
}