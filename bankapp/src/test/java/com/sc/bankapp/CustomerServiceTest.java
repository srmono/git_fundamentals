package com.sc.bankapp;

import com.sc.bankapp.exception.ResourceNotFoundException;
import com.sc.bankapp.model.Account;
import com.sc.bankapp.model.Customer;
import com.sc.bankapp.repository.CustomerRepo;
import com.sc.bankapp.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CustomerService using JUnit 5 + Mockito.
 * Compatible with Spring Boot 3.4.10 and Java 17.
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    // Mock the repository so tests don't touch the DB
    @Mock
    private CustomerRepo customerRepo;

    // Inject mocks into the service under test
    @InjectMocks
    private CustomerService customerService;

    private Customer sampleCustomer;

    @BeforeEach
    void setUp() {
        // prepare a sample customer used across tests
        sampleCustomer = new Customer();
        sampleCustomer.setId(10L);
        sampleCustomer.setFirstName("Test");
        sampleCustomer.setLastName("User");
        sampleCustomer.setEmail("test.user@example.com");
        sampleCustomer.setPhone("9000000000");
        sampleCustomer.setActive("true");
        sampleCustomer.setPassword("pwd");
        sampleCustomer.setRole("USER");

        Account a = new Account();
        a.setAccountType(1);
        a.setAccountNumber("ACC-TEST-01");
        a.setAccountBranch("Main");
        a.setAccountBalance(new BigDecimal("1000.00"));
        // account.customer will be set by service during create/save flows
        sampleCustomer.setAccounts(List.of(a));
    }

    /**
     * Test: getCustomerById should return a Customer when repository returns one.
     */
    @Test
    void getCustomerById_returnsCustomer_whenFound() {
        // Arrange: mock repository to return our sample customer
        when(customerRepo.findById(10L)).thenReturn(Optional.of(sampleCustomer));

        // Act
        Customer result = customerService.getCustomerById(10L);

        // Assert
        assertNotNull(result, "Returned customer should not be null");
        assertEquals(10L, result.getId(), "Returned customer id should match");
        assertEquals("Test", result.getFirstName(), "First name should match");
        verify(customerRepo, times(1)).findById(10L);


    }

    /**
     * Test: getCustomerById should throw ResourceNotFoundException when repo returns empty.
     */
    @Test
    void getCustomerById_throws_whenNotFound() {
        // Arrange
        when(customerRepo.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> customerService.getCustomerById(999L),
                "Expected ResourceNotFoundException when customer not found");

        assertTrue(ex.getMessage().contains("Customer not found with ID"));
        verify(customerRepo, times(1)).findById(999L);
    }

    /**
     * Test: createCustomer should set the parent customer on child accounts, then save.
     * This verifies the service behavior that establishes relationships before saving.
     */
    @Test
    void createCustomer_setsCustomerOnAccounts_andSaves() {
        // Arrange: build a new customer with one account (account.customer initially null)
        Customer toCreate = new Customer();
        toCreate.setFirstName("New");
        toCreate.setLastName("Customer");
        toCreate.setEmail("new@example.com");

        Account acc = new Account();
        acc.setAccountNumber("NEW-ACC-01");
        acc.setAccountBalance(new BigDecimal("50.00"));
        toCreate.setAccounts(List.of(acc));

        // Mock save to return a customer with an id (simulate DB-generated id)
        Customer saved = new Customer();
        saved.setId(55L);
        saved.setFirstName(toCreate.getFirstName());
        saved.setLastName(toCreate.getLastName());
        saved.setEmail(toCreate.getEmail());
        saved.setAccounts(toCreate.getAccounts());

        when(customerRepo.save(any(Customer.class))).thenReturn(saved);

        // Act
        Customer result = customerService.createCustomer(toCreate);

        // Assert basic returned values
        assertNotNull(result, "Saved customer should not be null");
        assertEquals(55L, result.getId(), "Saved customer id should match mocked id");

        // Capture the argument passed to customerRepo.save(...) to verify relationships were set
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepo, times(1)).save(captor.capture());
        Customer captured = captor.getValue();

        // The account inside captured should reference the captured customer (relationship set by service)
        assertNotNull(captured.getAccounts(), "Captured customer should have accounts list");
        assertFalse(captured.getAccounts().isEmpty(), "Captured accounts should not be empty");
        assertEquals(captured, captured.getAccounts().get(0).getCustomer(),
                "Account.customer must reference the saved parent customer");

        // Also ensure the returned object equals the mocked saved object
        assertEquals(55L, result.getId());
    }
}
