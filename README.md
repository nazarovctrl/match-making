# Matchmaking Server


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
```

## Running the Application with Docker Compose

To run the application, ensure that Docker and Docker Compose are installed on your machine. 
Then, execute the commands in the specified order.

1. **Pull the Docker Image**

```sh
docker pull nazarovv2/match-making:latest
```

2. **Start the Application**

```sh
docker-compose up -d match-making-app
```
