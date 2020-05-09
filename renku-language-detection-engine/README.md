# Renku Language Detection Engine

Renku Language Detection Engine is a stateless microservice for performing NLP language detection. It is powered by [Idyl NLP](https://github.com/idylnlp/idylnlp). Renku is available via [DockerHub](https://hub.docker.com/r/mtnfog/renku/), [AWS Marketplace](http://aws.amazon.com/marketplace/pp/B078KCFVDV), and [Azure Marketplace](https://azuremarketplace.microsoft.com/en-us/marketplace/apps/mtnfog.renku-language-detection-engine?tab=Overview).

## Quick Start

* To build: `mvn clean install`
* To run: `java -jar renku-app/target/renku.jar`
* To detect language: `curl "http://localhost:7070/api/language" -d "What language is this text?" -H "Content-Type text/plain"`

## Clients

The [NLP Building Blocks Java SDK](https://github.com/mtnfog/nlp-building-blocks-java-sdk) includes a client for Renku's API.

## License

Renku Language Detection Engine is licensed under the Apache License, version 2.0.

Copyright 2018 Mountain Fog, Inc.
