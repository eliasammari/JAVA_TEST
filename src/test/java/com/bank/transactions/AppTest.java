package com.bank.transactions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AppTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // This test ensures that the Spring application context loads successfully.
        assertNotNull(applicationContext, "The application context should have loaded.");
    }

    @Test
    void testBeanCreation() {
        // Check if the ClientService bean is created successfully.
        assertNotNull(applicationContext.getBean(com.bank.transactions.service.ClientService.class), "ClientService bean should be created");
        // Check if the ClientController bean is created successfully.
        assertNotNull(applicationContext.getBean(com.bank.transactions.controller.ClientController.class), "ClientController bean should be created");
    }

    @Test
    void createTestCase() {
        AppTest test = new AppTest();
        assertNotNull(test, "Test case should be created successfully");
    }

    @Test
    void testSuite() {
        AppTest suite = new AppTest();
        assertNotNull(suite, "Test suite should be created successfully");
    }

    @Test
    void testApp() {
        assertNotNull(this, "This test should always pass");
    }
}
