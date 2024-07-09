# Matchmaking Server

This project is a matchmaking server built with Java 21 and Spring Boot 3.3.1. The server supports various game modes, client readiness confirmation, match result reporting, and ELO rating calculation.

## Table of Contents

- [Requirements](#requirements)
- [Setup](#setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [ELO Calculation](#elo-calculation)
- [License](#license)

## Requirements

- Java 21
- Spring Boot 3.3.1

## Setup

1. **Clone the repository:**

    ```sh
    git clone https://github.com/yourusername/matchmaking-server.git
    cd matchmaking-server
    ```

2. **Build the project:**

    Use Maven to build the project.

    ```sh
    ./mvnw clean install
    ```

3. **Run the application:**

    Use Maven to run the Spring Boot application.

    ```sh
    ./mvnw spring-boot:run
    ```

## Running the Application

To run the application, make sure you have Java 21 installed. You can run the application using the following command:

```sh
java -jar target/matchmaking-server-0.0.1-SNAPSHOT.jar
