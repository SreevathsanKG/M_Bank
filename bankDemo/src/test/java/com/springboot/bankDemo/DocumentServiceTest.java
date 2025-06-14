package com.springboot.bankDemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.bankDemo.model.Customer;
import com.springboot.bankDemo.model.Document;
import com.springboot.bankDemo.model.User;
import com.springboot.bankDemo.repository.CustomerRepository;
import com.springboot.bankDemo.repository.DocumentRepository;
import com.springboot.bankDemo.service.DocumentService;

@SpringBootTest
public class DocumentServiceTest {

	@InjectMocks
    private DocumentService documentService;
    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private CustomerRepository customerRepository;

    private User user;
    private Customer customer;
    private Document document;

    @BeforeEach
    public void init() {
        user = new User();
        user.setId(1);
        user.setUsername("david@gmail.com");
        user.setPassword("david@123");
        user.setRole("CUSTOMER");

        customer = new Customer();
        customer.setId(1);
        customer.setFirstName("David");
        customer.setLastName("Miller");
        customer.setEmail("david@gmail.com");
        customer.setPhoneNumber("9876543210");
        customer.setAddress("Mumbai");
        customer.setRegistrationDate(LocalDate.now());
        customer.setUser(user);

        document = new Document();
        document.setId(1);
        document.setPanDocLink("pan.pdf");
        document.setAadharDocLink("aadhar.pdf");
        document.setCustomer(customer);
    }

    @Test
    public void postDocumentTest() {
        when(customerRepository.getCustomerByUsername("david@gmail.com")).thenReturn(customer);
        when(documentRepository.save(any(Document.class))).thenReturn(document);
        assertEquals(document, documentService.postDocument("david@gmail.com", document));
    }

    @Test
    public void putDocumentTest() {
        Document updated = new Document();
        updated.setPanDocLink("updatedpan.pdf");
        updated.setAadharDocLink(null);

        when(customerRepository.getCustomerByUsername("david@gmail.com")).thenReturn(customer);
        when(documentRepository.getByCustomerId(1)).thenReturn(Optional.of(document));
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        Document result = documentService.putDocument("david@gmail.com", updated);
        assertEquals("updatedpan.pdf", result.getPanDocLink());
    }

    @Test
    public void getByCustomerIdTest() throws Exception {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(documentRepository.getByCustomerId(1)).thenReturn(Optional.of(document));
        assertEquals(document, documentService.getByCustomerId(1));
    }

    @Test
    public void getDocByUsernameTest() throws Exception {
        when(customerRepository.getCustomerByUsername("david@gmail.com")).thenReturn(customer);
        when(documentRepository.getByCustomerId(1)).thenReturn(Optional.of(document));
        assertEquals(document, documentService.getDocByUsername("david@gmail.com"));
    }

    @Test
    public void getAllDocumentsTest() {
        when(documentRepository.findAll()).thenReturn(List.of(document));
        assertEquals(List.of(document), documentService.getAll());
    }
    
    @AfterEach
    public void afterTest() {
    	user = null;
    	customer = null;
    	document = null;
    }
}
