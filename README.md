# DevOps Final Project - Full CI/CD Process

This repository presents a comprehensive CI/CD pipeline for a web application.

## Table of Contents
- [Tools Used](#tools-used)
- [Overview](#overview)
  - [Checkout Step](#checkout-step)
  - [Build Docker Image](#build-docker-image)
  - [Push Docker Image](#push-docker-image)
  - [Deploy to QA](#deploy-to-qa)
  - [Run Tests](#run-tests)
  - [Deploy to Production](#deploy-to-production)
- [Accessing Environments](#accessing-environments)
- [Screenshots](#screenshots)
  - [JUnit Plugin Test Results](#junit-plugin-test-results)
  - [Unstable Job (One or More Tests Failed)](#unstable-job-one-or-more-tests-failed)
  - [Stable Job with a Good Tag](#stable-job-with-a-good-tag)
  - [Stable Job with a Bad Tag](#stable-job-with-a-bad-tag)
- [Author](#Author)

## Tools Used
* Jenkins (Jenkins Pipeline)
* Docker
* Selenium (for tests)
* RestAssured (for tests)
* Java

## Overview

This CI/CD process consists of 6 steps:

1. **Checkout:** Pulls the latest changes from the Git repository, captures the associated tag, and prints it.

2. **Build Docker Image:** Creates a Docker image named **hit-web-server** and tags it with the Jenkins job number.

3. **Push Docker Image:** Pushes the Docker image to Docker Hub.

4. **Deploy to QA:** Sets up a QA environment by running a Docker container named **application-qa** on port 80.

5. **Run Tests:** Executes two types of tests - one with RestAssured, checking a GET request, and another Selenium test verifying the presence of a name on the web page. Test results are published using the JUnit Jenkins Plugin. If any test fails, the build is tagged as UNSTABLE.

6. **Deploy to Production:** Deploys to production only if the Git tag matches the pattern v* and all tests pass. The production environment runs in a Docker container named **application-prod** on port 8082.

The entire pipeline has a time limit of 20 minutes.

## Accessing Environments

- **QA Environment:** [http://localhost/80](http://localhost/80)
- **Production Environment:** [http://localhost/8082](http://localhost/8082) 

## Step Details

### Checkout Step

Automatically pulls the latest changes from the main branch and captures the associated tag.

### Build Docker Image

Builds the Docker image with the name **hit-web-server** and tags it with the job number.

### Push Docker Image

Pushes the Docker image to Docker Hub.

### Deploy to QA

Creates a Docker container named **application-qa** running on port 80.

### Run Tests

Executes RestAssured and Selenium tests, publishing results with the JUnit Jenkins Plugin. If any test fails, the build is tagged as UNSTABLE.

### Deploy to Production

Deploys to production only if the Git tag matches v* and all tests pass. The production container is named **application-prod** and runs on port 8082.

## Screenshots

### JUnit Plugin Test Results
![JUnit Result](https://github.com/itai-markovetzky/hit-web-server/blob/main/Screenshots/brave_nnL88igsvO.png)

### Unstable Job (One or More Tests Failed)
![Unstable Job](https://github.com/itai-markovetzky/hit-web-server/blob/main/Screenshots/brave_pnOJqXs8dS.png)

### Stable Job with a Good Tag
![Stable Job](https://github.com/itai-markovetzky/hit-web-server/blob/main/Screenshots/brave_38nCY86exk.png)

### Stable Job with a Bad Tag
![Stable Job Bad Tag](https://github.com/itai-markovetzky/hit-web-server/blob/main/Screenshots/brave_Tp3omsr2G4.png)

## Author

This CI/CD pipeline was developed and is maintained by:

- [Itai Markovetzky](https://github.com/itai-markovetzky)

Feel free to reach out if you have any questions or feedback. Contributions are welcome!
