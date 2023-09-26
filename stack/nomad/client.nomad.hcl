// Nomad client config with common plugin setting

# Copyright (c) HashiCorp, Inc.
# SPDX-License-Identifier: MPL-2.0

# Full configuration options can be found at https://www.nomadproject.io/docs/configuration

data_dir  = "/opt/nomad/data"
bind_addr = "0.0.0.0"

// Replace server ip with your own
client {
  enabled = true
  servers = ["127.0.0.1"]
}

plugin "docker" {
  config {
    allow_privileged = true
  }
}

plugin "raw_exec" {
  config {
    enabled = true
  }
}
