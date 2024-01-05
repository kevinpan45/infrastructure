job "redis-deploy" {
  datacenters = ["dc1"]
  type        = "service"

  group "redis" {
    network {
      port "redis" {
        static = 6379
      }
    }
    count = 1
    task "redis-task" {
      driver = "docker"
      config {
        image = "redis:latest"
        ports = ["redis"]
      }
      resources {
        cpu    = 512
        memory = 1024
      }
    }
  }
}
