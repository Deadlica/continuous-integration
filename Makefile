build:
	cd CI-server && mvn package

run:
	cd CI-server && mvn spring-boot:run
