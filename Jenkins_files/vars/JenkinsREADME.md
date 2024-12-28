# Jenkins Configuration for CloudDevOpsProject

This document outlines the setup and configuration of Jenkins for the CloudDevOpsProject. Jenkins orchestrates the CI/CD pipeline to automate build, test, analysis, and deployment of the Java application.

## Overview

Jenkins is deployed in this project to:

- Automate the CI/CD pipeline.
- Integrate with tools such as Docker, SonarQube, and Kubernetes.
- Trigger builds based on code changes in the repository.
- Edit the deployment.yaml and pushed it for ArgoCD integration.

## Directory Structure

```
jenkins_files/
├── Jenkinsfile               # Defines the CI/CD pipeline
├── vars/                     # Shared library scripts
│   ├── buildAndPushDocker.groovy   # Build and push Docker image
│   ├── deployToKubernetes.groovy   # Deploy application to Kubernetes
│   ├── sonarQube.groovy            # SonarQube integration
```

## Prerequisites

1. **Jenkins Installation**:
   Jenkins is installed on the Jenkins Master using Ansible. For installation details, refer to the Ansible configuration README.

2. **Required Plugins**:
   Ensure the following Jenkins plugins are installed:
   - Docker Pipeline
   - SonarQube Scanner
   - Kubernetes
   - Git

3. **Credentials**:
   Configure Jenkins with the following credentials:
   - **GitHub Credentials**: For accessing the repository.
   - **DockerHub Credentials**: For pushing Docker images.
   - **SonarQube Token**: For static code analysis.

## Steps to Configure Jenkins

### 1. Access Jenkins

1. SSH into the Jenkins Master instance:
   ```bash
   ssh -i <your-key.pem> ec2-user@<jenkins-master-ip>
   ```
2. Retrieve the Jenkins initial admin password:
   ```bash
   sudo cat /var/lib/jenkins/secrets/initialAdminPassword
   ```
3. Access Jenkins in your browser:
   `http://<jenkins-master-ip>:8080`
4. Complete the Jenkins setup wizard.

### 2. Configure Tools

1. **Add Global Tools**:
   - Java
   - Docker
   - SonarQube
2. **Integrate SonarQube**:
   - Navigate to **Manage Jenkins > Configure System**.
   - Add the SonarQube server URL and authentication token.

### 3. Set Up Shared Library

1. Add a shared library pointing to the project repository:
   - Repository: `https://github.com/ahmedhussein18/CloudDevOpsProject.git`
   - Path: `vars`
   - Name: `shared-library`

### 4. Configure the Pipeline

Create a new Jenkins pipeline and link it to the project repository:

1. Navigate to **New Item > Pipeline**.
2. Add the repository URL under the **Pipeline Script from SCM** section.
3. Specify the branch and path to the Jenkinsfile.

### 5. Trigger Builds

- **Automatic Builds**:
  Configure Jenkins to trigger builds automatically based on code changes in the repository.
- **Manual Builds**:
  Run builds manually from the Jenkins dashboard.

## Pipeline Stages

The CI/CD pipeline defined in the Jenkinsfile includes the following stages:

1. **Checkout Code**:
   - Pull the latest code from the GitHub repository.
2. **Build and Test**:
   - Compile the Java application and run tests.
3. **Code Analysis**:
   - Perform static code analysis using SonarQube.
4. **Docker Build and Push**:
   - Build a Docker image for the application and push it to DockerHub.
5. **Pushing Deployment.yaml**:
   - Edits the Deployment.yaml file and pushes it to the repo.

## Notes

- Ensure the Jenkins server has sufficient resources to handle builds and deployments.
- For troubleshooting, refer to Jenkins logs and the pipeline console output.

## Resources

- [Jenkins Documentation](https://www.jenkins.io/doc/)
- [Jenkins Pipeline Syntax](https://www.jenkins.io/doc/book/pipeline/syntax/)
- [SonarQube Integration Guide](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-jenkins/)

