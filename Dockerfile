FROM tomcat:9-jdk8
ADD target/film2night-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/film2night-1.0-SNAPSHOT.war
EXPOSE 8080
CMD ["catalina.sh","run"]