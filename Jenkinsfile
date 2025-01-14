pipeline {
    agent any

    stages {
        stage('Setup Node') {
            steps {
                // Install the specified Node.js version using nvm
                sh '''
                . ~/.nvm/nvm.sh
                nvm install 16
                nvm use 16
                '''
            }
        }

        stage('Build') {
            steps {
                echo 'Starting Build'
                sh './gradlew assemble'
            }
        }

        stage('Unit Test') {
            steps {
                echo 'Starting Tests'
                sh './gradlew test'
            }
        }
    }

    post {
        failure {
            echo 'The pipeline failed'
        }
        success {
            echo 'The pipeline completed successfully'
        }
    }
}