pipeline {
    agent any

    environment {
        SONARQUBE_SERVER = 'SonarQube'
        DOCKER_IMAGE_NAME = 'test-image'
        DOCKERHUB_USERNAME= 'ahmedhussein18'
        REGISTRY_CREDENTIALS = 'dockerhub'
        KUBECONFIG = 'kubeconfig'
    }

    stages {
        stage('Git Checkout') {
            steps {
                echo 'Checking out repository...'
                git branch: 'main', url: 'https://github.com/ahmedhussein18/CloudDevopsProject.git'
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

        stage('Build JAR') {
            steps {
                echo 'Building JAR file...'
                dir('FinalProjectCode') {
                sh './gradlew build'
                }
            }
        }

        stage('SonarQube Test') {
            steps {
                echo 'Running SonarQube analysis...'
                script {
                    withSonarQubeEnv(SONARQUBE_SERVER) {
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

        stage('Build Image') {
            steps {
                echo 'Building Docker image...'
                script {
                    sh '''
                    docker build -t ${DOCKERHUB_USERNAME}/${DOCKER_IMAGE_NAME}:${BUILD_NUMBER} .
                    '''
                }
            }
        }

        stage('Push Image into Registry') {
            steps {
                echo 'Pushing Docker image to registry...'
                script {
                    withCredentials([usernamePassword(credentialsId: REGISTRY_CREDENTIALS, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh """
                        echo "${DOCKER_PASSWORD}" | docker login -u "${DOCKER_USERNAME}" --password-stdin
                        docker push ${DOCKERHUB_USERNAME}/${DOCKER_IMAGE_NAME}:${BUILD_NUMBER}
                        """
                    }
                }
            }
        }
        stage('Ensure Deployment YAML Exists') {
            steps {
                script {
                    if (!fileExists('deployment.yaml')) {
                        sh "kubectl create deployment ivolve --image=${DOCKERHUB_USERNAME}/${DOCKER_IMAGE_NAME}:${BUILD_NUMBER} --replicas=3 --dry-run=client -o yaml > deployment.yaml"
                        sh "kubectl expose deployment ivolve --type=NodePort --port=8081"
                    }
                }
            }
        }

        stage('Update Deployment YAML') {
            steps {
                script {
                    sh "sed -i 's|<IMAGE_PLACEHOLDER>|${DOCKERHUB_USERNAME}/${DOCKER_IMAGE_NAME}:${BUILD_NUMBER}|g' deployment.yaml"
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBE_CONFIG')]) {
                    script {
                        sh '''
                        export KUBECONFIG=$KUBE_CONFIG
                        kubectl apply -f ${KUBE_DEPLOY_FILE}
                        '''
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check logs for details.'
        }
    }
}
