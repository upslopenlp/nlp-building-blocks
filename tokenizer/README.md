# Tokenizer

* To build: `mvn clean install`
* To run: `java -jar sonnet-app/target/sonnet.jar`
* To tokenize: `curl "http://localhost:9040/api/tokenize?language=eng" -d "George Washington was president." -H "Content-Type: text/plain"`

This command produces the response:

`["George","Washington","was","president"]`

The array of tokens can now be utilized by downstream NLP microservices and applications.