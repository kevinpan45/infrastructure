job "dtrack-deploy" {
  datacenters = ["dc1"]
  type        = "service"

  group "dtrack" {
    // base on keycloak OIDC
    volume "edge-stack" {
      type      = "host"
      source    = "edge-stack"
      read_only = false
    }
    network {
      port "ui" {
        static = 8080
      }
      port "api" {
        static = 8080
      }
    }
    count = 1
    task "dtrack-server-task" {
      driver = "docker"
      env = {
        "ALPINE_OIDC_ENABLED"              = "true"
        "ALPINE_OIDC_CLIENT_ID"            = "${OIDC_CLIENT_ID}"
        "ALPINE_OIDC_ISSUER"               = "${OIDC_ISSUER}"
        "ALPINE_OIDC_USERNAME_CLAIM"       = "preferred_username"
        "ALPINE_OIDC_TEAMS_CLAIM"          = "groups"
        "ALPINE_OIDC_USER_PROVISIONING"    = "true"
        "ALPINE_OIDC_TEAM_SYNCHRONIZATION" = "true"
      }
      config {
        image = "dependencytrack/apiserver"
        ports = ["api"]
        volumes = [
          "/data/edge-stack/${NOMAD_JOB_NAME}:/data"
        ]
      }
      resources {
        cpu    = 4096
        memory = 8192
      }
    }
    task "dtrack-ui-task" {
      driver = "docker"
      env = {
        "API_BASE_URL"   = "${API_BASE_URL}"
        "OIDC_ISSUER"    = "${OIDC_ISSUER}"
        "OIDC_CLIENT_ID" = "${OIDC_CLIENT_ID}"
      }
      config {
        image = "dependencytrack/frontend"
        ports = ["ui"]
      }
      resources {
        cpu    = 512
        memory = 1024
      }
    }
  }
}
