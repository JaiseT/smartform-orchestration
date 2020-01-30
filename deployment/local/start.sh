#!/bin/bash

# Bring down any previously running containers
docker-compose down

# Build images
echo 'Building images...'
docker-compose build --no-cache

# pause

echo 'Complete'


# Bring up new containers (silently)
docker-compose up

if which open > /dev/null; then
 open 'http://localhost:9080/webportal/'
elif which start > /dev/null; then
 start 'http://localhost:9080/webportal'
else
 echo "Could not detect the web browser to use."
fi