package com.wpbrewery.mms.walterpenk.repository;

import com.wpbrewery.mms.walterpenk.entity.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder().beerName("My Beer").build());

        assertNotNull(savedBeer);
        assertNotNull(savedBeer.getId());
    }
}