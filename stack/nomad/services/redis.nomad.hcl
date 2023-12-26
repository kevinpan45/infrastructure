job "redis-deploy" {
  datacenters = ["dc1"]
  type        = "service"

  group "redis" {
    count = 1
    task "redis-task" {
      driver = "docker"
      config {
        image = "redis:latest"
      }
    }
  }
}
