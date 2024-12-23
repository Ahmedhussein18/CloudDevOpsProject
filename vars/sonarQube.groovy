def call(envVars) {
    pipeline {
        agent any

        stages {
            stage('Git Checkout') {
                steps {
                    echo 'Checking out repository...'
                    git branch: envVars.branch, url: envVars.repoUrl
                }
            }

            stage('Unit Test') {
                steps {
                    echo 'Running unit tests...'
                    dir('FinalProjectCode') {
                        sh '''
                        chmod +x ./gradlew
                        ./gradlew test
                        '''
                    }
                }
            }

            stage('SonarQube Test') {
                steps {
                    echo 'Running SonarQube analysis...'
                    script {
                        withSonarQubeEnv(envVars.sonarQubeServer) {
                            dir('FinalProjectCode') {
                                sh '''
                                ./gradlew clean build
                                ./gradlew sonarqube
                                '''
                            }
                        }
                    }
                }
            }
        }
    }
}
