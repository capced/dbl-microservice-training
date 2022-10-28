.PHONY: all cleanDatabase updateDatabase runMockServer initExpectations runApi runFront runTests

all: cleanDatabase updateDatabase runMockServer initExpectations runApi runFront runTests

cleanDatabase:
	mvn -B -f database/pom.xml clean compile liquibase:dropAll

updateDatabase:
	mvn -B -f database/pom.xml clean compile liquibase:update
	
dbDocumentation:
	mvn -B -f database/pom.xml liquibase:dbDoc -Dliquibase.outputDirectory=target/dbdocs

runMockServer:
	mvn -B -f mock/pom.xml -Dmockserver.serverPort=1080 -Dmockserver.logLevel=INFO org.mock-server:mockserver-maven-plugin:5.12.0:runForked 

initExpectations:
	mvn -B -f mock/pom.xml clean compile exec:java

runApi:
	mvn -B -f api/pom.xml clean package spring-boot:run &

runFront:
	cd frontend; \
	export GIT_DIR=../; \
	npm install; \
	npx ng serve &

runTests:
	sleep 30
	mvn -B -f control/pom.xml clean package

stopMockServer:
	mvn -B -f mock/pom.xml -Dmockserver.serverPort=1080 -Dmockserver.logLevel=INFO -Dmockserver.persistedExpectationsPath=mocks org.mock-server:mockserver-maven-plugin:5.12.0:stopForked
	