#!/usr/bin/env bash

set -e

. common.sh


aws cloudformation deploy --stack-name ${ECS_REPO_STACK_NAME} --template-file `pwd`/cloudformation/ecs-repo.yaml \
--parameter-overrides \
RepositoryName=${REPOSITORY_NAME} \
--profile ${AWS_PROFILE} \
--region ${AWS_REGION} \
--capabilities  CAPABILITY_NAMED_IAM

. push.sh


aws cloudformation deploy --stack-name ${ECS_SERVICE_STACK_NAME} --template-file `pwd`/cloudformation/ecs-service.yaml \
--parameter-overrides \
RepositoryName=${REPOSITORY_NAME} \
ProjectName="${PROJECT_NAME}" \
ProjectVersion="${PROJECT_VERSION}" \
MyIP="${MY_IP}" \
--profile ${AWS_PROFILE} \
--region ${AWS_REGION} \
--capabilities  CAPABILITY_NAMED_IAM