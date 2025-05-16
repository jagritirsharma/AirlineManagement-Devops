pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'airline-management-system'
        DOCKER_TAG = "${BUILD_NUMBER}"
        EC2_HOST = 'your-ec2-host'  // Replace with your EC2 host
        EC2_USER = 'ec2-user'       // Replace with your EC2 user
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
                    
                    // Copy the image to EC2
                    sh "scp image.tar ${EC2_USER}@${EC2_HOST}:~/"
                    
                    // Load and run the image on EC2
                    sh """
                        ssh ${EC2_USER}@${EC2_HOST} '
                            docker load < image.tar
                            docker stop ${DOCKER_IMAGE} || true
                            docker rm ${DOCKER_IMAGE} || true
                            docker run -d --name ${DOCKER_IMAGE} -p 8080:8080 ${DOCKER_IMAGE}:${DOCKER_TAG}
                        '
                    """
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