#!/usr/bin/env bash

# Builds, tags and pushes latest api-server code to ECR

set -e

. common.sh

echo "Building..."

./gradlew clean build jibDockerBuild -x test

echo "Tagging..."

docker tag ${PROJECT_LOCAL_TAG} ${ECR_HOST}/${PROJECT_REMOTE_TAG}

echo "Logging into ECR..."

LOGIN_CMD=`aws ecr get-login --no-include-email --region ${AWS_REGION} --profile ${AWS_PROFILE}`

${LOGIN_CMD}

echo "Pushing..."

docker push ${ECR_HOST}/${PROJECT_REMOTE_TAG}

echo "Done!"