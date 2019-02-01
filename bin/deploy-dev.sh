#!/usr/bin/env bash
set -e
set -o pipefail
CURR_DIR=$(dirname $0)
printf "Current Directory: %s \n" "$CURR_DIR"

_=$(command -v cf)

if [ "$?" != "0" ]; then
  echo "Cloud Foundry needs to be installed"
  return 1;
fi

SPACE_PREFIX='ShopRite Mobile App'
SPACE="$SPACE_PREFIX - Dev"
APP="srmobile-develop"
ROUTE="mybluemix.net"
PRIMARY_ROUTE="srmobile-develop.mybluemix.net"
WAR="target/shopritemobileapplication.war"
BRANCH=$(git rev-parse --abbrev-ref HEAD) 
APP_NAME="$APP-$BRANCH"

function deploy_app {
  echo "Attempting to deploy $WAR to $APP_NAME"
  if [ ! -e $WAR ]; then
    echo "Can't find war file"
    return 1
  fi
  results=$(cf push "$APP_NAME" -n "$APP_NAME" -p "$WAR")
  printf "Successfully deployed %s\n", $APP_NAME
}

function map_route {
  echo "Attempting to map $APP_NAME to $APP.$ROUTE"
  results=$(cf map-route "$APP_NAME" "$ROUTE" -n "$APP")
  echo "Successfully mapped route"
}

function unmap_route {
  echo "Attempting to unmap $APP_NAME from $APP.$ROUTE"
  results=$(cf unmap-route "$APP_NAME" "$ROUTE" -n "$APP")
  echo "Successfully unmapped $APP_NAME from $APP.$ROUTE"
}

function switch_space {
  results=$(cf target -s "$SPACE")
  return
}

function delete_app {
  echo "Attempting to delete $APP_NAME from $SPACE"
  results=$(cf delete $APP_NAME -f)
  return
}

function deploy() {
  switch_space
  deploy_app
  map_route
}

function teardown() {
  switch_space
  unmap_route
  delete_app
}

# Parse arguments
usage() {
    echo "Usage: $0 [-h] [-d] [-t]"
    echo "  -h  Deploy or Teardown Develop Cloud Foundry app using current branch as name."
    echo "  -d  Deploy. Deploy generated war file to Cloud Foundry"
    echo "  -t  Teardown. Delete the generated application in Cloud Foundry."
    exit
}

COMMAND=$1
case "$COMMAND" in
  "deploy")
    deploy
    ;;
  "teardown")
    teardown
    ;;
esac
