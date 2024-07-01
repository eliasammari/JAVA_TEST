package com.bank.transactions.service;

import com.bank.transactions.entity.Client;
import com.bank.transactions.entity.HistoricalTransaction;
import com.bank.transactions.repository.ClientRepository;
import com.bank.transactions.repository.HistoricalTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private HistoricalTransactionRepository historicalTransactionRepository;

    public Client createClient(Client client) {
        // Ensure the ID is not set manually
        client.setId(null);
        return clientRepository.save(client);
    }

     public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client updateClient(Long id, Client client) {
        Client existingClient = clientRepository.findById(id).orElse(null);
        if (existingClient != null) {
            existingClient.setName(client.getName());
            existingClient.setEmail(client.getEmail());
            existingClient.setPhone(client.getPhone());
            existingClient.setAddress(client.getAddress());
            existingClient.setBalance(client.getBalance());
            return clientRepository.save(existingClient);
        } else {
            return null;
        }
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Transactional
    public void transfer(Long senderId, Long receiverId, double amount) throws Exception {
        Client sender = clientRepository.findById(senderId)
                .orElseThrow(() -> new Exception("Sender not found"));
        Client receiver = clientRepository.findById(receiverId)
                .orElseThrow(() -> new Exception("Receiver not found"));

        if (sender.getBalance() >= amount && amount <= 1500) {
            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);

            clientRepository.save(sender);
            clientRepository.save(receiver);

            HistoricalTransaction transaction = new HistoricalTransaction();
            System.out.println(sender);
            System.out.println(receiver);
            System.out.println(LocalDateTime.now());
            System.out.println(amount);
            
            transaction.setId(null);
            transaction.setSender(sender);
            transaction.setReceiver(receiver);
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setAmount(amount);
            transaction.setTransactionType("TRANSFER");
            transaction.setDescription("Transfer from " + sender.getName() + " to " + receiver.getName());

            historicalTransactionRepository.save(transaction);

            System.out.println("Transaction saved: " + transaction);
        } else {
            throw new Exception("Transfer failed: Insufficient balance or amount exceeds limit.");
        }
    }
}

