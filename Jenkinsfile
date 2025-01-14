pipeline {
    agent any

    stages {
        stage('Setup Node.js with NVM') {
            steps {
                echo 'Starting Build'
                sh '''
                    # Reinitialize NVM
                    export NVM_DIR="$HOME/.nvm"
                    [ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"

                    # Use Node.js version installed via NVM
                    nvm use 16

                    # Run Gradle build
                    ./gradlew assemble
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