#!/bin/sh

read_var(){
  echo $(grep -v '^#' .env | grep -e "$1" | sed -e 's/.*=//')
}

SECRET=$(read_var "HASURA_GRAPHQL_ADMIN_SECRET")
ENDPOINT=$(read_var "HASURA_GRAPHQL_ENDPOINT")

echo "HASURA_GRAPHQL_ENDPOINT: $ENDPOINT"
echo "HASURA_GRAPHQL_ADMIN_SECRET: $SECRET"

./gradlew downloadApolloSchema --endpoint="$ENDPOINT" --schema "./common/app/src/commonMain/graphql/schema.graphqls" --header "X-Hasura-Admin-Secret: $SECRET"
