package com.springboot.bankDemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springboot.bankDemo.model.Customer;
import com.springboot.bankDemo.model.User;
import com.springboot.bankDemo.repository.CustomerRepository;
import com.springboot.bankDemo.service.CustomerService;
import com.springboot.bankDemo.service.UserService;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@InjectMocks
	private CustomerService customerService;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private UserService userService;
	
	private User user;
	private Customer customer;
	
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
	}
	
	@Test
	public void postCustomerTest() {
		
		when(customerRepository.save(any(Customer.class))).thenReturn(customer);
		
		assertEquals(customer, customerService.postCustomer(customer));
	}
	
	@Test
	public void getCustomerByIdTest() {
		when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
		
		assertEquals(customer, customerService.getCustomerById(1));
		
		RuntimeException e = assertThrows(RuntimeException.class, () -> customerService.getCustomerById(2));
		assertEquals("ID is Invalid".toLowerCase(), e.getMessage().toLowerCase());
	}
	
	@AfterEach
	public void afterTest() {
		user = null;
		customer = null;
	}
}
