# Receipt Processor Developing Environment
* OS: MacOS
* Language: Java 17
* Framework: Spring Boot
* Database: H2
* Project: Maven
* IDE: IntelliJ

## Run 
### with Docker
```
docker build -t lxy/app .
docker run -p 8080:8080 lxy/app
```
### with .jar
```
java -jar target/*.jar
```
## Endpoints
### Post Request: 
* Path: `localhost:8080/receipts/process`
* Method: `Post`
* Payload: Receipt JSON

### Get Request: 
* Path: `localhost:8080/receipts/{id}/points`
* Method: `Post`
