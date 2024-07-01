package com.bank.transactions.controller;

import com.bank.transactions.entity.Client;
import com.bank.transactions.repository.ClientRepository;
import com.bank.transactions.repository.HistoricalTransactionRepository;
import com.bank.transactions.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private HistoricalTransactionRepository historicalTransactionRepository;

    @BeforeEach
    void setUp() {
        Client client = new Client();
        client.setId(1L);
        client.setName("John Doe");
        client.setEmail("john.doe@example.com");
        client.setPhone("123-456-7890");
        client.setAddress("123 Main St");
        client.setBalance(1000);

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
    }

    @Test
    void healthCheck() throws Exception {
        mockMvc.perform(get("/api/clients/health"))
                .andExpect(status().isOk());
    }

    @Test
    void createClient() throws Exception {
        String clientJson = "{\"name\":\"Jane Doe\",\"email\":\"jane.doe@example.com\",\"phone\":\"123-456-7890\",\"address\":\"123 Main St\",\"balance\":500.0}";

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isOk());

        verify(clientService, times(1)).createClient(any(Client.class));
    }

    @Test
    void transfer() throws Exception {
        doNothing().when(clientService).transfer(anyLong(), anyLong(), anyDouble());

        mockMvc.perform(post("/api/clients/transfer")
                        .param("senderId", "1")
                        .param("receiverId", "2")
                        .param("amount", "500"))
                .andExpect(status().isOk());

        verify(clientService, times(1)).transfer(anyLong(), anyLong(), anyDouble());
    }
}
