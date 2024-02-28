#!/bin/bash
# Start Spring Boot application
# nohup java -jar /app/*.jar > /app/app.log &

# Start Apache in the foreground
/usr/sbin/apache2ctl -D FOREGROUND

# java -jar /app/*.jar > /app/app.log
# service apache2 start
