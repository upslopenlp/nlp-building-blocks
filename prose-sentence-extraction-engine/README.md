# Prose Sentence Detection Engine

Prose Sentence Extraction Engine is a stateless microservice for performing NLP sentence extraction. It is powered by [Idyl NLP](https://github.com/idylnlp/idylnlp). Renku is available via [DockerHub](https://hub.docker.com/r/mtnfog/prose/), [AWS Marketplace](http://aws.amazon.com/marketplace/pp/B078K3W9QH), and [Azure Marketplace](https://azuremarketplace.microsoft.com/en-us/marketplace/apps/mtnfog.prose-sentence-extraction-engine?tab=Overview).

## Quick Start

* To build: `mvn clean install`
* To run: `java -jar prose-app/target/renku.jar`
* To extract sentences: `curl "http://localhost:8060/api/sentences" -d "This is a sentence. This is another sentence." -H "Content-Type text/plain"`

## Clients

The [NLP Building Blocks Java SDK](https://github.com/mtnfog/nlp-building-blocks-java-sdk) includes a client for Prose's API.

## License

Prose Sentence Detection Engine is licensed under the Apache License, version 2.0.

Copyright 2018 Mountain Fog, Inc.
