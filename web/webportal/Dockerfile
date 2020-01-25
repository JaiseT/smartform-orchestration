#Author : Twinkle Joseph
# Dockerfile
FROM tomcat:8

#Define tomcat home and keycloak version
ENV TOMCAT_HOME=/usr/local/tomcat \

     KEYCLOAK_VERSION=7.0.1
	 
#Download keycloak client adapter for tomcat 8
RUN cd ${TOMCAT_HOME}/lib/ \

  && curl -k -L -O https://downloads.jboss.org/keycloak/${KEYCLOAK_VERSION}/adapters/keycloak-oidc/keycloak-tomcat-adapter-dist-${KEYCLOAK_VERSION}.tar.gz \

  && tar zxvf keycloak-tomcat-adapter-dist-${KEYCLOAK_VERSION}.tar.gz	 

COPY /web/webportal/target/webportal.war /usr/local/tomcat/webapps

