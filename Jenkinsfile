@Library('shared-library') _
pipeline {
    agent any

    environment {
        SONARQUBE_SERVER = 'SonarQube'
        DOCKER_IMAGE_NAME = 'test-image'
        DOCKERHUB_USERNAME = 'ahmedhussein18'
        REGISTRY_CREDENTIALS = 'dockerhub'
        KUBECONFIG = 'kubeconfig'
        REPO_URL = 'https://github.com/ahmedhussein18/CloudDevopsProject.git'
        BRANCH = 'main'
        BUILD_NUMBER = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('SonarQube') {
            steps {
                script {
                    sonarQube([
                        branch: BRANCH,
                        repoUrl: REPO_URL,
                        sonarQubeServer: SONARQUBE_SERVER
                    ])
                }
            }
        }

        stage('Build and Push Docker') {
            steps {
                script {
                    buildAndPushDocker([
                        dockerUsername: DOCKERHUB_USERNAME,
                        imageName: DOCKER_IMAGE_NAME,
                        buildNumber: BUILD_NUMBER,
                        registryCredentials: REGISTRY_CREDENTIALS
                    ])
                }
            }
        }

