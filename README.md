# Infrastructure
Microservice infrastructure base on k3s

## Folder Description

- archetype: Maven archetype project base on common, for generating microservice project
- iam: Identity and access management, contains Account/Authentication/Authorization/Organization modules
- infrastructure: Application architecture deployment script and code
- common: Basic framework, all microservice project base on this project 
- platform: Platform management
  - subscriber: Event subscriber service
  - msg: Message service 
- vm-prepare: ECS initial script and code, IaC by Hashicorp Terraform
