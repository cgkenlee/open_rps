# open_rps

> Open Rock Paper Scissors Spring Boot project

> Simple hypermedia-driven RESTful web service on a game of rock, paper, scissors and/or lizard, spock 

> HATEOAS, Spring REST Docs, Asciidoctor, MongoDB

---

## Installation

### Prerequisites
- Java 8
- A running MongoDB instance
 
### Clone

- Clone this repo to your local machine using `https://github.com/cgkenlee/open_rps`

### Setup

> Run via gradle wrapper

```shell
$ gradlew bootRun
```

or

> Build a jar via gradle wrapper and run 

```shell
$ gradlew bootJar
$ java -jar open_rps-0.0.1-SNAPSHOT.jar
```

---

## Documentation
- Using Spring REST Docs, gradle tasks `bootJar` and `asciidoctor` generates documentation

## Tests
- JUnit 5 Spring Boot tests, gradle task `test`
