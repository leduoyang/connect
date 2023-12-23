#!/bin/bash

# Start Java backend service in the background
java -jar app.jar --spring.profiles.active=dev &

# Start Filebeat in the background
mkdir -p /usr/share/filebeat/logs
nohup /usr/share/filebeat/filebeat -e -c /usr/share/filebeat/filebeat.yml >> /usr/share/filebeat/logs/filebeat.log 2>&1 &

# Keep the container running
tail -f /dev/null
