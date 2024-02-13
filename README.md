# Continuous Integration Server


## Introduction
This Continuous Integration (CI) server automates the process of downloading projects from GitHub, compiling them with Maven, and running tests. Designed to streamline the development workflow, it provides immediate feedback on the build status of projects, enhancing productivity and code quality.

## Prerequisites
Before you begin, ensure you have the following installed:
- Java JDK 17 or newer
- Maven 3.6.0 or newer


## Installation
To set up the CI server on your local machine, follow these steps:

1. Navigate to the directory where you want to install the server.
2. Clone the repository and enter its root directory:
    ```bash
    git clone https://github.com/Deadlica/continuous-integration.git
    cd continuous-integration
    ```
3. Build the server into a `.jar` file and run all unit tests (optional):
    ```bash
    make build
    ```


## Configuration
Configure the server by setting environment variables or modifying configuration files as needed. Ensure the server has appropriate access to interact with GitHub repositories and perform builds.


## How to run the CI server
The following command will start the server on port `8080` and can be accessed here [localhost:8080](http://localhost:8080)
```bash
make run
```


## Usage
Integrate your GitHub projects with the CI server by setting up webhooks to trigger builds on push events. Consult the server documentation for detailed instructions on webhook setup and other features.


## Contributions
