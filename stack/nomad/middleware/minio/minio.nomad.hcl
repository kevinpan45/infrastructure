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
        "MINIO_IDENTITY_OPENID_CONFIG_URL_PRIMARY_IAM"="${MINIO_IDENTITY_OPENID_CONFIG_URL_PRIMARY_IAM}"
        "MINIO_IDENTITY_OPENID_CLIENT_ID_PRIMARY_IAM"="${MINIO_IDENTITY_OPENID_CLIENT_ID_PRIMARY_IAM}"
        "MINIO_IDENTITY_OPENID_CLIENT_SECRET_PRIMARY_IAM"="${MINIO_IDENTITY_OPENID_CLIENT_SECRET_PRIMARY_IAM}"
        "MINIO_IDENTITY_OPENID_DISPLAY_NAME_PRIMARY_IAM"="minio"
        "MINIO_IDENTITY_OPENID_SCOPES_PRIMARY_IAM"="openid,email,preferred_username"
        "MINIO_IDENTITY_OPENID_REDIRECT_URI_DYNAMIC_PRIMARY_IAM"="on"
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