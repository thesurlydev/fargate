#!/usr/bin/env bash

set -e

# update path as needed
. ././../cloudformaton-common.sh

# derived from 'rootProject.name'  property in settings.gradle.kts
PROJECT_NAME=$(./gradlew properties -q | grep "name:" | awk '{print $2}' | tr -d '[:space:]')

# derived from 'version' property in gradle.properties
PROJECT_VERSION=$(./gradlew properties -q | grep "version:" | awk '{print $2}' | tr -d '[:space:]')
PROJECT_LOCAL_TAG="${PROJECT_NAME}:${PROJECT_VERSION}"

# assumes the repository exists!
REPOSITORY_NAME="${PROJECT_NAMESPACE}/${PROJECT_NAME}"

PROJECT_REMOTE_TAG="${REPOSITORY_NAME}:${PROJECT_VERSION}"

ECR_HOST="${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
ECS_SERVICE_DESIRED_COUNT=2

# DatabaseName must begin with a letter and contain only alphanumeric characters.
DB_NAME="${PROJECT_NAME//-/}"
DB_STACK_NAME="${PROJECT_NAME}-db-stack"
ECS_REPO_STACK_NAME="${PROJECT_NAME}-ecs-repo-stack"
ECS_SERVICE_STACK_NAME="${PROJECT_NAME}-ecs-service-stack"