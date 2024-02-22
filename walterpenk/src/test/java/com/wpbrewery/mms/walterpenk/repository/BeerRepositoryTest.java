package com.wpbrewery.mms.walterpenk.repository;

import com.wpbrewery.mms.walterpenk.bootstrap.BootstrapData;
import com.wpbrewery.mms.walterpenk.entity.Beer;
import com.wpbrewery.mms.walterpenk.model.BeerStyle;
import com.wpbrewery.mms.walterpenk.services.BeerCsvServiceImpl;
import com.wpbrewery.mms.walterpenk.services.BeerServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;


    @Test
    void testGetBeerListByName(){
        List<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");
        //System.out.println(list.size());
        assertThat(list.size()).isGreaterThan(30);
    }

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