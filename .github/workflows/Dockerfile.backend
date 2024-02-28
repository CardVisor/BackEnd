# 베이스 이미지 설정
FROM openjdk:17
WORKDIR /app

# 빌드된 JAR 파일을 Docker 이미지에 추가
COPY ../../backend/CardVisor/build/libs/CardVisor-0.0.1-SNAPSHOT.jar ./app.jar

# 컨테이너 실행 시 시작 명령어
CMD ["java", "-jar", "./app.jar"]

# 컨테이너 내부의 애플리케이션 포트
EXPOSE 7777
