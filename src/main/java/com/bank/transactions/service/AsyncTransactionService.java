package com.bank.transactions.service;

import com.bank.transactions.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.logging.Logger;

@Service
public class AsyncTransactionService {

    @Autowired
    private EntityManager entityManager;

    private static final Logger logger = Logger.getLogger(AsyncTransactionService.class.getName());

    @Transactional
    public void transferMoney(Long fromClientId, Long toClientId, double amount) {
        try {
            Client fromClient = entityManager.find(Client.class, fromClientId, LockModeType.PESSIMISTIC_WRITE);
            Client toClient = entityManager.find(Client.class, toClientId, LockModeType.PESSIMISTIC_WRITE);

            if (fromClient == null || toClient == null) {
                logger.severe("Client not found");
                throw new IllegalArgumentException("Client not found");
            }

            fromClient.setBalance(fromClient.getBalance() - amount);
            toClient.setBalance(toClient.getBalance() + amount);

            entityManager.persist(fromClient);
            entityManager.persist(toClient);
        } catch (Exception e) {
            logger.severe("Error in transferMoney: " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void updateBalance(Long clientId, double amount) {
        try {
            Client client = entityManager.find(Client.class, clientId, LockModeType.PESSIMISTIC_WRITE);

            if (client == null) {
                logger.severe("Client not found");
                throw new IllegalArgumentException("Client not found");
            }

            client.setBalance(client.getBalance() + amount);

            entityManager.persist(client);
        } catch (Exception e) {
            logger.severe("Error in updateBalance: " + e.getMessage());
            throw e;
        }
    }
}
