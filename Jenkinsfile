pipeline {
    environment {
        registry = "itay71700/hit-web-server"
        registryCredential = 'itay71700'
        dockerImage = ''
        gitTag = null
        testflag = "true"
    }
    agent any
    options{
        timeout(time:30, unit: 'MINUTES')
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'git', url: 'git@github.com:Neophyte96/hit-web-server.git'
                script {
                    gitTag = sh(returnStdout: true, script: "git tag --sort=-creatordate | head -n 1").trim()

                    if (gitTag) {
                        echo "The tag for this build is : $gitTag"
                    } else {
                        echo "This build has no tag."
                    }
                }
            }
        }
        stage('Docker Build') {
            steps {
                script {
                    dockerImageName = "itay71700/hit-web-server:$BUILD_NUMBER"
                    dockerImage = docker.build(dockerImageName, "./webServer/")
                }
                echo "The build has been successfully finished."
            }
        }
        stage('Docker Push') {
            steps {
                script {
                    docker.withRegistry("", registryCredential) {
                        dockerImage.push()
                    }
                }
                echo "The push has been successfully finished."
            }
            options{
                timeout(time:5, unit: 'MINUTES')
            }
        }
        stage('Deploy to QA') {
            steps {
                echo "Closing previous container instances if exist:"
                sh "docker rm -f application-qa"
                echo "Deploying to the QA container.."
                sh "docker run --rm --name application-qa -d -p  80:80 $dockerImageName"
                echo "Deployment to the QA has been successfully finished."
            }
        }
        options{
            timeout(time:5, unit: 'MINUTES')
        }
        stage('Run Automation tests') {
            steps {
                catchError(message: "One or more tests have been failed and therefore deployment to production is skipped", buildResult: "UNSTABLE", stageResult: "UNSTABLE") {
                    echo "Running the Tests!"
                    dir("automation")
                            {
                                sh "gradle clean test"
                            }
                }
                junit skipMarkingBuildUnstable: true, testResults: 'automation/build/test-results/test/TEST-webApplicationTests.xml'
                echo "Test results have been successfully published."

            }
            options{
                timeout(time:5, unit: 'MINUTES')
            }
        post {
            unstable {
                echo "Some of the tests failed therefore this build is set to UNSTABLE"
                script {testflag = "false"}
            }
        }
    }
        stage("Deploy to PRODUCTION") {
            when {
                    expression { gitTag =~ "([Vv].*)" && testflag == 'true' }
                }
            steps {
                echo "Closing previous container instances if exist:"
                echo "Deploying to production"
                sh "docker rm -f application-prod"
                echo "Deploying to PROD.."
                sh "docker run --rm --name application-prod -d -p 8082:80 $dockerImageName"
                echo "Deployment to the PROUDCTION has been successfully finished."
            }
        }
    }
            post{
                always{
                    echo "Finished the CICD, thank you."
                }
                unstable{
                    echo "Build was UNSTABLE: Skipped deployment to production because the tests failed."
                }
            }
}

