# CloudDevOpsProject  

This project automates the deployment of a CI/CD pipeline for a Java application using various DevOps tools. It leverages Terraform, Ansible, Jenkins, SonarQube, Docker, Minikube, and ArgoCD to create a fully functional and automated workflow.  

## Project Overview  

1. **Infrastructure as Code**: Terraform provisions the AWS infrastructure, including a VPC, instances, and networking components.  
2. **Configuration Management**: Ansible configures the provisioned instances, installing required software and tools.  
3. **CI/CD Pipeline**: Jenkins orchestrates the pipeline to build, test, analyze, containerize, and deploy the application.  
4. **Kubernetes Deployment**: ArgoCD automates deployment on a Minikube Kubernetes cluster by monitoring repository changes.  

## Steps to Use the Project  

### Prerequisites  
1. Install the following tools:  
   - [Ansible](https://docs.ansible.com/ansible/latest/installation_guide/intro_installation.html)  
   - [Terraform](https://learn.hashicorp.com/tutorials/terraform/install-cli)  
   - [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2.html)  
   - [Boto3](https://boto3.amazonaws.com/v1/documentation/api/latest/guide/quickstart.html)  

2. Configure AWS CLI to log in with an IAM user that has the required privileges:  
   ```bash  
   aws configure  
   ```  

### Setup  

#### 1. Clone the Repository  
   ```bash  
   git clone https://github.com/ahmedhussein18/CloudDevOpsProject.git  
   cd CloudDevOpsProject  
   ```  

#### 2. Provision Infrastructure with Terraform  
   ```bash  
   cd terraform_files  
   terraform init  
   terraform plan  
   terraform apply  
   ```  

#### 3. Configure Instances with Ansible  
   ```bash  
   cd ../ansible_files  
   ansible-playbook -i aws_ec2.yml playbook.yml  
   ```  
   Wait for all tasks to complete.  

#### 4. Access Jenkins Master  
   1. SSH into the Jenkins Master instance:  
      ```bash  
      ssh -i <your-key.pem> ec2-user@<jenkins-master-ip>  
      ```  
   2. Retrieve the Jenkins Master password:  
      ```bash  
      sudo cat /var/lib/jenkins/secrets/initialAdminPassword  
      ```  
   3. Access Jenkins in your browser:  
      ```  
      http://<jenkins-master-ip>:8080  
      ```  
   4. Complete the Jenkins setup wizard.  

#### 5. Configure Jenkins  
   1. Install the **SonarQube Scanner** plugin in Jenkins.  
   2. Add credentials in Jenkins for:  
      - GitHub  
      - DockerHub  
   3. Configure Jenkins to use the shared library:  
      - Repository: `https://github.com/ahmedhussein18/CloudDevOpsProject.git`  
      - Path: `vars`  
      - Name: `shared-library`  
   4. Generate a SonarQube token and configure the SonarQube plugin.  

#### 6. Set Up Minikube, kubectl, and ArgoCD  

##### Install Minikube  
SSH into the Jenkins Node and install Minikube:  
```bash  
# Update system and install dependencies  
sudo apt-get update && sudo apt-get install -y apt-transport-https ca-certificates curl software-properties-common  

# Download and install Minikube  
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64  
sudo install minikube-linux-amd64 /usr/local/bin/minikube  

# Verify installation  
minikube version  
```  

##### Install kubectl  
Install kubectl on the same instance:  
```bash  
# Download kubectl binary  
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"  

# Install kubectl  
chmod +x kubectl  
sudo mv kubectl /usr/local/bin/  

# Verify installation  
kubectl version --client  
```  

##### Start Minikube  
Start a Minikube cluster:  
```bash  
minikube start --driver=docker  
```  

##### Install and Configure ArgoCD  
  
1. Create the `argocd` namespace and install ArgoCD in the Minikube cluster:  
   ```bash  
   kubectl create namespace argocd  
   kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml  
   ```  

2. Expose the ArgoCD server:  
   ```bash  
   kubectl port-forward svc/argocd-server -n argocd 8080:443 &  
   ```  

##### Create the ArgoCD Application  
1. Create a file named `application.yaml` with the following content:  
   ```yaml  
   apiVersion: argoproj.io/v1alpha1  
   kind: Application  
   metadata:  
     name: java-app  
     namespace: argocd  
   spec:  
     project: default  
     source:  
       repoURL: https://github.com/ahmedhussein18/CloudDevOpsProject.git  
       targetRevision: HEAD  
       path: .  
     destination:  
       server: https://kubernetes.default.svc  
       namespace: default  
     syncPolicy:  
       automated:  
         prune: true  
         selfHeal: true  
   ```
2. ```
   kubectl apply -f application.yaml
   ```  
## Notes  
- Ensure your AWS environment is properly configured with sufficient permissions.  
- For troubleshooting, refer to logs and documentation for the tools used.  

