package com.bank.transactions;

import com.bank.transactions.entity.Client;
import com.bank.transactions.service.AsyncTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
@Transactional
public class ConcurrentTransactionTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AsyncTransactionService transactionService;

    private Client client1;
    private Client client2;

    @BeforeEach
    public void setup() {
        client1 = new Client();
        client1.setName("Karim Duval");
        client1.setEmail("karim.duval@example.com");
        client1.setPhone("1234567890");
        client1.setAddress("789 Sunset Blvd");
        client1.setBalance(1500.0);
        
        client2 = new Client();
        client2.setName("Joyce Jonathan");
        client2.setEmail("joyce.jonathan@example.com");
        client2.setPhone("0987654321");
        client2.setAddress("321 Sunrise Ave");
        client2.setBalance(2500.0);

        entityManager.persist(client1);
        entityManager.persist(client2);
    }

    @Test
    public void testConcurrentTransactions() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> transactionA = CompletableFuture.runAsync(() -> {
            try {
                transactionService.transferMoney(client1.getId(), client2.getId(), 100.0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        CompletableFuture<Void> transactionB = CompletableFuture.runAsync(() -> {
            try {
                transactionService.updateBalance(client1.getId(), 50.0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        CompletableFuture.allOf(transactionA, transactionB).get();
        
        // Assertions to verify the transactions
        Client updatedClient1 = entityManager.find(Client.class, client1.getId());
        Client updatedClient2 = entityManager.find(Client.class, client2.getId());

        // Assertions
        System.out.println("Client 1 Balance: " + updatedClient1.getBalance());
        System.out.println("Client 2 Balance: " + updatedClient2.getBalance());
    }
}
