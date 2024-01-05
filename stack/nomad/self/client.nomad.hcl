client {
  enabled = true
  servers = ["127.0.0.1"]
}

plugin "docker" {
  config {
    allow_privileged = true
  }
}