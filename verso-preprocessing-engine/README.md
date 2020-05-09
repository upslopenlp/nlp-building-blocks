# Verso Preprocessing Engine

Verso Preprocessing Engine is a stateless microservice for preprocessing text in preparation for processing by an NLP pipeline. Verso exposes common text preprocessing functions through a REST interface.

Verso supports:

* Lowercasing.
* Uppercasing.
* Removing words below a minimum length.
* Removing words longer than a minimum length.
* Removing digits.
* Removing punctuation.
* Removing stopwords (currently only English is supported).

## Quick Start

* To build: `mvn clean install`
* To run: `java -jar verso-app/target/verso.jar`
* To detect language: `curl "http://localhost:7080/api/text" -d "Preprocess this text, please." -H "Content-Type text/plain"`

## Clients

The [NLP Building Blocks Java SDK](https://github.com/mtnfog/nlp-building-blocks-java-sdk) includes a client for Verso's API.

## License

Verso Preprocessing Engine is licensed under the Apache License, version 2.0.

Copyright 2018 Mountain Fog, Inc.
