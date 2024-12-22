resource "aws_instance" "nginx" {
  count         = length(var.public_subnet_ids)
  ami           = var.ami
  instance_type = var.instance_type
  subnet_id     =var.public_subnet_ids[count.index]
  security_groups = var.nginx_sg
  key_name = var.key_name
  root_block_device {
    volume_size = var.volume_size # Specify the size in GB
    volume_type = "gp3" # General-purpose SSD
  }
  tags = {
    Name = "${var.project_name}-nginx-${count.index + 1}"
  }
}


