#!/usr/bin/env bash

set -e

. common.sh

aws cloudformation deploy --stack-name ${DB_STACK_NAME} --template-file `pwd`/cloudformation/db.yaml \
--parameter-overrides \
DatabaseName=${DB_NAME} \
DatabaseUsername=test1234 \
DatabasePassword=test1234 \
VPC=${MY_VPC} \
Subnets=${MY_SUBNETS} \
MyIP=${MY_IP} \
--profile ${AWS_PROFILE} \
--region ${AWS_REGION}