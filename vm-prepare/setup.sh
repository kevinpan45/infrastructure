# System software
apt-get update && export DEBIAN_FRONTEND=noninteractive &&
    apt-get -y -qq install wget net-tools zip unzip vim iftop iotop curl lsb-core

# Install Cockpit
apt-get install -y cockpit

# Install Docker
curl -fsSL https://get.docker.com | bash -s docker

# Install k3d and create k3s cluster
curl -s https://raw.githubusercontent.com/k3d-io/k3d/main/install.sh | bash
k3d cluster create local-stack

# Install HashiCorp Products
apt-get install gpg coreutils &&
    wget -O- https://apt.releases.hashicorp.com/gpg | gpg --dearmor -o /usr/share/keyrings/hashicorp-archive-keyring.gpg &&
    gpg --no-default-keyring --keyring /usr/share/keyrings/hashicorp-archive-keyring.gpg --fingerprint &&
    curl -fsSL https://apt.releases.hashicorp.com/gpg | apt-key add - &&
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/hashicorp-archive-keyring.gpg] https://apt.releases.hashicorp.com $(lsb_release -cs) main" | tee /etc/apt/sources.list.d/hashicorp.list &&
    apt update &&
    apt-get install nomad
