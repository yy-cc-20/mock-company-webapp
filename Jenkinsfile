pipeline {
 search-task
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Starting Build'
                sh './gradlew assemble'
            }
        }
        stage('Test') {
            steps {
                echo 'Starting Tests'
                sh './gradlew test'
            }
        }
    }
}
