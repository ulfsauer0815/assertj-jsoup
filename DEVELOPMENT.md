# Development

## Release a new version

### Tag the new version
```sh
./gradlew release
```
[Pre-release checks](https://github.com/allegro/axion-release-plugin/blob/master/docs/configuration/checks.md) can be disabled.

### Update version references

Update version references to the new one, e.g. README.md.

### Publish

You can choose to
**(A)** let GitHub Actions publish the release for you or
**(B)** publish it from your local machine.

*Note*: **(A)** will also publish a GitHub release draft  

#### (A) GitHub Actions
```sh
git push --tags
```

#### (B) Locally

Make sure the signing ey is present as `release.gpg` or get it via `ci/load-gpg-key.sh`.  

Fill out `ci/env/sonatype.env` (see [example](ci/env/sonatype.env.example)), then run

```sh
./ci/release.sh
```

### Approve

#### Maven Central
Manually approve the release to move it from staging to public:\
https://s01.oss.sonatype.org/#stagingRepositories

Staging Repositories: Close -> Release

#### GitHub
Manually approve the GitHub release draft:\
https://github.com/ulfsauer0815/assertj-jsoup/releases


## GPG Signing

Signing key (last 8 digits): `83D3EDD`


## More Info

### Publish / Release

- [Sonatype / Maven Central requirements](https://central.sonatype.org/publish/requirements)
