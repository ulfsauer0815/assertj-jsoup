#!/bin/bash
set -e

ENV_FILE="env/sonatype.env"

GPG_KEY_BIN_FILE="release.gpg"

pushd "$(dirname "$0")" > /dev/null

if [ -f "$ENV_FILE" ] ;then
  #shellcheck source=env/sonatype.env
  . "$ENV_FILE"
fi

pushd ".." > /dev/null

./gradlew publishToSonatype \
  "-PrequiredSigning" \
  "-Dorg.gradle.project.sonatypeUsername=$SONATYPE_USERNAME" \
  "-Dorg.gradle.project.sonatypePassword=$SONATYPE_PASSWORD" \
  "-Dorg.gradle.project.signing.keyId=$SIGNING_KEY_ID" \
  "-Dorg.gradle.project.signing.password=$SIGNING_PASSWORD" \
  "-Dorg.gradle.project.signing.secretKeyRingFile=$GPG_KEY_BIN_FILE"

popd > /dev/null

popd > /dev/null
