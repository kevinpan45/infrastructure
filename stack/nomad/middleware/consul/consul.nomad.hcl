job "consul-deploy" {
  datacenters = ["dc1"]

  group "consul" {
    volume "edge-stack" {
      type      = "host"
      source    = "edge-stack"
      read_only = false
    }

    network {
      port "http" {
        static = 8500
      }
      port "udp" {
        static = 8600
      }
    }

    task "consul" {
      driver = "docker"

      config {
        image = "consul:1.15.4"
        ports = ["http", "udp"]
        args = [
          "agent",
          "-server",
          "-ui",
          "-node=old-dog",
          "-bootstrap-expect=1",
          "-client=0.0.0.0",
          "-data-dir=/consul/data",
          "-config-dir=/consul/config"
        ]

        volumes = [
          "/data/edge-stack/${NOMAD_JOB_NAME}/config:/consul/config",
          "/data/edge-stack/${NOMAD_JOB_NAME}/data:/consul/data"
        ]
      }

      resources {
        cpu    = 500
        memory = 256
      }
    }
  }
}