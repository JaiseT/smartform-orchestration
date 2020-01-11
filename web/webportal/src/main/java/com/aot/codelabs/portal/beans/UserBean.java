package com.aot.codelabs.portal.beans;

import java.io.IOException;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@ManagedBean("userBean")
@SessionScoped
public class UserBean {
	
	@Value("${keycloak.auth-logout-url}")
	private String logoutUrl;
	
	public void logout() throws IOException {
	   FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
       FacesContext.getCurrentInstance().getExternalContext().redirect(getLogoutUrl());
    }

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}
	
	public void navigateTo() throws IOException {
		HttpServletRequest httpReq = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(httpReq.isUserInRole("USER")) {
			 FacesContext.getCurrentInstance().getExternalContext().redirect("/webportal/user/application.xhtml");
		}
		else if(httpReq.isUserInRole("MANAGER")) {
			 FacesContext.getCurrentInstance().getExternalContext().redirect("/webportal/admin/tasks.xhtml");
		}
		if(httpReq.isUserInRole("FL_STAFF") || httpReq.isUserInRole("DL_STAFF") || httpReq.isUserInRole("FOI_STAFF")) {
			 FacesContext.getCurrentInstance().getExternalContext().redirect("/webportal/staff/tasks.xhtml");
		}
		
	}

	
}