#!/bin/sh

read_var(){
  echo $(grep -v '^#' .env | grep -e "$1" | sed -e 's/.*=//')
}

SECRET=$(read_var "HASURA_GRAPHQL_ADMIN_SECRET")

echo "HASURA_GRAPHQL_ADMIN_SECRET: $SECRET"

./gradlew downloadApolloSchema --endpoint="http://localhost:8080/v1/graphql" --schema "./common/app/src/commonMain/graphql/schema.graphqls" --header "X-Hasura-Admin-Secret: $SECRET"
