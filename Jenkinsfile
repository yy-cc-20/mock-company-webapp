pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Starting Build'
                script {
                    def exitCode = sh(script: './gradlew assemble', returnStatus: true)
                    if (exitCode != 0) {
                        error "Build failed with exit code ${exitCode}"
                    }
                }
            }
        }

        stage('Unit Test') {
            steps {
                echo 'Starting Tests'
                script {
                    def exitCode = sh(script: './gradlew test', returnStatus: true)
                    if (exitCode != 0) {
                        error "Unit tests failed with exit code ${exitCode}"
                    }
                }
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