@Library('shared-library3@main') _

pipeline {
    agent any
    
    tools {
        maven 'mvn-3.9.6'
    }

    environment {
        DOCKER_REGISTRY = 'abdelhaqelattar2002'
        DOCKER_TAG = 'v1.0.0' 
        DOCKER_IMAGE_NAME = 'java-mvn-app' 
    }

    stages {
        stage('Checkout Code') {
            steps {
                script {
                    getcheckoutgroovy(branch: 'main', url: 'https://github.com/abdelhaqell/jenkins.git')
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    buildgroovy()
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    def userInput = input(
                        message: 'Do you want to push the Docker image to the registry?',
                        parameters: [booleanParam(name: 'CONFIRM_PUSH', defaultValue: true, description: 'Check to proceed')]
                    )
                    if (!userInput) {
                        error('Docker image push skipped by user.')
                    }
                }
                script {
                    pushdockergroovy(DOCKER_REGISTRY, DOCKER_IMAGE_NAME, DOCKER_TAG)
                }
            }
        }
    }
}
