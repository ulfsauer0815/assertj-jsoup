# Development

## Release locally

### Tag the new version
```sh
./gradlew release
```
[Pre-release checks](https://github.com/allegro/axion-release-plugin/blob/master/docs/configuration/checks.md) can be disabled.

### Publish

Make sure the signing ey is present as `release.gpg` or get it via `ci/load-gpg-key.sh`.  

Make sure to fill out `ci/env/sonatype.env`, then run

```sh
./ci/release.sh
```

## GPG Signing

Signing key (last 8 digits): `83D3EDD`


## More Info

### Publish / Release

- [Sonatype / Maven Central requirements](https://central.sonatype.org/publish/requirements)
