#!/bin/sh

if [ ! -f .env ]
then
  export $(cat .env | xargs)
fi

 ./gradlew downloadApolloSchema --endpoint="http://localhost:8080/v1/graphql" --schema "./common/app/src/commonMain/graphql/schema.graphqls" --header "X-Hasura-Admin-Secret: $HASURA_GRAPHQL_ADMIN_SECRET"
