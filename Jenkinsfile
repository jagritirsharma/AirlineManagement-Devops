pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'airline-management-system'
        DOCKER_TAG = "${BUILD_NUMBER}"
        EC2_HOST = 'ec2-13-203-214-221.ap-south-1.compute.amazonaws.com'
        MAVEN_HOME = tool 'Maven'
    }
    
    tools {
        maven 'Maven'
        jdk 'JDK17'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }
        
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        
        stage('Verify Docker') {
            steps {
                script {
                    // Using full paths to ensure Docker commands work
                    bat '"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" --version'
                    bat '"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" info'
                    bat '"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe" run hello-world'
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    bat "\"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe\" build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                }
            }
        }
        
        stage('Deploy to EC2') {
            steps {
                script {
                    // Save the image to a tar file
                    bat "\"C:\\Program Files\\Docker\\Docker\\resources\\bin\\docker.exe\" save ${DOCKER_IMAGE}:${DOCKER_TAG} > image.tar"
                    
                    // Using Publish Over SSH plugin
                    sshPublisher(
                        publishers: [
                            sshPublisherDesc(
                                configName: 'EC2-Instance',
                                transfers: [
                                    sshTransfer(
                                        sourceFiles: 'image.tar',
                                        removePrefix: '',
                                        remoteDirectory: '',
                                        execCommand: """
                                            docker load < image.tar
                                            docker stop ${DOCKER_IMAGE} || true
                                            docker rm ${DOCKER_IMAGE} || true
                                            docker run -d --name ${DOCKER_IMAGE} -p 8080:8080 ${DOCKER_IMAGE}:${DOCKER_TAG}
                                            rm image.tar
                                        """
                                    )
                                ]
                            )
                        ]
                    )
                    
                    // Clean up local tar file
                    bat "del image.tar"
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
    }
} 