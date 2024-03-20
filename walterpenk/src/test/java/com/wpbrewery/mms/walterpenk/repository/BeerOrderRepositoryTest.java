package com.wpbrewery.mms.walterpenk.repository;

import com.wpbrewery.mms.walterpenk.entity.Beer;
import com.wpbrewery.mms.walterpenk.entity.BeerOrder;
import com.wpbrewery.mms.walterpenk.entity.BeerOrderShipment;
import com.wpbrewery.mms.walterpenk.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer testCustomer;
    Beer testBeer;

    @BeforeEach
    void setUp() {
        testCustomer = customerRepository.findAll().get(0);
        testBeer = beerRepository.findAll().get(0);
    }
    @Test
    @Transactional
    void testBeerOrders(){

        BeerOrder beerOrder = BeerOrder.builder()
                .customerRef("Test customer")
                .customer(testCustomer)
                .beerOrderShipment(BeerOrderShipment.builder()
                                .trackingNumber("123456")
                                .build()
                        )
                .build();

        BeerOrder savedOrder = beerOrderRepository.save(beerOrder);
        System.out.println(savedOrder.getCustomerRef());

    }
}