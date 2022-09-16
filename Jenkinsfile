pipeline {
    environment {
        registry = "itay71700/hit-web-server"
        registryCredential = 'itay71700'
        dockerImage = ''
        gitTag = null
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
                //bat "docker run -d -p 80:80"+"dockerImageName"
                sh "docker run --rm --name application-qa -d -p  80:80 $dockerImageName"
                echo "Deployed to QA!"
            }
        }
        stage('Run Automation tests') {
            steps {
                echo "Running the Tests!"
                dir("automation")
                        {
                            sh "gradle clean test"
                        }
                junit skipMarkingBuildUnstable: true, testResults: 'automation/build/test-results/test/TEST-webApplicationTests.xml'
            }
        }
            stage("Deploy to Production") {
                when {
                    expression { gitTag =~ "([Vv].*)" }
                }
                steps {
                    echo "Deploying to production"
                    sh "docker run --rm --name application-prod -d -p  8000:8086 $dockerImageName"
                }
                }
            }
        }
