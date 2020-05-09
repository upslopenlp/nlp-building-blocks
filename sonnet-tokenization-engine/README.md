# Sonnet Tokenization Engine

Sonnet Tokenization Engine is a stateless microservice for performing NLP string tokenization. It is powered by [Idyl NLP](https://github.com/idylnlp/idylnlp). Sonnet Tokenization Engine is available on [DockerHub](https://hub.docker.com/r/mtnfog/sonnet/), [AWS Marketplace](http://aws.amazon.com/marketplace/pp/B0787XP17M), and [Azure Marketplace](https://azuremarketplace.microsoft.com/en-us/marketplace/apps/mtnfog.sonnet-tokenization-engine?tab=Overview).

Sonnet Tokenization Engine can use trained models to perform the string tokenization or default internal tokenizers.

## Quick Start

* To build: `mvn clean install`
* To run: `java -jar sonnet-app/target/sonnet.jar`
* To tokenize: `curl "http://localhost:9040/api/tokenize?language=eng" -d "George Washington was president." -H "Content-Type: text/plain"`

This command produces the response:

`["George","Washington","was","president"]`

The array of tokens can then be utilized by other NLP microservices and applications.

## Clients

The [NLP Building Blocks Java SDK](https://github.com/mtnfog/nlp-building-blocks-java-sdk) includes a client for Sonnet's API.

## License

Sonnet Tokenization Engine is licensed under the Apache License, version 2.0.

Copyright 2018 Mountain Fog, Inc.
