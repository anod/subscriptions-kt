#!/bin/sh
## subscriptionsbw_postgres_1

# Backup
docker exec -ti subscriptionsbw-postgres-1 /bin/bash 

pg_dump -U postgres -d postgres > dump.sql

docker cp subscriptionsbw-postgres-1:/dump.sql ./
