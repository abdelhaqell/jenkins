pipeline {
    agent any
    
    tools {
        maven 'mvn-3.9.6'
    }

    environment {
        // Docker Hub username :
        DOCKER_REGISTRY = 'abdelhaqelattar2002'
        // Docker image tag :
        DOCKER_TAG = 'v1.0.0' 
        // Docker image name :
        DOCKER_IMAGE_NAME = 'java-mvn-app' 
    }

    stages {
        stage('Checkout Code') {
            steps {
                getcheckoutgroovy(
                    branch: 'main',
                    url:'https://github.com/abdelhaqell/jenkins.git'
                )
            }
        }

        stage('User Confirmation') {
            steps {
                script {
                    def userInput = input(
                        message: 'Do you want to proceed with the build?',
                        parameters: [
                            choice(name: 'PROCEED', choices: ['Yes', 'No'], description: 'Select Yes to continue')
                        ]
                    )

                    if (userInput == 'No') {
                        error('Build stopped by user.')
                    }
                }
            }
        }

        stage('Build') {
            steps {
                sh '''
                   cd $WORKSPACE
                   pwd
                   ls 
                   mvn clean install
                '''
            }
        }

        stage('Generate Artifact') {
            steps {
                sh '''
                   cd $WORKSPACE
                   mvn package
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def userInput = input(
                        message: 'Do you want to build the Docker image?',
                        parameters: [booleanParam(name: 'CONFIRM_BUILD', defaultValue: true, description: 'Check to proceed')]
                    )
                    if (!userInput) {
                        error('Docker image build skipped by user.')
                    }
                }
                sh '''
                   cd $WORKSPACE
                   docker build -t ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_TAG} .
                '''
            }
        }

        stage('Login to Docker Registry') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'id3', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh "echo ${DOCKER_PASS} | docker login -u ${DOCKER_USER} --password-stdin"
                    }
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
                sh "docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_TAG}"
            }
        }

        stage('Cleanup') {
            steps {
                sh "docker rmi ${DOCKER_REGISTRY}/${DOCKER_IMAGE_NAME}:${DOCKER_TAG}"
            }
        }
    }
}
