pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'airline-management-system'
        DOCKER_TAG = "${BUILD_NUMBER}"
        EC2_HOST = 'ec2-13-203-214-221.ap-south-1.compute.amazonaws.com'
        EC2_CREDENTIALS = 'ec2-ssh-key-id'
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
        
        stage('Build Docker Image') {
            steps {
                script {
                    bat "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                }
            }
        }
        
        stage('Deploy to EC2') {
            steps {
                script {
                    // Save the image to a tar file
                    bat "docker save ${DOCKER_IMAGE}:${DOCKER_TAG} > image.tar"
                    
                    // Using the configured SSH credentials
                    sshagent([EC2_CREDENTIALS]) {
                        // Copy the image to EC2
                        bat "scp -o StrictHostKeyChecking=no image.tar ec2-user@${EC2_HOST}:~/"
                        
                        // Load and run the image on EC2
                        bat """
                            ssh -o StrictHostKeyChecking=no ec2-user@${EC2_HOST} "
                                docker load < image.tar &&
                                docker stop ${DOCKER_IMAGE} || true &&
                                docker rm ${DOCKER_IMAGE} || true &&
                                docker run -d --name ${DOCKER_IMAGE} -p 8080:8080 ${DOCKER_IMAGE}:${DOCKER_TAG} &&
                                rm image.tar
                            "
                        """
                    }
                    
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