# Sentence Detector

* To build: `mvn clean install`
* To run: `java -jar sentence-detector-app/target/sentence-detector.jar`
* To extract sentences: `curl "http://localhost:8060/api/sentences" -d "This is a sentence. This is another sentence." -H "Content-Type text/plain"`