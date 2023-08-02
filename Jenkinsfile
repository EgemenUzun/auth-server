pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                bat './gradlew build -x test'
            }
        }
        stage('Unit Test') {
            steps {
                bat './gradlew test'
            }
        }
    stage('Stop Active Daemon') {
                steps {
                    bat 'gradlew --stop'
                }
            }
        stage('Run App') {
            steps {
                bat 'start gradlew bootRun'
            }
        }
    }
}