make setupLINUX:
	wget -U none https://repo1.maven.org/maven2/org/eclipse/jetty/aggregate/jetty-all/7.0.2.v20100331/jetty-all-7.0.2.v20100331.jar
	wget -U none https://repo1.maven.org/maven2/javax/servlet/servlet-api/2.5/servlet-api-2.5.jar
	curl -LO --tlsv1 https://bin.equinox.io/c/4VmDzA7iaHb/ngrok-stable-linux-amd64.zip
	unzip ngrok-stable-linux-amd64.zip 

make setupMAC:
	wget -U none https://repo1.maven.org/maven2/org/eclipse/jetty/aggregate/jetty-all/7.0.2.v20100331/jetty-all-7.0.2.v20100331.jar
	wget -U none https://repo1.maven.org/maven2/javax/servlet/servlet-api/2.5/servlet-api-2.5.jar
	curl -LO --tlsv1 https://bin.equinox.io/c/4VmDzA7iaHb/ngrok-stable-darwin-386.zip
	unzip ngrok-stable-darwin-386.zip


make compileServer:
	javac -cp servlet-api-2.5.jar:jetty-all-7.0.2.v20100331.jar ContinuousIntegrationServer.java

make startServer:
	java -cp .:servlet-api-2.5.jar:jetty-all-7.0.2.v20100331.jar ContinuousIntegrationServer &

make server:
	make compileServer
	make startServer

make proxy:
	./ngrok http 8019

make run: 
	make server
	make proxy
