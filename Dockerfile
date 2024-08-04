FROM amazoncorretto:17

# JAR 파일을 복사
COPY ./build/libs/*.jar server.jar

# ImageMagick 설치
RUN yum update -y \
    && yum install -y ImageMagick

# ENTRYPOINT 설정
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "server.jar"]