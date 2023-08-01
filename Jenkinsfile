pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                bat './gradlew build -x test --no-daemon'
            }
        }
        stage('Unit Test') {
            steps {
                bat './gradlew test'
            }
        }

        stage('Run App') {
            steps {
                bat """set JENKINS_NODE_COOKIE=dontKillMe && start /min gradlew bootRun"""
            }
        }
    }
}