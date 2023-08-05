#!/bin/sh

# Restore
#docker compose up postgres
docker cp ./dump.sql subscriptionsbw-postgres-1:/

docker exec -it subscriptionsbw-postgres-1 dropdb -U postgres postgres
docker exec -it subscriptionsbw-postgres-1 psql -U postgres -d template1 -c 'CREATE DATABASE postgres'
docker exec -it subscriptionsbw-postgres-1 psql -U postgres -d postgres -f dump.sql