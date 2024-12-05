## Run Dokploy in Docker

```bash
docker network create --driver overlay --attachable dokploy-network
 
mkdir -p /etc/dokploy
 
chmod -R 777 /etc/dokploy
 
docker pull dokploy/dokploy:latest
 
# Installation
docker service create \
  --name dokploy \
  --replicas 1 \
  --network dokploy-network \
  --mount type=bind,source=/var/run/docker.sock,target=/var/run/docker.sock \
  --mount type=bind,source=/etc/dokploy,target=/etc/dokploy \
  --publish published=3000,target=3000,mode=host \
  --update-parallelism 1 \
  --update-order stop-first \
  dokploy/dokploy:latest
```