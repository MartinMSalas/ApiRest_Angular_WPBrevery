package com.wpbrewery.mms.walterpenk.services;

import com.wpbrewery.mms.walterpenk.model.BeerCsvRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeerCsvServiceImplTest {

    BeerCsvService beerCSVService = new BeerCsvServiceImpl();

    @Test
    void convertCSV() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
        List<BeerCsvRecord> recs = beerCSVService.convertCSV(file);
        System.out.println(recs.size());
        assertThat(recs.size()).isGreaterThan(0);

    }
}