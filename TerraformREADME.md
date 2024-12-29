# Terraform Configuration for CloudDevOpsProject

This directory contains the Terraform configuration files used to provision the infrastructure for the CloudDevOpsProject on AWS. Terraform enables Infrastructure as Code (IaC), allowing consistent and repeatable infrastructure deployment.

## Overview

The Terraform configuration in this project:

- Provisions a Virtual Private Cloud (VPC) with a public subnet, an Internet Gateway (IGW), a Route Table (RT), and a Route Table Association (RT Association).
- Deploys a Jenkins master instance and a Jenkins node in the public subnet.
- Makes use of variables, loops, and modules to ensure the code is reusable and dynamic.
- (Not included) Includes a CloudWatch module that creates alarms based on CPU utilization. These alarms notify an SNS topic, sending alerts via email.

## Directory Structure
terraform_files/
├── main.tf           # Main configuration file defining resources
├── provider.tf       # Provider configuration for AWS
├── variables.tf      # Input variables
├── modules/          # Modularized configurations for better reusability
│   ├── instances/    # EC2 instance definitions
│   │   ├── main.tf
│   │   ├── output.tf
│   │   ├── variable.tf
│   ├── vpc/          # VPC definitions
│   │   ├── main.tf
│   │   ├── output.tf
│   │   ├── variables.tf
│   ├── cloudwatch/   # CloudWatch alarm definitions
│       ├── main.tf
│       ├── output.tf
│       ├── variables.tf
```

## Prerequisites

1. Install [Terraform](https://learn.hashicorp.com/tutorials/terraform/install-cli):
   ```bash
   # Verify Terraform installation
   terraform version
   ```
2. Configure AWS CLI and authenticate using an IAM user with appropriate permissions:
   ```bash
   aws configure
   ```
3. Ensure the following Terraform version and AWS permissions:
   - **Terraform version**: >= 1.0.0
   - AWS Permissions:
     - EC2 Management
     - VPC Creation
     - IAM Role Creation

## Steps to Deploy Infrastructure

### 1. Initialize Terraform

Navigate to the `terraform_files` directory and initialize the Terraform configuration:
```bash
terraform init
```

### 2. Review the Plan

View the planned infrastructure changes before applying them:
```bash
terraform plan
```

### 3. Apply the Configuration

Provision the infrastructure by applying the configuration:
```bash
terraform apply
```
Confirm the prompt to proceed. This will create the defined resources in your AWS account.

### 4. Verify the Deployment

Check the output for details about the created resources, such as EC2 instance public IPs and VPC details.

### 5. Manage Resources

To destroy the infrastructure, run:
```bash
terraform destroy
```

## Variables

The `variables.tf` file defines input variables for the Terraform configuration. Key variables include:

| Variable Name     | Description                        | Default Value |
|-------------------|------------------------------------|---------------|
| `region`          | AWS Region for resource creation  | `us-east-1`   |
| `instance_type`   | EC2 instance type                 | `t2.micro`    |
| `key_name`        | Name of the SSH key pair          | `dev-key`     |

Modify the `terraform.tfvars` or pass variables directly when running Terraform commands to customize values.

## Modules

This project uses a modularized approach for reusability and scalability:

1. **VPC Module**:
   - Provisions the VPC, subnets, route tables, and gateways.
   - Located in `modules/vpc/`.

2. **Instances Module**:
   - Creates EC2 instances with specified configurations.
   - Located in `modules/instances/`.

3. **CloudWatch Module**:
   - Creates a cloudwatch alarm that tracks the instance's cpu utilization.
   - Notifies an SNS topic to send mail to all subscribers upon alarm trigger. 

## Notes

- Ensure proper IAM permissions for your AWS account before running Terraform.
- For troubleshooting, refer to the Terraform logs and AWS console.

## Resources

- [Terraform Documentation](https://www.terraform.io/docs/index.html)
- [AWS Provider Configuration](https://registry.terraform.io/providers/hashicorp/aws/latest/docs)
