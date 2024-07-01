package com.bank.transactions.controller;

import com.bank.transactions.entity.Client;
import com.bank.transactions.entity.HistoricalTransaction;
import com.bank.transactions.service.ClientService;
import com.bank.transactions.repository.HistoricalTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Client Controller", description = "Endpoints for managing clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private HistoricalTransactionRepository historicalTransactionRepository;

    // Health check endpoint
    @GetMapping("/health")
    @Operation(summary = "Health Check", description = "Check if the application is running")
    public String healthCheck() {
        return "Application is running!";
    }

    // Post new client
    @PostMapping
    @Operation(summary = "Create Client", description = "Create a new client")
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    // Get All clients
    @GetMapping
    @Operation(summary = "Get All Clients", description = "Retrieve all clients")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    // Get client by id
    @GetMapping("/{id}")
    @Operation(summary = "Get Client by ID", description = "Retrieve a client by their ID")
    public Client getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    // Update client by ID
    @PutMapping("/{id}")
    @Operation(summary = "Update Client", description = "Update an existing client")
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        return clientService.updateClient(id, client);
    }

    // Delete a client
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Client", description = "Delete a client by their ID")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer Money", description = "Transfer money from one client to another")
    public ResponseEntity<String> transfer(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestParam double amount) {
        try {
            clientService.transfer(senderId, receiverId, amount);
            return ResponseEntity.ok("Transfer successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/transactions")
    @Operation(summary = "Get Historical Transactions", description = "Retrieve all historical transactions")
    public List<HistoricalTransaction> getAllTransactions() {
        return historicalTransactionRepository.findAll();
    }

}
