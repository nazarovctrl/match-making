# Matchmaking Server


## Requirements

- Java 21
- Spring Boot 3.3.1

## How to run

<details close>
  <summary>
    <h3>
      Docker    
    </h3>
  </summary>
</details>

<details close>
  <summary>
    <h3>
      Jar    
    </h3>
  </summary>
    
1. **Clone the repository:**

    ```sh
    git clone https://github.com/nazarovctrl/match-making.git
    cd match-making
    ```
2. **Paste the .env file into  match-making folder**

    .env file content
   
    ```.env
    DB_URL=<your-databse-url>
    DB_USERNAME=<your-databse-username>
    DB_PASSWORD=<your-databse-password>
    SECURITY_TOKEN_ACCESS_SECRET_KEY=<repalce-with-generated-secret-koy-for-access-token>
    SECURITY_TOKEN_ACCESS_TIME=<access-token-valid-time-in-millieseconds>
    SECURITY_TOKEN_REFRESH_SECRET_KEY=<repalce-with-generated-secret-koy-for-refresh-token>
    SECURITY_TOKEN_REFRESH_TIME=<refresht-token-valid-time-in-millieseconds>
    ```
4. **Build the project:**

    Use Maven to build the project.

    ```sh
    mvn clean install
    ```

5. **Run the application:**

    To run the application, make sure you have Java 21 installed
    
    ```sh
    java -jar target/match-making-0.0.1-SNAPSHOT.jar
    ```

</details>
