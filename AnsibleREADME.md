# Ansible Configuration for CloudDevOpsProject

This directory contains the Ansible playbooks and configurations used to manage and configure the infrastructure for the CloudDevOpsProject. Ansible automates the configuration of instances, ensuring consistency and efficiency.

## Overview

The Ansible configuration in this project:

- Utilizes dynamic inventories to group instances into two categories:
  - **Jenkins Master**
  - **Jenkins Node**
- Automates the installation of required software and tools:
  - **Jenkins Master**:
    - Jenkins
    - SonarQube
  - **Jenkins Node**:
    - Java
    - Docker
- Ensures idempotency and reusability using roles and tasks.

## Directory Structure

```
ansible_files/
├── ansible.cfg               # Ansible configuration file
├── aws_ec2.yml               # Dynamic inventory script for AWS
├── playbook.yml              # Main playbook to run all tasks
├── roles/                    # Roles for modularized configurations
│   ├── docker/               # Role to install Docker
│   │   ├── tasks/
│   │       ├── main.yml
│   ├── jenkins/              # Role to install Jenkins
│   │   ├── tasks/
│   │       ├── main.yml
│   ├── sonarqube/            # Role to install SonarQube
│       ├── tasks/
│       ├── main.yml
│       ├── templates/
│           ├── sonar.properties.j2
```

## Prerequisites

1. Install [Ansible](https://docs.ansible.com/ansible/latest/installation_guide/intro_installation.html):
   ```bash
   # Verify Ansible installation
   ansible --version
   ```
2. Ensure AWS CLI is installed and configured with an IAM user that has the required permissions:
   ```bash
   aws configure
   ```
3. Install `boto3` and `botocore` for dynamic inventory management:
   ```bash
   pip install boto3 botocore
   ```
4. Ensure your SSH key is configured for Ansible to connect to your instances.

## Steps to Run the Playbook

### 1. Configure the Inventory

The `aws_ec2.yml` file is used as a dynamic inventory script to group instances:
- Jenkins Master
- Jenkins Node

### 2. Run the Playbook

Execute the main playbook to configure the instances:
```bash
ansible-playbook -i aws_ec2.yml playbook.yml
```

### 3. Verify Configuration

- For Jenkins Master:
  - Jenkins should be accessible via the web interface at \`http://<jenkins-master-ip>:8080\`.
  - SonarQube should be running and accessible via the web interface.
- For Jenkins Node:
  - Docker should be installed and operational.

## Roles and Tasks

### Jenkins Master

- **Tasks**:
  - Install Jenkins.
  - Install and configure SonarQube.
- **Key Files**:
  - `roles/jenkins/tasks/main.yml`
  - `roles/sonarqube/tasks/main.yml`

### Jenkins Node

- **Tasks**:
  - Install Docker.
- **Key Files**:
  - `roles/docker/tasks/main.yml`

## Notes

- Ensure that the IAM role or user used for Ansible has permissions to access EC2 instances and perform the required tasks.
- Dynamic inventories require proper tagging of instances in AWS to group them correctly.
- For troubleshooting, check Ansible logs and instance logs for detailed error messages.

## Resources

- [Ansible Documentation](https://docs.ansible.com/ansible/latest/index.html)
- [Dynamic Inventory Guide](https://docs.ansible.com/ansible/latest/collections/amazon/aws/aws_ec2_inventory.html)
