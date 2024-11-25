# Install k3s to a remote host via SSH(Ubuntu 20.04)
variable "host_ip" {
  type = string
}

variable "ssh_user" {
  type = string
}

variable "ssh_password" {
  type = string
}

resource "null_resource" "init_server" {
  connection {
    type     = "ssh"
    host     = var.host_ip
    user     = var.ssh_user
    password = var.ssh_password
  }

  provisioner "file" {
    source      = "setup.sh"
    destination = "/tmp/setup.sh"
  }

  provisioner "remote-exec" {
    inline = [
      "chmod +x /tmp/setup.sh", # Ensure the script is executable
      "sudo /tmp/setup.sh"      # Run the script
    ]
  }
}