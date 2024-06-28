# Banking Transactions Simulation

## Project Overview

This project is a simulation of banking transactions implemented using Java and Spring Boot. The application provides a RESTful API for money transfers between accounts. The main objectives are to demonstrate the use of asynchronous operations and ensure ACID properties in transactions.

## Features

- RESTful API for money transfers between accounts
- Database integration with PostgreSQL/MySQL
- Asynchronous operations
- ACID compliance

## Requirements

- Java 11 or later
- Maven 3.6.3 or later
- PostgreSQL or MySQL
- Docker
- Git

## Installation

1. **Clone the repository**
    ```bash
    git clone https://github.com/YOUR_GITHUB_USERNAME/NEW_REPOSITORY_NAME.git
    cd NEW_REPOSITORY_NAME
    ```

2. **Set up the database**
    - Install PostgreSQL 
    - Create a new database and user

3. **Configure the application**
    - Update the `application.properties` file with your database credentials

4. **Build the application**
    ```bash
    mvn clean install
    ```

5. **Run the application**
    ```bash
    java -jar target/transactions-simulation-0.0.1-SNAPSHOT.jar
    ```

## Usage

Use `curl` or any API client to test the API endpoints.

## Testing

To run the tests, use:
```bash
mvn test
