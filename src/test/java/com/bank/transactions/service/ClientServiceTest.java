package com.bank.transactions.service;

import com.bank.transactions.entity.Client;
import com.bank.transactions.repository.ClientRepository;
import com.bank.transactions.repository.HistoricalTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private HistoricalTransactionRepository historicalTransactionRepository;

    private Client sender;
    private Client receiver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sender = new Client();
        sender.setId(1L);
        sender.setName("John Doe");
        sender.setBalance(2000);

        receiver = new Client();
        receiver.setId(2L);
        receiver.setName("Jane Smith");
        receiver.setBalance(1000);
    }

    @Test
    void transfer_success() throws Exception {
        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(sender));
        when(clientRepository.findById(2L)).thenReturn(java.util.Optional.of(receiver));

        clientService.transfer(1L, 2L, 500);

        assertEquals(1500, sender.getBalance());
        assertEquals(1500, receiver.getBalance());

        verify(clientRepository, times(1)).save(sender);
        verify(clientRepository, times(1)).save(receiver);
        verify(historicalTransactionRepository, times(1)).save(any());
    }

    @Test
    void transfer_insufficientBalance() {
        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(sender));
        when(clientRepository.findById(2L)).thenReturn(java.util.Optional.of(receiver));

        Exception exception = assertThrows(Exception.class, () -> {
            clientService.transfer(1L, 2L, 2500);
        });

        assertEquals("Transfer failed: Insufficient balance or amount exceeds limit.", exception.getMessage());

        verify(clientRepository, times(0)).save(sender);
        verify(clientRepository, times(0)).save(receiver);
        verify(historicalTransactionRepository, times(0)).save(any());
    }

    @Test
    void transfer_amountExceedsLimit() {
        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(sender));
        when(clientRepository.findById(2L)).thenReturn(java.util.Optional.of(receiver));

        Exception exception = assertThrows(Exception.class, () -> {
            clientService.transfer(1L, 2L, 1600);
        });

        assertEquals("Transfer failed: Insufficient balance or amount exceeds limit.", exception.getMessage());

        verify(clientRepository, times(0)).save(sender);
        verify(clientRepository, times(0)).save(receiver);
        verify(historicalTransactionRepository, times(0)).save(any());
    }
}
