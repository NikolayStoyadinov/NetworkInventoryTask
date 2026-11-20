
pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                dir('ci') {
                    sh 'podman --remote compose up --build --abort-on-container-exit'
                }
            }
        }
    }

    post {
        always {
            dir('ci') {
                sh 'podman --remote compose down --volumes || true'
            }
        }
    }
}
