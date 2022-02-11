# Install k3s with tls san, replace <Cloud VM Public IP> to your real IP
curl -sfL http://rancher-mirror.cnrancher.com/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn INSTALL_K3S_EXEC="--tls-san <Cloud VM Public IP>" sh -

# Install Kubernetes Dashboard UI, get service kubernetes-dashboard port after command 'kubectl get svc -n kubernetes-dashboard' executed.
# see https://blog.csdn.net/kaikai0720/article/details/107011937
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.3/aio/deploy/recommended.yaml
kubectl get pods -n kubernetes-dashboard
kubectl get svc -n kubernetes-dashboard
kubectl patch svc kubernetes-dashboard -p '{"spec":{"type":"NodePort"}}' -n kubernetes-dashboard
kubectl get svc -n kubernetes-dashboard

# Install Helm
curl https://baltocdn.com/helm/signing.asc | sudo apt-key add -
sudo apt-get install apt-transport-https --yes
echo "deb https://baltocdn.com/helm/stable/debian/ all main" | sudo tee /etc/apt/sources.list.d/helm-stable-debian.list
sudo apt-get update
sudo apt-get install helm

