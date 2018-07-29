#!/usr/bin/env bash

set -e

. push.sh

TEMPLATE_BODY=file://`pwd`/cloudformation/ecs-service.yaml

aws cloudformation update-stack --stack-name ${ECS_SERVICE_STACK_NAME} \
--template-body ${TEMPLATE_BODY} \
--parameters \
ParameterKey=DesiredCount,ParameterValue=${ECS_SERVICE_DESIRED_COUNT} \
ParameterKey=ProjectVersion,ParameterValue="${PROJECT_VERSION}" \
ParameterKey=ProjectName,ParameterValue="${PROJECT_NAME}" \
ParameterKey=MyIP,ParameterValue="${MY_IP}" \
ParameterKey=RepositoryName,ParameterValue="${REPOSITORY_NAME}" \
--profile ${AWS_PROFILE} \
--region ${AWS_REGION} \
--capabilities  CAPABILITY_NAMED_IAM