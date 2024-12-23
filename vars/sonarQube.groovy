def call(Map envVars) {
    // Git Checkout
    echo 'Checking out repository...'
    git branch: envVars.branch, url: envVars.repoUrl

    // Unit Test
    echo 'Running unit tests...'
    dir('FinalProjectCode') {
        sh '''
        chmod +x ./gradlew
        ./gradlew test
        '''
    }

    // SonarQube Test
    echo 'Running SonarQube analysis...'
    withSonarQubeEnv(envVars.sonarQubeServer) {
        dir('FinalProjectCode') {
            sh '''
            ./gradlew clean build
            ./gradlew sonarqube
            '''
        }
    }
}
