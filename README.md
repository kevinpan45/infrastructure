# Infrastructure
Microservice infrastructure base on k3s

## Folder Description

- archetype: Maven archetype project base on common, for generating microservice project. GitHub Repo: https://github.com/kevinpan45/archetype
- iam: Identity and access management, contains Account/Authentication/Authorization/Organization modules. GitHub Repo: https://github.com/kevinpan45/iam
- infrastructure: Application architecture deployment script and code
- common: Basic framework, all microservice project base on this project. GitHub Repo: https://github.com/kevinpan45/basic-framework
- platform: Platform management. GitHub Repo: https://github.com/kevinpan45/platform
  - subscriber: Event subscriber service
  - msg: Message service 
- vm-prepare: ECS initial script and code, IaC by Hashicorp Terraform
