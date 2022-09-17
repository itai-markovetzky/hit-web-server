
# DevOps Final Project - FULL CICD process

A full CICD process for web application.



## Built with
* Jenkins (Jenkins Pipeline)
* Docker
* Selenium (tests)
* RestAssured (tests)
* Java
## Description


This job is a full CICD process, with 6 steps.

    1. Checkout from the git repository.
    2. Build the docker image.
    3. Push the docker to the Docker hub.
    4. Deploy to QA environment.
    5. Running 2 tests with JUNIT and publishing results with JUNIT plugin.
    6. Deploy to PRODUCTION if it's git tagged that is v* and passed all tests.

The whole job is with timelimit of 20 minutes.
## Accessing Containers

In order to access to the **QA** Environment go to port **80**:

```http://localhost/80```

In order to access to the **PRODUCTION** Environment go to port **8082**:

```http://localhost/8082```

(This was selected randomly just for the project)
## Details about each step

#### Checkout step
_____
Inside this step we're automatically pulling the repo if there was any commit / push / merge to main branch.
also we're taking the tag it was pushed with for the future steps and also printing it out.

#### Building image
_____

We're building the docker image with the job name (**hit-web-server**) and docker tagging it with the job number.

#### Pushing image
_____
We're pushing the image into the Docker.io repo, In this case specificly to mine.

#### Deploying to QA
_____
In order to demonstrate the QA environment we're creating a docker container with the name **application-qa** running with the current image at port **80**.
we're also checking there is no previous instance of the container so the port can be free.

#### Running tests
_____
Here we're running 2 tests:

    1. with restassured that checks a GET and receives 200 back. 
    2. Selenium test - Open the page app and verify my name is inside.
after the tests we publish with JUNIT result with the JUNIT Jenkins Plugin, a screenshot will be shown later.
If one or more of the tests failed the build will be tagged as UNSTABLE.

#### Deploying to Production
_____
In order to deploy to production we need to answer 2 conditions:

    1. The git tag of the build needs to be v* (we check that using regex.)
    2. All the tests need to pass.
if both of the above conditions are made, a new contianer will be made named **application-prod** running with this good build at port **8082**.

#### Finish

## Screenshots
### JUNIT Plugin test results
![JUNIT result](https://puu.sh/JlLPB/d8174ab88d.png)

### Unstable job (one or more of the tests failed)
![UNSTABLE Job](https://puu.sh/JlLS8/4bd85f2aac.png)

### Stable job with good tag

![STABLE Job](https://puu.sh/JlLXm/54864612a3.png)

### Stable job with bad tag
![STABLE Job bad tag](https://puu.sh/JlM1b/e07c3fa8f1.png)



#### Made by Itai Markovetzky

