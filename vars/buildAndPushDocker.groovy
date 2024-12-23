def call(Map envVars) {
    // Build Docker Image
    echo 'Building Docker image...'
    sh """
    docker build -t ${envVars.dockerUsername}/${envVars.imageName}:${envVars.buildNumber} .
    """

    // Push Docker Image
    echo 'Pushing Docker image to registry...'
    withCredentials([usernamePassword(credentialsId: envVars.registryCredentials, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
        sh """
        echo "${DOCKER_PASSWORD}" | docker login -u "${DOCKER_USERNAME}" --password-stdin
        docker push ${envVars.dockerUsername}/${envVars.imageName}:${envVars.buildNumber}
        """
    }
}
