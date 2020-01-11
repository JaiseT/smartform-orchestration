This document is intended to cover the setup and changes made for POC.

1. Version : orbeon-2019.1.0.201910220019-CE
	Download site : https://www.orbeon.com/download

2. External datasource setup
	a. Include resource "jdbc/postgresql" in server.xml 
	[Reference: C:\sumi\aotspace\softwares\orbeonspace\apache-tomcat-7.0.93\conf]
	b. Enable external resource connection in web.xml 
	[Reference: C:\sumi\aotspace\softwares\orbeonspace\apache-tomcat-7.0.93\webapps\orbeon\WEB-INF]
	c. Include the property of persistence provider information on targeted profile specific properties-local-<profile>.xml
	[Reference: C:\sumi\aotspace\softwares\orbeonspace\apache-tomcat-7.0.93\webapps\orbeon\WEB-INF\resources\config]
	d. Run the DDL scripts on database
	Script Download site :https://doc.orbeon.com/form-runner/persistence/relational-db
	
3. Enable Rest services
	a. Include the property "service-public-methods" on targeted profile specific properties-local-<profile>.xml 
	[Reference: C:\sumi\aotspace\softwares\orbeonspace\apache-tomcat-7.0.93\webapps\orbeon\WEB-INF\resources\config]
	
4. For service enabled custom button of form [SERVICE URI OF PORTAL - HARDCODED]
	a. Service URI to be modified on targeted profile specific properties-local-<profile>.xml 
	[Reference: C:\sumi\aotspace\softwares\orbeonspace\apache-tomcat-7.0.93\webapps\orbeon\WEB-INF\resources\config]
	
5. Setting up profile is on web.xml
	[Reference: C:\sumi\aotspace\softwares\orbeonspace\apache-tomcat-7.0.93\webapps\orbeon\WEB-INF]
