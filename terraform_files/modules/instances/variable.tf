variable "ami" {
  description = "AMI ID for the instance"
  type        = string
}
variable "volume_size" {
  description = "volume size"
  type        = number
  default = 20
}
variable "instance_type" {
  description = "Type of instance to launch"
  type        = string
}

variable "public_subnet_ids" {
  description = "Public Subnet IDs where the instance will be launched"
  type        =list(string)
}

variable "key_name" {
  description = "Instance key name"
}
variable "vpc_id" {
  description = "VPC ID where the instance will be launched"
  type        = string
}

variable "project_name" {
  type =string
}

variable "nginx_sg" {
  type=list(string)
}