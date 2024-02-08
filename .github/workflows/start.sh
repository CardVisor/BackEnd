# start.sh
#!/bin/bash
service apache2 start
java -jar /app/*.jar > /app/app.log
