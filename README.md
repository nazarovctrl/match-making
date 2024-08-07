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

To run the application, ensure that Docker is installed on your machine.
Then, execute the commands in the specified order.

1. **Create match-making folder in your machine**

2. **Create and copy the .env file, and get docker-compose.yml from GitHub link below and put them into the match-making folder**

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
   docker-compose.yml link

   https://github.com/nazarovctrl/match-making/blob/master/docker-compose.yml
   
3. **Pull the Docker Image**

    ```sh
   docker pull nazarovv2/match-making:latest
    ```

4. **Start the Application**

    ```sh
   docker-compose up -d match-making-app
   ```
5. **Link for the application**

   http://localhost/swagger-ui/index.html#/

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
3. **Build the project:**

    Use Maven to build the project.

    ```sh
    mvn clean install
    ```

4. **Run the application:**

    To run the application, make sure you have Java 21 installed
    
    ```sh
    java -jar target/match-making-0.0.1-SNAPSHOT.jar
    ```
5. **Link for the application**

   http://localhost/swagger-ui/index.html#/

</details>


## More detailed instructions
For detailed instructions, visit the [Notion page](https://www.notion.so/match-making-a374c786725c4de59c06a69c9d0abab8?pvs=4).

