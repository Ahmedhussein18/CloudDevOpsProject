variable "project_name" {
  description = "Name prefix for all resources"
  type        = string
  default     = "final-project"
}

variable "vpc_cidr" {
  description = "CIDR block for the VPC"
  type        = string
  default     = "10.0.0.0/16"
}

variable "public_subnet_cidrs" {
  description = "List of CIDR blocks for public subnets"
  type        = list(string)
  default     = ["10.0.1.0/24"]
}

variable "availability_zones" {
  description = "List of availability zones to use"
  type        = list(string)
  default     = ["us-east-1a"]
}

variable "instance_type" {
  description = "Instance type for both Nginx and Httpd instances"
  type        = string
  default     = "t3.xlarge"
}

variable "key_name" {
  description = "SSH key name"
  default = "marzouk-key"
}


