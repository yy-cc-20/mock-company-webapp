pipeline {
    agent any

    stages {
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