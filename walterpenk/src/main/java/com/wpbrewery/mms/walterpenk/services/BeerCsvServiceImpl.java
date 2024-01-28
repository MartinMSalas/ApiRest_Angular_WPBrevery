package com.wpbrewery.mms.walterpenk.services;

import com.opencsv.bean.CsvToBeanBuilder;
import com.wpbrewery.mms.walterpenk.model.BeerCsvRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
@Service
public class BeerCsvServiceImpl implements BeerCsvService {
    @Override
    public List<BeerCsvRecord> convertCSV(File csvFile) throws FileNotFoundException {
        try {
            List<BeerCsvRecord> recs = new CsvToBeanBuilder<BeerCsvRecord>(new FileReader(csvFile))
                    .withType(BeerCsvRecord.class)
                    .build()
                    .parse();
            return recs;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
