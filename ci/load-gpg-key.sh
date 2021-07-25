#!/bin/bash
set -e

ENV_FILE="env/sonatype.env"

pushd "$(dirname "$0")" > /dev/null

if [ -f "$ENV_FILE" ] ;then
  #shellcheck source=env/sonatype.env
  . "$ENV_FILE"
fi

# base64-encoded armored GPG key
GPG_KEY_FILE="../release.asc"
# intermediary GPG key
GPG_KEY_BIN_FILE="../release.gpg"

echo "$GPG_SIGNING_KEY" | base64 --decode > "$GPG_KEY_FILE"
gpg --quiet --output "$GPG_KEY_BIN_FILE" --dearmor "$GPG_KEY_FILE"


popd > /dev/null
