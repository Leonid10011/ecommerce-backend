#!/bin/bash

# Maven clean & package to create image
./mvnw clean package -DskipTests

# check if successful

if [ $? -ne 0 ]; then
  echo "Maven build failed"
  exit 1
fi

# Create Docker image
running_container=$(docker ps -a -q -f name=ecommerce)

if [ -n "$running_container" ]; then
  echo "Container 'ecommerce' is running. Stopping and removing ..."
  docker stop ecommerce
  docker rm ecommerce

  if [ $? -ne 0 ]; then
    echo "Failed to stop or remove the 'ecommerce' container"
    exit 1
  fi
fi

docker run -d --name ecommerce -d lb/ecommerce:1.0.0-SNAPSHOT -p 8090:8090 -it
