pipeline {
    agent any

    stages {
        stage('Setup Node.js with NVM') {
            steps {
                sh '''
                    # Install NVM
                    curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.5/install.sh | bash

                    # Load NVM (ensure it matches your shell environment)
                    export NVM_DIR="$HOME/.nvm"
                    [ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"

                    nvm install 16
                    nvm use 16

                    # Verify installation
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