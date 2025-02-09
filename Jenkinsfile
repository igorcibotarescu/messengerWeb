pipeline {
    
    agent any
    
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('e4b38632-4d2c-4347-a8f1-4b2d4db212e4')
        IMAGE_NAME = "igorcibotarescu/test-java-app"
        TAG = "latest"
    }
    
    stages {
        stage('Clean Workspace') {
            steps {
                script {
                    sh 'echo "All files and dirs before deleting"'
                    sh 'ls -lh -a'
                    // Delete the workspace before each build
                    deleteDir()
                    sh 'ls -lh -a'
                    sh 'echo "All files and dirs after deleting"'
                }
            }
        }
        
        stage('Clone repo') {
            steps {
                script {
                    sh 'echo "Cloning from git!"'
                    sh 'git --version'
                    sh 'git clone https://github.com/igorcibotarescu/messengerWeb.git'
                }
            }
        }
        stage('Build app') {
            steps {
                script {
                    sh 'echo "Start building the app..."'
                    dir('messengerWeb') {
                        sh 'ls'
                        sh 'mvn --version'
                        sh 'mvn clean install'
                    }
                }
            }
        }
        stage('Build and deploy image') {
            steps {
                script {
                    dir('messengerWeb') {
                        sh 'echo "Show all running docker containers"'
                        sh 'docker ps -a'
                        sh 'pwd'
                        sh 'ls -l'
                        sh 'docker ps -q -f "name=app-container" | xargs -r docker stop'
                        sh 'docker ps -aq -f "name=app-container" | xargs -r docker rm'
                        sh 'echo "Show all running docker containers after cleaning"'
                        sh 'docker ps -a'
                        sh 'chmod 644 Dockerfile'
                        sh 'echo "Start building the docker container"'
                        sh 'docker build --no-cache -f /home/ubuntu/jenkins-slave/workspace/test-pipeline/messengerWeb/Dockerfile -t ${IMAGE_NAME}:${TAG} .'
                        // Log in to Docker Hub using credentials
                        withCredentials([usernamePassword(credentialsId: 'e4b38632-4d2c-4347-a8f1-4b2d4db212e4', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                            sh "echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin"
                        }
                        sh "docker push ${IMAGE_NAME}:${TAG}"
                    }
                }
            }
        }
        stage('Run docker image') {
            steps {
                script {
                    sh 'docker run -d -p 8080:8080 --name app-container igorcibotarescu/test-java-app:latest'
                    sh "docker logout"
                }
            }
        }
    }
}
