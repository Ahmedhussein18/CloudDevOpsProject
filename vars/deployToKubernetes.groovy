def call(envVars) {
    pipeline {
        agent any

        stages {
            stage('Update Deployment YAML') {
                steps {
                    script {
                        sh """
                        sed -i 's|<IMAGE_PLACEHOLDER>|${envVars.dockerUsername}/${envVars.imageName}:${envVars.buildNumber}|g' deployment.yaml
                        """
                    }
                }
            }

            stage('Deploy to Kubernetes') {
                steps {
                    withCredentials([file(credentialsId: envVars.kubeconfig, variable: 'KUBE_CONFIG')]) {
                        script {
                            sh '''
                            export KUBECONFIG=$KUBE_CONFIG
                            kubectl apply -f deployment.yaml
                            kubectl apply -f service.yaml
                            '''
                        }
                    }
                }
            }
        }
    }
}
