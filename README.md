
# fargate

Minimal template for AWS Fargate.


## Prerequisite

Create a file to hold project-agnostic variables. (Sourced from `common.sh`)

```
#!/usr/bin/env bash

set -e

AWS_PROFILE=default
AWS_ACCOUNT_ID=[YOUR_AWS_ACCOUNT_ID]
AWS_REGION="us-west-2"
MY_IP=[YOUR_IP]
MY_VPC=[YOUR_VPC_ID]
MY_SUBNETS=[SUBNETS]
PROJECT_NAMESPACE=[YOUR_NAMESPACE]
```

By default the ECS service and database are locked down and allow ingress from the IP address defined by `MY_IP`  


## Initial Setup

Optional: To create an Aurora cluster:

```
./create-db.sh`
```
  
Build and initial setup of a Fargate cluster:

```
./create-ecs-service.sh`
```
 
  
## Build

```
./gradlew clean build jibDockerBuild
```    
  
## Deploying  
  
To redeploy code changes:

1. update project version in `gradle.properties`
2. run `./redeploy.sh`     