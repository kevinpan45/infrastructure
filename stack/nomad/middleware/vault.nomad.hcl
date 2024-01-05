job "vault-deploy" {
  datacenters = ["dc1"]
  type        = "service"

  group "vault" {
    network {
      port "vault" {
        static = 8200
      }
    }
    volume "edge-stack" {
      type      = "host"
      source    = "edge-stack"
      read_only = false
    }
    task "vault-task" {
      driver = "docker"
      config {
        image = "vault:1.13.3"
        ports = ["vault"]
      }
      resources {
        cpu    = 512
        memory = 1024
      }
    }
  }
}
