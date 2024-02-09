# Continuous Integration Server


## Installation
Start by navigating to a directory where you would like to install the server

Next, clone the repository and navigate inside its root directory:
```bash
git clone https://github.com/Deadlica/continuous-integration.git
cd continuous-integration
```

Lastly, the following command will build the server into a .jar file as well as run all unit tests

`Note: It is not necessary to do this in order to run the server`
```bash
make build
```



## How to run the CI server
The following command will start the server on port `8080` and can be accessed here [localhost:8080](http://localhost:8080)
```bash
make run
```
