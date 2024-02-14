<h1 align="center">Continuous Integration Server</h1>

<p align="center">
    <img src="https://github.com/Deadlica/continuous-integration/blob/main/CI-server/assets/server.gif" alt="code gif" width="540" height="405">
</p>


<br />

## Introduction
This Continuous Integration (CI) server automates the process of downloading, compiling and testing projects on Github. It is designed to streamline the development workflow, as it provides immediate feedback on the build status of projects, creating a seamless integration and quality control.

![build](https://github.com/deadlica/continuous-integration/actions/workflows/build-maven.yml/badge.svg)

![pull](https://img.shields.io/github/issues-pr/deadlica/continuous-integration)
![issues](https://img.shields.io/github/issues/deadlica/continuous-integration)
![coverage](https://img.shields.io/codecov/c/github/deadlica/continuous-integration)
![language](https://img.shields.io/github/languages/top/deadlica/continuous-integration)


## Technologies
The following technologies are required to build and run the server:
- Java version 17 or newer
- Maven 3.11.0 or newer

## Documentation
Extensive javadocs can be found at https://deadlica.github.io/continuous-integration/

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
To ensure that the server works correctly with your GitHub repository, you need to configure a GitHub webhook under the repository settings as well as generating a GitHub token with read/write permissions to `Commit Statuses` and `Contents` for your repository.

1. Make sure that the payload URL and content type is set as the table shown below (replace `domain` with the domain that you are using or `<IP-address>:<port>` if you are not using a domain)

`Note: If you're using a normal IP-address then you need to portforward a port that maps to port 8080 for the machine that's running the server`

| Field        | value                 |
| ------------ | --------------------- |
| Payload URL  | https://`domain`/push |
| Content type | application/json      |

2. Add the GitHub token for the repository as an environment variable for the machine running the CI server and name the environment variable as `GITHUB_TOKEN`

## How to run the CI server
The following command will start the server on port `8080` and can be accessed here [localhost:8080](http://localhost:8080)
```bash
make run
```

## How to run the Tests
Go into the CI-server directory and enter the command 
```bash
mvn test
```

## How to generate javadocs pages
Maven javadocs can be used to generate html pages of documentation for the entire project.
The following command will do just that and can be found under CI-server/target/site/apidocs
```bash
make docs
```

## Usage
Integrate your GitHub projects with the CI server by setting up webhooks to trigger builds on push events. Consult the server documentation for detailed instructions on webhook setup and other features.

## Build history webpage
The following page contains the full build history of our CI server. It is possible to get more detailed information of each build entry by clicking on that entry in the list.
[CI server web interface](https://ci-server.samflix.se/builds)

## Essence
The team currently in the stage "Formed". We have moved beyond the stage "seeded" since the members know the team goals and rules. The team is in the stage "formed" as it fulfils the checklist for the stage. Each member's individual responsibilites is defined, the members understand what their role is and everyone is commited to working in accorance to the defined rules and standards. Our communication is good and we have regular meetings which help us stay updated so that we can clearly define what needs to be done and how to distribute the work. The team members have begun to know and trust each other and this will hopefully improve as we continue to work together. We are focused on achieving the team mission (passing the assignment with P+) but we are not quite at the stage "Collaborating". We are currently getting to know and trust each other and we would also have become better at working better together as a cohesive unit to reach the next state. This will likely improve as we continue to work together as long as we work according to good principles and are mindfull of how we want to improve. 

## Contributions
**André**
- Worked on connecting the server to a SQL database
- Implemented commit status functionality 
- Worked on Essence and README

**Gustaf**
- Wrote the StatusUpdater class
- Implemented commit status functionality 
- Worked on connecting the server to a SQL database
- Updated the web interface used to browse the Build history

**Samuel**
- Set up project structure (using spring boot etc...)
- Set up a dedicated server under the "samflix.se", "samflix.app" domain
- Wrote a docker image, to run the web server as a docker container on the dedicated server
- Worked on parsing json, cloning repo and compiling code, running test
- Setup generating javadocs pages and deploying to GitHub pages
- Implemented the REST API endpoints
- Implemented the Controller endpoints for dynamically displaying build logs on HTML pages
- Added GitHub CI workflow for building the project with maven on every push and all pull requests to main
- Worked on building threads

**Noel**
- Worked on parsing json, cloning repo and compiling code, running test
- implemeted unit test for several sections
- Worked on building threads  

**Rafael**
- implemeted unit test for several sections
- Worked on README
- Some documentation
 
