# System software
apt-get update && export DEBIAN_FRONTEND=noninteractive &&
    apt-get -y -qq install wget net-tools zip unzip vim iftop iotop curl lsb-core

# Install Cockpit
apt-get install -y cockpit

# Install Docker
curl -fsSL https://get.docker.com | bash -s docker

# Install Helm
curl https://baltocdn.com/helm/signing.asc | apt-key add - &&
    apt-get install apt-transport-https --yes &&
    echo "deb https://baltocdn.com/helm/stable/debian/ all main" | tee /etc/apt/sources.list.d/helm-stable-debian.list &&
    apt-get update &&
    apt-get install helm

# Install Kubectl
curl -LO https://storage.googleapis.com/kubernetes-release/release/v1.22.4/bin/linux/amd64/kubectl &&
    chmod +x ./kubectl &&
    mv ./kubectl /usr/local/bin/kubectl

# Install HashiCorp Products
RUN apt-get install gpg coreutils &&
    wget -O- https://apt.releases.hashicorp.com/gpg | gpg --dearmor -o /usr/share/keyrings/hashicorp-archive-keyring.gpg &&
    gpg --no-default-keyring --keyring /usr/share/keyrings/hashicorp-archive-keyring.gpg --fingerprint &&
    curl -fsSL https://apt.releases.hashicorp.com/gpg | apt-key add - &&
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/hashicorp-archive-keyring.gpg] https://apt.releases.hashicorp.com $(lsb_release -cs) main" | tee /etc/apt/sources.list.d/hashicorp.list &&
    apt update &&
    apt-get install nomad
