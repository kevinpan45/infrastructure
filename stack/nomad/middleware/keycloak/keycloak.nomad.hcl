job "keycloak-deploy" {
  datacenters = ["dc1"]

  group "keycloak" {
    volume "edge-stack" {
      type      = "host"
      source    = "edge-stack"
      read_only = false
    }

    network {
      port "http" {
        static = 8080
      }
    }

    task "keycloak" {
      driver = "docker"
      env = {
        "KEYCLOAK_ADMIN"          = "${KEYCLOAK_ADMIN}"
        "KEYCLOAK_ADMIN_PASSWORD" = "${KEYCLOAK_ADMIN_PASSWORD}"
      }
      config {
        image = "quay.io/keycloak/keycloak:latest"
        ports = ["http"]
        args = [
          "start",
          "--db=mysql",
          "--features=token-exchange",
          "--db-url=${MYSQL_JDBC_URL}",
          "--db-username=${MYSQL_USER}",
          "--db-password=${MYSQL_PASSWORD}",
        ]
      }

      resources {
        cpu    = 500
        memory = 256
      }
    }
  }
}