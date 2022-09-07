# Named-Entity Recognizer

* To build: `mvn clean install`
* To run: `java -jar ner-app/target/ner.jar`
* To detect language: `curl "http://localhost:9000/api/extract?language=eng" -d '["George", "Washington", "was", "president."]' -H "Content-Type: application/json"`

The response:

`{"entities":[{"text":"George Washington","confidence":0.96,"span":{"tokenStart":0,"tokenEnd":2},"type":"person","languageCode":"eng","extractionDate":1512511318007,"metadata":{"x-model-filename":"mtnfog-en-person.bin"}}],"extractionTime":2}`
