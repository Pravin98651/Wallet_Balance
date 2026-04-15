pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK 11'
    }

    environment {
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials'
        DOCKER_IMAGE = 'pravin98651/wallet-balance'
        DOCKER_TAG = "v${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    if (env.GIT_URL) {
                        git branch: 'main', url: env.GIT_URL
                    } else {
                        checkout scm
                    }
                }
            }
        }

        stage('Build & Test') {
            steps {
                dir('Wallet_Balance') {
                    bat 'mvn clean test'
                }
            }
        }

        stage('Package') {
            steps {
                dir('Wallet_Balance') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                dir('Wallet_Balance') {
                    script {
                        docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS_ID}") {
                            def app = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                            app.push()
                            app.push("latest")
                        }
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                dir('Wallet_Balance') {
                    script {
                        bat "kubectl set image deployment/wallet-balance-app wallet-balance=${DOCKER_IMAGE}:${DOCKER_TAG}"
                    }
                }
            }
        }
    }
}
