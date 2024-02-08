# Stage 1: Copy the React application
FROM alpine as frontend-build
WORKDIR /app
COPY ../../frontend/build/ ./

# Stage 2: Copy the Spring Boot application
FROM alpine as backend-build
WORKDIR /app
COPY ../../backend/CardVisor/build/libs/ ./

# Stage 3: Set up the Apache server
FROM ubuntu:20.04 as server-setup
ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get update && \
    apt-get install -y software-properties-common && \
    add-apt-repository universe && \
    apt-get install -y apache2 && \
    a2enmod proxy && \
    a2enmod proxy_http && \
    service apache2 restart && \
    apt-get install -y openjdk-17-jdk && \
    apt-get install -y tzdata && \
    apt-get install -y locales && \
    locale-gen ko_KR.UTF-8 && \
    apt-get install -y supervisor && \
    apt-get install -y tini && \
    rm -rf /var/lib/apt/lists/*

ENV LANG ko_KR.UTF-8  
ENV LANGUAGE ko_KR:ko  
ENV LC_ALL ko_KR.UTF-8

COPY --from=backend-build /app/CardVisor-0.0.1-SNAPSHOT.jar /app/
COPY --from=frontend-build /app /var/www/html
COPY ../../backend/.github/workflows/000-default.conf /etc/apache2/sites-available/
COPY ../../backend/.github/workflows/start.sh /start.sh
ENTRYPOINT ["/usr/bin/tini", "--"]
CMD ["/bin/bash", "/start.sh"]

EXPOSE 80
