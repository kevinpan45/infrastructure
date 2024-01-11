job "nexus-deploy" {
  datacenters = ["dc1"]
  type        = "service"

  group "nexus" {
    volume "edge-stack" {
      type      = "host"
      source    = "edge-stack"
      read_only = false
    }
    network {
      port "ui" {
        static = 8081
      }
    }
    count = 1
    task "nexus-task" {
      driver = "docker"
      config {
        image = "sonatype/nexus3"
        ports = ["ui"]
        volumes = [
          "/data/edge-stack/${NOMAD_JOB_NAME}:/nexus-data" # chown -R 200 this directory in the host
        ]
      }
      resources {
        cpu    = 4096
        memory = 8192
      }
    }
  }
}