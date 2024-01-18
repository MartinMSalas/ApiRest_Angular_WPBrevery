package com.wpbrewery.mms.walterpenk.repository;

import com.wpbrewery.mms.walterpenk.entity.Beer;
import com.wpbrewery.mms.walterpenk.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeerNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("My Beer 123456789012345678901234567890123456789012345678901")
                    .beerStyle(BeerStyle.IPA)
                    .upc("123456789")
                    .price(new BigDecimal("12.99"))
                    .build());

            beerRepository.flush();

        });

    }
    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                            .beerName("My Beer")
                            .beerStyle(BeerStyle.IPA)
                            .upc("123456789")
                            .price(new BigDecimal("12.99"))
                .build());

        beerRepository.flush();
        assertNotNull(savedBeer);
        assertNotNull(savedBeer.getId());
    }
}