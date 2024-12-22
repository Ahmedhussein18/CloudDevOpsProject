### Lab 11: Ansible Dynamic Inventories

#### Objective
Set up Ansible dynamic inventories to automatically discover and manage AWS infrastructure. Use Ansible Galaxy role to install Apache.

---

### Prerequisites

1. Ensure Ansible is installed on the control node:
   ```bash
   sudo apt install ansible -y  # For Ubuntu/Debian
   ```

2. Verify that Python dependencies for AWS are installed:
   ```bash
   pip install boto3 botocore
   ```

3. Install the `geerlingguy.apache` role from Ansible Galaxy:
   ```bash
   ansible-galaxy install geerlingguy.apache
   ```

4. Ensure the following files are present:
   - `aws_ec2.yml`: Configures the dynamic inventory for AWS EC2.
   - `ansible.cfg`: Points to the dynamic inventory file and the SSH private key.
   - `playbook.yml`: Contains tasks for configuring Apache using the `geerlingguy.apache` role.

---

### Steps

1. Verify the Dynamic Inventory
   Test the AWS EC2 dynamic inventory file:
   ```bash
   ansible-inventory -i aws_ec2.yml --graph
   ```
   This command should display a graph of your AWS instances grouped by tags or other attributes.

2. Run the Playbook
   Execute the playbook to configure Apache on the discovered AWS instances:
   ```bash
   ansible-playbook -i aws_ec2.yml playbook.yml
   ```

---

### Verification

1. Verify Apache Installation
   Use Ansible ad-hoc commands to verify that Apache is installed and running:
   ```bash
   ansible all -i aws_ec2.yml -m command -a "systemctl status apache2"  # For Ubuntu
   ```

2. Access the Apache Web Server
   - Navigate to the public IP address of one of the AWS EC2 instances in your browser.
   - You should see the default Apache web page.

---

### Notes

- **Dynamic Inventory**: Automatically discovers EC2 instances using the `aws_ec2` plugin in the `aws_ec2.yml` file.
- **SSH Configuration**: Ensure the `path_to_key` is correctly set in `ansible.cfg` to use the SSH key for connecting to AWS instances.
- **Reusable Roles**: The `geerlingguy.apache` role simplifies Apache installation and configuration.


