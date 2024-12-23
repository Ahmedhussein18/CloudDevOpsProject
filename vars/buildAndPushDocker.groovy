def call(envVars) {
    pipeline {
        agent any

        stages {
            stage('Build Image') {
                steps {
                    echo 'Building Docker image...'
                    script {
                        sh """
                        docker build -t ${envVars.dockerUsername}/${envVars.imageName}:${envVars.buildNumber} .
                        """
                    }
                }
            }

            stage('Push Image into Registry') {
                steps {
                    echo 'Pushing Docker image to registry...'
                    script {
                        withCredentials([usernamePassword(credentialsId: envVars.registryCredentials, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                            sh """
                            echo "${DOCKER_PASSWORD}" | docker login -u "${DOCKER_USERNAME}" --password-stdin
                            docker push ${envVars.dockerUsername}/${envVars.imageName}:${envVars.buildNumber}
                            """
                        }
                    }
                }
            }
        }
    }
}
