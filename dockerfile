FROM tomcat
ADD ./target/ProjectOne.war /usr/local/tomcat/webapps/

EXPOSE 7777