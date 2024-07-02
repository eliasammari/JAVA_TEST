# Banking Transactions Simulation

## Project Overview

This project is a simulation of banking transactions implemented using Java and Spring Boot. The application provides a RESTful API for money transfers between accounts. The main objectives are to demonstrate the use of asynchronous operations and ensure ACID properties in transactions.

## Features

- RESTful API for money transfers between accounts
- Database integration with PostgreSQL
- Asynchronous operations
- ACID compliance

## Requirements

- java=17.0.11
- maven=3.8.1
- postgresql=15.6
- docker=27.0.2
- docker-compose=1.29.2
- swagger-ui=1.6.9
- git=2.30.0

## Installation


# JAVA_TEST Project

## Setup Instructions

### Step 1: Switch to the postgres user and create the database

Open a terminal and switch to the postgres user:

```sh
sudo -i -u postgres
psql
```

Run the following SQL commands to set up the database and user:

```sql
CREATE DATABASE mydb;
CREATE USER myuser WITH PASSWORD 'mypassword';
GRANT ALL PRIVILEGES ON DATABASE mydb TO myuser;
GRANT ALL ON SCHEMA public TO myuser;
ALTER USER myuser WITH SUPERUSER;
```

Exit `psql` and switch back to your regular user:

```sh
\q
exit
```

### Step 2: Clone the repository and manage branches

Navigate to your desired directory and clone the repository:

```sh
cd /path/to/your/directory
git clone https://github.com/eliasammari/JAVA_TEST.git
cd JAVA_TEST
git fetch origin
git checkout master
```

### Step 3: Ensure the creation of the tables and insert some clients

Execute the following Maven command to run the Java file that creates the necessary tables and inserts data:

```sh
mvn exec:java
```

## Handling Errors

### Verify PostgreSQL Port

Check if the PostgreSQL port (5432) is in use:

```sh
sudo netstat -atlpn | grep 5432
```

### Stop PostgreSQL Service

If PostgreSQL is running on the host machine and you need to stop it:

```sh
sudo systemctl stop postgresql
```

### Stop Active Docker Containers

If there are active Docker containers using the same port, stop them:

```sh
docker stop $(docker ps -q)
```

### Step 4: Build and Start the Docker Containers

Build and start the Docker containers:

```sh
docker-compose up --build
```

## Interact with the API

You can interact with the API via Swagger UI:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Verify Database State

To verify the database state from the terminal:

```sh
psql -h localhost -U myuser -d mydb -W
```
________________________________________________________
## Run with .jar file : 

```sh
mvn clean package && java -jar $(find target -name "*.jar" | head -n 1)
```

## More Details About Tests

### Concurrent Transactions

Tests for concurrent transactions can be found in the following file:

[ConcurrentTransactionTest.java](https://github.com/eliasammari/JAVA_TEST/blob/master/src/test/java/com/bank/transactions/service/ConcurrentTransactionTest.java)

### New Transfer and Historical Transactions

Tests for new transfers and saving them into historical transactions can be found in:

[ClientServiceTest.java](https://github.com/eliasammari/JAVA_TEST/blob/master/src/test/java/com/bank/transactions/service/ClientServiceTest.java)

### Client Controller

Tests for the client controller can be found in:

[ClientControllerTest.java](https://github.com/eliasammari/JAVA_TEST/blob/master/src/test/java/com/bank/transactions/controller/ClientControllerTest.java)

