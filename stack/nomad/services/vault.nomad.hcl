job "vault-deploy" {
  datacenters = ["dc1"]
  type        = "service"

  group "vault" {
    task "vault-task" {
      driver = "docker"
      config {
        image = "vault:1.13.3"
      }
    }
  }
}
