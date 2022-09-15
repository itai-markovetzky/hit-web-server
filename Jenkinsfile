pipeline {
    environment {
        registry = "itay71700/hit-web-server"
        registryCredential = 'itay71700'
        dockerImage = ''
    }
    agent any
    stages {
        //stage('Checkout') {
           // steps {
              //  git branch: 'main', credentialsId: 'git', url: 'git@github.com:Neophyte96/hit-web-server.git'
           // }
        //}
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
                dir("automation")
                {
                    bat "gradle clean test"
                }
        }
    }
}
}