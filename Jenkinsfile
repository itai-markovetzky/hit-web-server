def gitTag = null
pipeline {
    environment {
        registry = "itay71700/hit-web-server"
        registryCredential = 'itay71700'
        dockerImage = ''
    }
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'git', url: 'git@github.com:Neophyte96/hit-web-server.git'
                script{
                gitTag = sh(returnStdout:  true, script: "git tag --sort=-creatordate | head -n 1").trim()

                if (gitTag != null)
                {
                echo "The tag is : $gitTag"
                }
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    dockerImageName = "itay71700/hit-web-server:$BUILD_NUMBER"
                    dockerImage = docker.build(dockerImageName,"./webServer/")
                }
            }
        }
        stage('Push') {
            steps {
                script {
                    docker.withRegistry( "", registryCredential ) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Deploy to QA'){
            steps {
                //bat "docker run -d -p 80:80"+"dockerImageName"
                bat "docker run --rm --name application-qa -d -p  80:80 $dockerImageName"
                echo "Running the tests!"
                }
        }
        stage('Run Automation tests'){
                    steps {
                    echo "Running the Tests!"
                    dir("automation")
                    {
                        bat "gradle clean test"
                    }
                    junit skipMarkingBuildUnstable: true, testResults: 'automation/build/test-results/test/TEST-webApplicationTests.xml'
                }
        }
    }
}