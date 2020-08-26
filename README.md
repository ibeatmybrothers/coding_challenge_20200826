### WordCount Service (LifeWay Coding Challenge)

#### Requirements
1. Create a service that takes in a request with an id and message field, counts the words within the message, and returns an accumulative count.
2. The service should ignore requests with an id that has already been processed
3. The service should expect a JSON request and return a JSON response
 
#### Assumptions
1. I assumed by "word", the service should filter out any text that is not alphabetical
2. I assumed by ignoring requests with duplicate ids, the service should return a successful response, but not process the message, so it returns the current count
3. I assumed I should use a POST method since there is no limit specified on the request size

#### Why DropWizard
Mostly because I've used it in the past. It's easy to pull in a base structure and then add what I need, and it also lends itself well to small services like this.

#### Why Swagger
It's nice documentation for services, and it's an easy way to do an eye test. It dawned on me afterwards that there's probably scripts ready to run to validate this app, 
but it's a good tool to include regardless.

#### How To Start Up
1. Open a terminal/command prompt
2. cd into lifeway-coding-challenge directory
3. Run `mvn clean package` to create the JAR locally
4. Run `java -jar target/lifeway-coding-challenge-1.0-SNAPSHOT.jar server src/main/resources/config.yml`


#### How to Test
##### Sample CURL Command
```
curl -X POST "http://localhost:8080/word-count/post" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"id\": \"4\", \"message\": \"This is a-test call on the service ! ! ! This should-return a count of 16 when it's first run.\"}"
```

##### Using Swagger
Once the app is running, you can connect to Swagger using this [link](http://localhost:8080/swagger) 
