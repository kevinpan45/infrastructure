# Install k3s to a remote host via SSH(Ubuntu 20.04)
resource "null_resource" "init_server" {
  connection {
    type     = "ssh"
    host     = "${host_ip}"
    user     = "${ssh_user}"
    password = "${ssh_password}"
  }
  
  provisioner "remote-exec" {
    inline = [
      "apt-get update",
      "apt-get install -y --no-install-recommends vim git zip unzip wget",
      "curl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun"
      "curl -sfL https://rancher-mirror.oss-cn-beijing.aliyuncs.com/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn sh -s - --docker"
    ]
  }
}