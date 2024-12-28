def call(Map envVars) {
    // Update Deployment YAML
    echo 'Updating deployment file...'
    sh """
    sed -i 's|image: .*|image:${envVars.dockerUsername}/${envVars.imageName}:${envVars.buildNumber}|g' deployment.yaml
    """


}
