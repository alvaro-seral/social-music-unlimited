FROM bitnami/tomcat:9.0.38
MAINTAINER Cristian, Alvaro, Dorian

ADD *.jar /opt/bitnami/tomcat/lib/
ADD context.xml /opt/bitnami/tomcat/conf/
ADD server.xml /opt/bitnami/tomcat/conf/

ADD ROOT.war /opt/bitnami/tomcat/webapps/
