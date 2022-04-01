# Description

Kotlin multi-platform project to manage subscriptions

KMM, Compose, OAuth, GraphQL, Ktor

# Running

## Run database

```bash
docker-compose up
```

### HASURA Console

Open http://localhost:8080/console

Update schema

```bash
./download-graphql-schema.sh
```

## Run server

```bash
./gradlew server:run
```

## Run web client

```bash
./gradlew web:jsBrowserDevelopmentRun
```

open http://localhost:9091/