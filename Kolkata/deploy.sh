#!/bin/bash
set -e
mvn clean package
cp target/*.war ~/wildfly/standalone/deployments
