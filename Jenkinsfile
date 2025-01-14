pipeline {
    agent any

    stages {
        stage('Setup Node.js') {
            steps {
                sh '''
                    # Install Node.js and npm
                    curl -fsSL https://deb.nodesource.com/setup_16.x | bash -
                    apt-get update -y
                    apt-get install -y nodejs
                    node -v
                    npm -v
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