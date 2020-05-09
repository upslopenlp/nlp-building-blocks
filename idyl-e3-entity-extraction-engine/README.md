# Idyl E3 Entity Extraction Engine

Idyl E3 Entity Extraction Engine is a stateless microservice for performing NLP language detection. It is powered by [Idyl NLP](https://github.com/idylnlp/idylnlp). Idyl E3 is available via [DockerHub](https://hub.docker.com/r/mtnfog/idyl-e3/) and the [AWS Marketplace](https://aws.amazon.com/marketplace/pp/B01BSQUR2K).

## Quick Start

* To build: `mvn clean install`
* To run: `java -jar idyl-e3-app/target/idyl-e3.jar`
* To detect language: `curl "http://localhost:9000/api/extract?language=eng" -d '["George", "Washington", "was", "president."]' -H "Content-Type: application/json"`

The response:

`{"entities":[{"text":"George Washington","confidence":0.96,"span":{"tokenStart":0,"tokenEnd":2},"type":"person","languageCode":"eng","extractionDate":1512511318007,"metadata":{"x-model-filename":"mtnfog-en-person.bin"}}],"extractionTime":2}`

## Clients

The [NLP Building Blocks Java SDK](https://github.com/mtnfog/nlp-building-blocks-java-sdk) includes a client for Idyl E3's API.

## License

Idyl E3 Entity Extraction Engine is licensed under the Apache License, version 2.0.

Copyright 2018 Mountain Fog, Inc.
