pipeline {
    agent any

    stages {
    stage('Stop Active Daemon') {
                steps {
                    bat 'gradlew --stop'
                }
            }
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

        stage('Run App') {
            steps {
                bat """gradlew bootRun"""
            }
        }
    }
}