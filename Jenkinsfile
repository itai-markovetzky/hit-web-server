pipeline {
    environment {
        registry = "itay71700/hit-web-server"
        registryCredential = 'itay71700'
        dockerImage = ''
        gitTag = null
        testPassFlag = true
    }
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'git', url: 'git@github.com:Neophyte96/hit-web-server.git'
                script {
                    gitTag = sh(returnStdout: true, script: "git tag --sort=-creatordate | head -n 1").trim()

                    if (gitTag) {
                        echo "The tag is : $gitTag"
                    } else {
                        echo "This build has no tag."
                    }
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    dockerImageName = "itay71700/hit-web-server:$BUILD_NUMBER"
                    dockerImage = docker.build(dockerImageName, "./webServer/")
                }
            }
        }
        stage('Push') {
            steps {
                script {
                    docker.withRegistry("", registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Deploy to QA') {
            steps {
                sh "docker run --rm --name application-qa -d -p  80:80 $dockerImageName"
                echo "Deployed to QA!"
            }
        }
        stage('Run Automation tests') {
            steps {
                catchError(message: "Tests has failed and therefore deployment to production is skipped", buildResult: "UNSTABLE", stageResult: "UNSTABLE") {
                    echo "Running the Tests!"
                    dir("automation")
                            {
                                sh "gradle clean test"
                            }
                }
                junit skipMarkingBuildUnstable: true, testResults: 'automation/build/test-results/test/TEST-webApplicationTests.xml'

            }
        post {
            unstable {
                echo "Some of the tests failed therefore we're skipping deployment to production"
                script {testPassFlag = false}
            }
        }
    }
        stage("Deploy to Production") {
            when {
                expression { gitTag =~ "([Vv].*)" && testPassFlag == true}
            }
            steps {
                echo "Deploying to production"
                sh "docker run --rm --name application-prod -d -p 8082:80 $dockerImageName"
                echo "Deployed to production! we're live and running!!"
            }
        }
    }
            post{
                always{
                    echo "Finished the CICD, thank you"
                }
                failure{
                    echo "Skipped deployment to production because the tests failed!!!"
                    dir("automation")
                            {
                                sh "docker rm -f application-qa"
                            }
                }
            }
}

