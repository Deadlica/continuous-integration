build:
	cd CI-server && mvn package

run:
	cd CI-server && mvn spring-boot:run

docs:
	cd CI-server && mvn javadoc:javadoc
