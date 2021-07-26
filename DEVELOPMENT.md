# Development

## Release

### Tag the new version
```sh
./gradlew release
```
[Pre-release checks](https://github.com/allegro/axion-release-plugin/blob/master/docs/configuration/checks.md) can be disabled.

### Publish

You can choose to
(A) let GitHub Actions publish the release for you or
(B) publish it from your local machine

#### (A) GitHub Actions
```sh
git push --tags
```

#### Option (B): Locally

Make sure the signing ey is present as `release.gpg` or get it via `ci/load-gpg-key.sh`.  

Fill out `ci/env/sonatype.env` (see [example](ci/env/sonatype.env.example)), then run

```sh
./ci/release.sh
```

## GPG Signing

Signing key (last 8 digits): `83D3EDD`


## More Info

### Publish / Release

- [Sonatype / Maven Central requirements](https://central.sonatype.org/publish/requirements)
