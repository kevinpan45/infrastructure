job "minio-deploy" {
  datacenters = ["dc1"]
  group "minio" {
    count = 1
    network {
      port "http" {
        to = 9000
      }
      port "rpc" {
        to = 9001
      }
    }
    task "minio" {
      driver = "docker"
      env = {
        "MINIO_ROOT_USER"     = "${MINIO_ROOT_USER}"
        "MINIO_ROOT_PASSWORD" = "${MINIO_ROOT_PASSWORD}"
      }
      config {
        image = "quay.io/minio/minio"
        volumes = [
          "/data/edge-stack/${NOMAD_JOB_NAME}/data:/mnt/data",
          "/data/edge-stack/${NOMAD_JOB_NAME}/config.env:/etc/config.env"
        ]
        ports = ["http", "rpc"]
        args = [
          "server",
          "/data"
        ]
      }
      resources {
        cpu    = 500
        memory = 256
      }
    }
  }
}