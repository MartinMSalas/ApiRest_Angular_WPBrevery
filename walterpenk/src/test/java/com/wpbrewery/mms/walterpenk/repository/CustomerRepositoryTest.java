package com.wpbrewery.mms.walterpenk.repository;

import com.wpbrewery.mms.walterpenk.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        Customer savedCustomer = customerRepository.save(Customer.builder().name("My Customer").build());

        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());
    }
}