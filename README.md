# Description

Kotlin multi-platform project to manage subscriptions

KMM, Compose, OAuth, GraphQL

# Running

## Run database

```bash
docker-compose up
```

### HASURA Console

open http://localhost:8080/console

## Run server

```bash
./gradlew server:run
```

## Run web client

```bash
./gradlew web:jsBrowserDevelopmentRun
```

open http://localhost:9091/