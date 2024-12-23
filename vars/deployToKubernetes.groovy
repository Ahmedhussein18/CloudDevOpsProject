def call(Map envVars) {
    // Update Deployment YAML
    echo 'Updating deployment file...'
    sh """
    sed -i 's|<IMAGE_PLACEHOLDER>|${envVars.dockerUsername}/${envVars.imageName}:${envVars.buildNumber}|g' deployment.yaml
    """

    // Deploy to Kubernetes
    echo 'Deploying to Kubernetes...'
    withCredentials([file(credentialsId: envVars.kubeconfig, variable: 'KUBE_CONFIG')]) {
        sh '''
        export KUBECONFIG=$KUBE_CONFIG
        kubectl apply -f deployment.yaml
        kubectl apply -f service.yaml
        '''
    }
}
