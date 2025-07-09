# POC Webflux Security

POC Spring Boot 4.0 with Webflux and Security

## Pre-requisites

- Java 21
- Gradle 8.14
- Kotlin 2.2

## Running the Project

### Build

```bash
./gradlew clean build
```

### Run App

```bash
./gradlew bootRun
```

## APIs

### Rest Controller Endpoints

**GET `/rc/greetings`**

```console
➜ curl http://localhost:8080/rc/greetings --user fResult:fResult123
{"greetings":"Hello, from Rest Controller, fResult"}⏎
```

**GET `/rc/greetings-with-roles`**
```console
➜ curl http://localhost:8080/rc/greetings-with-roles --user fResult:fResult123
{"greetings":"Hello, from Rest Controller with roles, [ROLE_USER]"}⏎  
```

### Functional Endpoints

**GET `/fe/greetings`**

```console
➜ curl http://localhost:8080/fe/greetings --user fResult:fResult123
{"greetings":"Hello, from Functional Endpoint, fResult"}⏎
```

**GET `/fe/greetings-with-roles`**

```console
➜ curl http://localhost:8080/fe/greetings-with-roles --user fResult:fResult123
{"greetings":"Hello, from Functional Endpoint with roles, [ROLE_USER]"}⏎  
```
