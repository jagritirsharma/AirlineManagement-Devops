pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'airline-management-system'
        DOCKER_TAG = "${BUILD_NUMBER}"
        EC2_HOST = 'ec2-13-203-214-221.ap-south-1.compute.amazonaws.com'
        EC2_CREDENTIALS = 'ec2-ssh-key-id'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            agent {
                docker {
                    image 'maven:3.8.4-openjdk-17-slim'
                    reuseNode true
                }
            }
            steps {
                sh 'mvn clean package'
            }
        }
        
        stage('Test') {
            agent {
                docker {
                    image 'maven:3.8.4-openjdk-17-slim'
                    reuseNode true
                }
            }
            steps {
                sh 'mvn test'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                }
            }
        }
        
        stage('Deploy to EC2') {
            steps {
                script {
                    // Save the image to a tar file
                    sh "docker save ${DOCKER_IMAGE}:${DOCKER_TAG} > image.tar"
                    
                    // Using the configured SSH credentials
                    sshagent([EC2_CREDENTIALS]) {
                        // Copy the image to EC2
                        sh "scp -o StrictHostKeyChecking=no image.tar ec2-user@${EC2_HOST}:~/"
                        
                        // Load and run the image on EC2
                        sh """
                            ssh -o StrictHostKeyChecking=no ec2-user@${EC2_HOST} '
                                docker load < image.tar
                                docker stop ${DOCKER_IMAGE} || true
                                docker rm ${DOCKER_IMAGE} || true
                                docker run -d --name ${DOCKER_IMAGE} -p 8080:8080 ${DOCKER_IMAGE}:${DOCKER_TAG}
                                rm image.tar
                            '
                        """
                    }
                    
                    // Clean up local tar file
                    sh 'rm image.tar'
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