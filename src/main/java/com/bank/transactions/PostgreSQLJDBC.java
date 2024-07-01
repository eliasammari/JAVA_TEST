package com.bank.transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class PostgreSQLJDBC {
    public static void main(String[] args) {
        Connection connection = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb", "myuser", "mypassword");

            stmt = connection.createStatement();

            // Create Client Table with Balance
            String createClientTable = "CREATE TABLE IF NOT EXISTS client (" +
                                       "id SERIAL PRIMARY KEY, " +
                                       "name VARCHAR(100) NOT NULL, " +
                                       "email VARCHAR(100) UNIQUE NOT NULL, " +
                                       "phone VARCHAR(15), " +
                                       "address TEXT, " +
                                       "balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00);";
            stmt.executeUpdate(createClientTable);

            // Create Historical Transactions Table with Sender and Receiver
            String createTransactionsTable = "CREATE TABLE IF NOT EXISTS historical_transactions (" +
                                             "id SERIAL PRIMARY KEY, " +
                                             "sender_id INT REFERENCES client(id), " +
                                             "receiver_id INT REFERENCES client(id), " +
                                             "transaction_date TIMESTAMP NOT NULL, " +
                                             "amount DECIMAL(10, 2) NOT NULL, " +
                                             "transaction_type VARCHAR(50), " +
                                             "description TEXT);";
            stmt.executeUpdate(createTransactionsTable);

            // Insert Clients with Initial Balances
            String insertClient1 = "INSERT INTO client (name, email, phone, address, balance) VALUES ('John Doe', 'john.doe@example.com', '123-456-7890', '123 Main St, Anytown, USA', 1000.00);";
            stmt.executeUpdate(insertClient1);

            String insertClient2 = "INSERT INTO client (name, email, phone, address, balance) VALUES ('Jane Smith', 'jane.smith@example.com', '234-567-8901', '456 Elm St, Othertown, USA', 1500.00);";
            stmt.executeUpdate(insertClient2);

            String insertClient3 = "INSERT INTO client (name, email, phone, address, balance) VALUES ('Alice Johnson', 'alice.johnson@example.com', '345-678-9012', '789 Oak St, Anothertown, USA', 2000.00);";
            stmt.executeUpdate(insertClient3);

            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Tables created and clients inserted successfully");
    }
}
