#!/bin/sh

echo "1. 🚀 Start Prod Docker Image Build!"

echo "⚡ bootJar"
chmod +x gradlew
./gradlew clean bootJar

JAR_FILE=$(find build/libs/ -type f -name "*.jar" | head -n 1)
echo "2. 🎯 Target JAR: $JAR_FILE"

IMAGE_NAME=$(basename "$JAR_FILE" | cut -d '-' -f 1)
IMAGE_TAG=$(basename "$JAR_FILE" | cut -d '-' -f 2 | cut -d '.' -f 1-3)
echo "3. 🐬 Docker Image Build, Version: wypl/$IMAGE_NAME:$IMAGE_TAG"
docker build -t "wypl/$IMAGE_NAME":"$IMAGE_TAG" .

echo "3. 🚀 Docker Container Start, Version: wypl/$IMAGE_NAME:$IMAGE_TAG"
docker run -d \
  --name wypl-image-server  \
  -e PROFILE=default  \
  -p 8080:8080  \
  "wypl/$IMAGE_NAME":"$IMAGE_TAG"