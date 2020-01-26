FROM tomcat:8

ARG tag
ARG build

RUN \
    cd /tmp \
    && mkdir orbeon \
    && cd orbeon \
    && echo "tag: $tag" \
    && echo "build: $build" \
    && wget https://github.com/orbeon/orbeon-forms/releases/download/$tag/$build.zip \
    && unzip $build.zip \
    && cd $build \
    && unzip orbeon.war \
    && mkdir -p /usr/local/orbeon \
    && mv WEB-INF/ /usr/local/orbeon \
    && cd \
    && rm -rf /tmp/orbeon

# Download postgresql jdbc driver into tomcat lib
RUN \
    cd /tmp \
    && wget https://jdbc.postgresql.org/download/postgresql-$postgre_jdbc_version.jar \
    && mv postgresql-$postgre_jdbc_version.jar /usr/local/orbeon/WEB-INF/lib

#Copy tomcat and orbeon config files
COPY  /orbeon/postgres/tomcat/ /usr/local/tomcat/
COPY  /orbeon/postgres/orbeon/ /usr/local/orbeon/



#COPY /orbeon/custom/tomcat/ /usr/local/tomcat/
#COPY /orbeon/custom/orbeon/ /usr/local/orbeon/
#COPY /orbeon/custom/resources/ /usr/local/custom/resources/